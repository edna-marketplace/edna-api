package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.ClotheSummaryDTO;
import com.spring.edna.models.entities.*;
import com.spring.edna.models.enums.StoreImageType;
import com.spring.edna.models.mappers.ClotheMapper;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.models.selectors.ClotheSelector;
import com.spring.edna.storage.GetImageUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FetchClothesWithFilter {

    @Autowired
    private ClotheRepository clotheRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private GetImageUrl getImageUrl;

    public List<ClotheSummaryDTO> execute(ClotheSelector selector) throws EdnaException {
        List<Clothe> clothes;

        if(selector.hasPagination()) {
            int pageNumber = selector.getPage();
            int pageSize = selector.getLimit();

            PageRequest page = PageRequest.of(pageNumber - 1, pageSize);
            clothes = clotheRepository.findAll(selector, page).toList();
        } else {
            clothes = clotheRepository.findAll(selector);
        }

        // isso aqui ta horrivel, foi mal, vou refatorar dps
        for(Clothe c : clothes) {
            Store store = storeRepository.findById(c.getStore().getId()).orElseThrow(() ->
                new EdnaException("Store not found.", HttpStatus.BAD_REQUEST)
            );

            List<StoreImage> storeImages = new ArrayList<>(store.getImages());

            List<ClotheImage> clotheImages = c.getImages();

            ClotheImage clotheImage = !clotheImages.isEmpty() ? clotheImages.get(0) : null;
            Optional<StoreImage> storeImage = !storeImages.isEmpty() ? storeImages.stream().filter(i -> i.getType()
                    .equals(StoreImageType.PROFILE)).findFirst() : null;

            if(clotheImage != null) {
                String clotheImageUrl = getImageUrl.execute(clotheImage.getUrl());

                clotheImages.get(0).setUrl(clotheImageUrl);
            }

            if(storeImage != null) {
                String storeImageUrl = getImageUrl.execute(storeImage.get().getUrl());

                storeImages.get(0).setUrl(storeImageUrl);
            } else {
                StoreImage newStoreImage = new StoreImage();
                newStoreImage.setUrl(null);
                storeImages.add(newStoreImage);
            }
        }

        return ClotheMapper.toClotheSummaryDTOList(clothes);
    }
}

