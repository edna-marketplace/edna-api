package com.spring.edna.services;

import com.spring.edna.models.dtos.WeekOrderDTO;
import com.spring.edna.models.enums.OrderStatus;
import com.spring.edna.models.repositories.ClotheOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Service
public class GetOrdersLastSevenDays {

    @Autowired
    private ClotheOrderRepository clotheOrderRepository;

    public WeekOrderDTO execute(String storeId) {

        LocalDateTime now = LocalDateTime.now();
        double variation = 0.0;

        LocalDateTime startOfCurrentWeek = now.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1).toLocalDate().atStartOfDay();
        LocalDateTime endOfCurrentWeek = startOfCurrentWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59);

        LocalDateTime startOfLastWeek = startOfCurrentWeek.minusWeeks(1);
        LocalDateTime endOfLastWeek = endOfCurrentWeek.minusWeeks(1);

        long currentWeekOrders = clotheOrderRepository.countByStoreIdAndStatusAndCreatedAtBetween(storeId, OrderStatus.COMPLETED, startOfCurrentWeek, endOfCurrentWeek);
        long lastWeekOrders = clotheOrderRepository.countByStoreIdAndStatusAndCreatedAtBetween(storeId, OrderStatus.COMPLETED, startOfLastWeek, endOfLastWeek);


        if (lastWeekOrders == 0 && currentWeekOrders == 0) {
            variation = 0;
        } else if (lastWeekOrders > 0) {
            variation = ((double) (currentWeekOrders - lastWeekOrders) / lastWeekOrders) * 100;
        } else {
            variation = 100;
        }

        return new WeekOrderDTO(currentWeekOrders, variation);

    }
}
