package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.PaginationMetaDTO;
import com.spring.edna.models.dtos.ClotheSummaryDTO;
import com.spring.edna.models.entities.*;
import com.spring.edna.models.enums.StoreImageType;
import com.spring.edna.models.mappers.ClotheMapper;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.models.selectors.ClotheSelector;
import com.spring.edna.services.presenters.FetchClothesWithFilterPresenter;
import com.spring.edna.storage.GetImageUrl;
import com.spring.edna.utils.GetStoreImagesUrls;
import com.spring.edna.utils.GetStoreImagesUrls.GetStoreImagesUrlsResponse;
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

    @Autowired
    private GetStoreImagesUrls getStoreImagesUrls;

    public FetchClothesWithFilterPresenter execute(ClotheSelector selector, User subject) throws EdnaException {
        if (!selector.hasPagination()) {
            throw new EdnaException("Missing page index and page limit", HttpStatus.BAD_REQUEST);
        }

        int totalCount = (int) clotheRepository.count(selector);
        PageRequest page = PageRequest.of(selector.getPage() - 1, selector.getLimit());
        List<Clothe> clothes = clotheRepository.findAll(selector, page).toList();

        String profileImageUrl = null;

        for (Clothe c : clothes) {
            Store store = storeRepository.findById(c.getStore().getId()).orElseThrow(() ->
                    new EdnaException("Store not found.", HttpStatus.BAD_REQUEST)
            );

            List<StoreImage> profileImage = store.getImages()
                    .stream()
                    .filter(image -> image.getType() == StoreImageType.PROFILE)
                    .toList();

            GetStoreImagesUrlsResponse storeImages = getStoreImagesUrls.execute(profileImage);

            profileImageUrl = storeImages.getProfileImageUrl();

            profileImage.get(0).setUrl(profileImageUrl);

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
