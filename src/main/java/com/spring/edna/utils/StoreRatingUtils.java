package com.spring.edna.utils;

import com.spring.edna.models.entities.ClotheOrder;

import java.util.ArrayList;
import java.util.List;

public class StoreRatingUtils {

    public static Double calculateAverageRating(List<ClotheOrder> orders) {
        if(orders == null || orders.isEmpty()) {
            return 0.0;
        }

        List<Integer> ratings = new ArrayList<>();

        for (ClotheOrder order : orders) {
            if (order.getRating() != null) {
                Integer rating = order.getRating();
                ratings.add(rating);
            }
        }

        if (ratings.isEmpty()) {
            return null;
        }

        Double storeRatingAcc = 0.0;

        for(Integer orderRating : ratings) {
            storeRatingAcc += orderRating;
        }

        Double avgRating = storeRatingAcc / ratings.size();

        return avgRating;
    }
}
