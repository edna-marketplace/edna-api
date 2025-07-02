package com.spring.edna.services;

import com.spring.edna.models.dtos.ClotheRankingDTO;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.repositories.SavedClotheRepository;
import com.spring.edna.storage.GetImageUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GetThreeMostSavedClothes {

    @Autowired
    private SavedClotheRepository savedClotheRepository;

    @Autowired
    private ClotheRepository clotheRepository;

    @Autowired
    private GetImageUrl getImageUrl;

    public List<ClotheRankingDTO> execute(String userId) {
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1); // Último segundo do mês

        List<Object[]> result = savedClotheRepository.findTopSavedClothesInMonth(startOfMonth, endOfMonth);

        return result.stream()
                .limit(3)
                .map(row -> {
                    String clotheId = (String) row[0];
                    String name = (String) row[1];
                    Integer price = (Integer) row[2];
                    Long savedCount = (Long) row[3];

                    Clothe clothe = clotheRepository.findById(clotheId).orElseThrow();
                    String imageUrl = getClotheFirstImage(clothe);

                    boolean savedByCurrentUser = savedClotheRepository.existsByCustomerIdAndClotheId(userId, clotheId);

                    return new ClotheRankingDTO(clotheId, name, imageUrl, price, savedCount, savedByCurrentUser);
                })
                .toList();
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