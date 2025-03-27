package com.spring.edna.utils;

import com.spring.edna.models.entities.OrderRating;

import java.util.List;

public class StoreRatingUtils {

    public static Double calculateAverageRating(List<OrderRating> orderRatings) {
        if(orderRatings == null || orderRatings.isEmpty()) {
            return 0.0;
        }

        Integer storeRatingAcc = 0;

        for(OrderRating orderRating : orderRatings) {
            storeRatingAcc += orderRating.getRating();
        }

        Double avgRating = (double) (storeRatingAcc / orderRatings.size());

        return avgRating;
    }
}
