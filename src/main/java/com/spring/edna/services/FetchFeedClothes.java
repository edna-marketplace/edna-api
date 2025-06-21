package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.FeedClotheDTO;
import com.spring.edna.models.dtos.PaginationMetaDTO;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.models.selectors.ClotheSelector;
import com.spring.edna.storage.GetImageUrl;
import com.spring.edna.utils.StoreImageUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FetchFeedClothes {

    @Data
    @AllArgsConstructor
    public static class FetchFeedClothesResponse {
        private List<FeedClotheDTO> clothes;
        private PaginationMetaDTO meta;
    }

    @Autowired
    private ClotheRepository clotheRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private GetImageUrl getImageUrl;

    @Autowired
    private StoreImageUtils storeImageUtils;

    public FetchFeedClothesResponse execute(ClotheSelector selector, String subjectId) throws EdnaException {
        Customer customer = customerRepository.findById(subjectId).orElse(null);

        if (customer == null) {
            throw new EdnaException("Usuário não encontrado.", HttpStatus.BAD_REQUEST);
        }

        selector.setGender(customer.getStylePreference());

        long totalCount = clotheRepository.count(selector);

        PageRequest page = PageRequest.of(selector.getPage() - 1, selector.getLimit(), Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Clothe> clothes = clotheRepository.findAll(selector, page).toList();

        List<FeedClotheDTO> feedClothes = toFeedClotheDTOList(clothes);

        PaginationMetaDTO meta = new PaginationMetaDTO(
                selector.getPage(),
                clothes.size(),
                totalCount
        );

        return new FetchFeedClothesResponse(
                feedClothes,
                meta
        );
    }

    private List<FeedClotheDTO> toFeedClotheDTOList(List<Clothe> clothes) throws EdnaException {
        List<FeedClotheDTO> feedClothes = new ArrayList<>();

        for (Clothe clothe : clothes) {
            Store store = storeRepository.findById(clothe.getStore().getId()).orElseThrow(() ->
                    new EdnaException("Store not found for the clothe with name:" + clothe.getName(), HttpStatus.BAD_REQUEST)
            );

            FeedClotheDTO clotheSummary = new FeedClotheDTO(
                    clothe.getId(),
                    clothe.getName(),
                    clothe.getPriceInCents(),
                    clothe.getSize(),
                    clothe.getSizeOther(),
                    clothe.getBrand(),
                    clothe.getBrandOther(),
                    getClotheFirstImage(clothe),
                    clothe.getStore().getId(),
                    clothe.getStore().getName(),
                    storeImageUtils.getProfileImageUrl(store)
            );

            feedClothes.add(clotheSummary);
        }

        return feedClothes;
    }

    private String getClotheFirstImage(Clothe clothe) {
        String firstImageUrl = clothe
                .getImages()
                .stream()
                .findFirst()
                .map(image -> image.getUrl())
                .orElse(null);

        return getImageUrl.execute(firstImageUrl);
    }
}
