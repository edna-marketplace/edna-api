package com.spring.edna.utils;

import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.entities.OrderRating;

import java.util.ArrayList;
import java.util.List;

public class StoreRatingUtils {

    public static Double calculateAverageRating(List<ClotheOrder> orders) {
        if(orders == null || orders.isEmpty()) {
            return 0.0;
        }

        List<OrderRating> ratings = new ArrayList<>();

        for (ClotheOrder order : orders) {
            OrderRating rating = order.getRating();

            ratings.add(rating);
        }

        Double storeRatingAcc = 0.0;

        for(OrderRating orderRating : ratings) {
            storeRatingAcc += orderRating.getRating();
        }

        Double avgRating = storeRatingAcc / ratings.size();

        return avgRating;
    }
}
