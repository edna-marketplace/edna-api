package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.ClotheSummaryDTO;
import com.spring.edna.models.dtos.PaginationMetaDTO;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.User;
import com.spring.edna.models.enums.ClotheBrand;
import com.spring.edna.models.enums.ClotheSize;
import com.spring.edna.models.enums.StoreImageType;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.repositories.StoreRepository;
import com.spring.edna.models.selectors.ClotheSelector;
import com.spring.edna.storage.GetImageUrl;
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
    private GetImageUrl getImageUrl;

    public FetchClothesWithFilterResponse execute(ClotheSelector selector, User subject) throws EdnaException {
        Store storeSubject = storeRepository.findById(subject.getId()).orElse(null);
        boolean isSubjectStore = storeSubject != null;

        long totalCount = clotheRepository.count(selector);
        PageRequest page = PageRequest.of(selector.getPage() - 1, selector.getLimit());

        List<Clothe> clothes = clotheRepository.findAll(selector, page).toList();

        List<ClotheSummaryDTO> clothesSummaries = new ArrayList<>();

        for (Clothe clothe : clothes) {
            Store store = storeRepository.findById(clothe.getStore().getId()).orElseThrow(() ->
                    new EdnaException("Store not found for the clothe with name:" + clothe.getName(), HttpStatus.BAD_REQUEST)
            );

            ClotheSummaryDTO clotheSummary = new ClotheSummaryDTO(
                    clothe.getId(),
                    clothe.getName(),
                    clothe.getPriceInCents(),
                    clothe.getBrand(),
                    getOtherBrand(clothe),
                    clothe.getSize(),
                    getOtherSize(clothe),
                    getClotheFirstImage(clothe),
                    isSubjectStore ? null : getStoreProfileImageUrl(store)
            );

            clothesSummaries.add(clotheSummary);
        }

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

    private String getStoreProfileImageUrl(Store store) {
        /*
            na operacao realizada abaixo para pegar a url (nome unico) da imagem de perfil, foi pego primeiro elemento do
            retorno do filtro porque nesse caso sempre vai retornar um array com uma posicao (com a imagem de perfil),
            ja que a lista de imagens de uma loja tem somente duas imagens (perfil e banner)
         */

        String profileImage = store
                .getImages()
                .stream()
                .filter(image -> image.getType() == StoreImageType.PROFILE)
                .findFirst()
                .map(image -> image.getUrl())
                .orElse(null);

        return getImageUrl.execute(profileImage);
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

    private String getOtherBrand(Clothe clothe) {
        if (clothe.getBrand() == ClotheBrand.OTHER) {
            if (clothe.getBrandOther() != null) {
                return clothe.getBrandOther();
            } else {
                return ClotheBrand.OTHER.toString();
            }
        }
        return null;
    }

    private String getOtherSize(Clothe clothe) {
        if (clothe.getSize() == ClotheSize.OTHER) {
            if (clothe.getSizeOther() != null) {
                return clothe.getSizeOther();
            } else {
                return ClotheSize.OTHER.toString();
            }
        }
        return null;
    }
}
