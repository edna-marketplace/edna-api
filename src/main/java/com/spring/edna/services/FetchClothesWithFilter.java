package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.PaginationMetaDTO;
import com.spring.edna.models.dtos.ClotheSummaryDTO;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.ClotheImage;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.StoreImage;
import com.spring.edna.models.enums.StoreImageType;
import com.spring.edna.models.mappers.ClotheMapper;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.models.selectors.ClotheSelector;
import com.spring.edna.services.presenters.FetchClothesWithFilterPresenter;
import com.spring.edna.storage.GetImageUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FetchClothesWithFilter {

    @Autowired
    private ClotheRepository clotheRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private GetImageUrl getImageUrl;

    public FetchClothesWithFilterPresenter execute(ClotheSelector selector, String customerId) throws EdnaException {
        if (!selector.hasPagination()) {
            throw new EdnaException("Missing page index and page limit", HttpStatus.BAD_REQUEST);
        }

        int totalCount = (int) clotheRepository.count(selector);
        PageRequest page = PageRequest.of(selector.getPage() - 1, selector.getLimit());
        List<Clothe> clothes = clotheRepository.findAll(selector, page).toList();

        for (Clothe c : clothes) {
            Store store = storeRepository.findById(c.getStore().getId()).orElseThrow(() ->
                    new EdnaException("Store not found.", HttpStatus.BAD_REQUEST)
            );

            List<StoreImage> storeImages = store.getImages().stream()
                    .filter(i -> i.getType().equals(StoreImageType.PROFILE))
                    .collect(Collectors.toList());

            StoreImage profileImage = storeImages.size() > 0 ? storeImages.get(0) : null;
            String profileImageUrl = (profileImage != null) ? getImageUrl.execute(profileImage.getUrl()) : null;

            if (storeImages.size() > 0) {
                storeImages.get(0).setUrl(profileImageUrl);
            } else {
                StoreImage storeImage = new StoreImage();
                storeImage.setUrl(null);
                storeImages.add(storeImage);
            }
            store.setImages(storeImages);

            List<ClotheImage> clotheImages = c.getImages().stream().limit(1).collect(Collectors.toList());
            if (!clotheImages.isEmpty()) {
                clotheImages.get(0).setUrl(getImageUrl.execute(clotheImages.get(0).getUrl()));
            }
            c.setImages(clotheImages);
        }

        PaginationMetaDTO meta = new PaginationMetaDTO(selector.getPage(), clothes.size(), totalCount);
        List<ClotheSummaryDTO> clothesSummary = ClotheMapper.toClotheSummaryDTOList(clothes);

        return new FetchClothesWithFilterPresenter(clothesSummary, meta);
    }
}
