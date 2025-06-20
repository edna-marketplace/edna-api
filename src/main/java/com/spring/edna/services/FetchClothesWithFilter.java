package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.ClotheSummaryDTO;
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
import com.spring.edna.utils.VerifySubjectStore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FetchClothesWithFilter {

    @Data
    @AllArgsConstructor
    public static class FetchClothesWithFilterResponse {
        private List<ClotheSummaryDTO> clothes;
        private PaginationMetaDTO meta;
    }

    @Autowired
    private ClotheRepository clotheRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private GetImageUrl getImageUrl;

    @Autowired
    private VerifySubjectStore verifySubjectStore;

    @Autowired
    private StoreImageUtils storeImageUtils;

    public FetchClothesWithFilterResponse execute(ClotheSelector selector, String subjectId) throws EdnaException {
        boolean isSubjectStore = verifySubjectStore.execute(subjectId);

        if (isSubjectStore) {
            selector.setStoreId(subjectId);
        } else {
            selector.setCustomerId(subjectId);
        }

        long totalCount = clotheRepository.count(selector);

        PageRequest page = PageRequest.of(selector.getPage() - 1, selector.getLimit());
        List<Clothe> clothes = clotheRepository.findAll(selector, page).toList();

        Customer customer = null;

        if (!isSubjectStore) {
            customer = customerRepository.findById(subjectId).orElseThrow(() -> new EdnaException(
                    "Cliente não encontrado! Verifique o token de autenticação e tente novamente.", HttpStatus.NOT_FOUND
            ));
        }

        List<ClotheSummaryDTO> clothesSummaries = toCotheSummaryDTOList(clothes, customer, isSubjectStore);

        PaginationMetaDTO meta = new PaginationMetaDTO(
                selector.getPage(),
                clothes.size(),
                totalCount
        );

        return new FetchClothesWithFilterResponse(
                clothesSummaries,
                meta
        );
    }

    private List<ClotheSummaryDTO> toCotheSummaryDTOList(List<Clothe> clothes, Customer customer, boolean isSubjectStore) throws EdnaException {
        List<ClotheSummaryDTO> clothesSummaries = new ArrayList<>();

        for (Clothe clothe : clothes) {
            Store store = storeRepository.findById(clothe.getStore().getId()).orElseThrow(() ->
                    new EdnaException("Loja não encontrada com o nome:" + clothe.getName(), HttpStatus.BAD_REQUEST)
            );

            boolean isSaved = false;

            if (!isSubjectStore) {
                isSaved = customer.getSavedClothes().stream()
                        .anyMatch(savedClothe -> savedClothe.getClothe().equals(clothe));
            }

            ClotheSummaryDTO clotheSummary = new ClotheSummaryDTO(
                    clothe.getId(),
                    clothe.getName(),
                    clothe.getPriceInCents(),
                    clothe.getBrand(),
                    clothe.getBrandOther(),
                    clothe.getSize(),
                    clothe.getSizeOther(),
                    isSaved,
                    getClotheFirstImage(clothe),
                    isSubjectStore ? null : clothe.getStore().getName(),
                    isSubjectStore ? null : storeImageUtils.getProfileImageUrl(store)
            );

            clothesSummaries.add(clotheSummary);
        }

        return clothesSummaries;
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
