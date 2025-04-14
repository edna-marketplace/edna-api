package com.spring.edna.utils;

import com.spring.edna.models.entities.CustomerOrder;
import com.spring.edna.models.entities.OrderRating;
import org.hibernate.query.Order;

import java.util.ArrayList;
import java.util.List;

public class StoreRatingUtils {

    public static Double calculateAverageRating(List<CustomerOrder> orders) {
        if(orders == null || orders.isEmpty()) {
            return 0.0;
        }

        List<OrderRating> ratings = new ArrayList<>();

        for (CustomerOrder order : orders) {
            OrderRating rating = order.getRating();

            ratings.add(rating);
        }

        Integer storeRatingAcc = 0;

        for(OrderRating orderRating : ratings) {
            storeRatingAcc += orderRating.getRating();
        }

        Double avgRating = (double) (storeRatingAcc / ratings.size());

        return avgRating;
    }
}
