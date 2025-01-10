package com.spring.edna.utils;

import com.spring.edna.models.entities.StoreRating;

import java.util.List;

public class StoreRatingUtils {

    public static Double calculateAverageRating(List<StoreRating> storeRatings) {
        if(storeRatings == null || storeRatings.isEmpty()) {
            return 0.0;
        }

        Integer storeRatingAcc = 0;

        for(StoreRating storeRating : storeRatings) {
            storeRatingAcc += storeRating.getRating();
        }

        Double avgRating = (double) (storeRatingAcc / storeRatings.size());

        return avgRating;
    }
}
