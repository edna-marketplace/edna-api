package com.spring.edna.services;

import com.spring.edna.models.dtos.WeekOrderDTO;
import com.spring.edna.models.repositories.CustomerOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Service
public class GetOrdersLastSevenDays {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    public WeekOrderDTO execute(String storeId) {

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime startOfCurrentWeek = now.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1).toLocalDate().atStartOfDay();
        LocalDateTime endOfCurrentWeek = startOfCurrentWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59);

        LocalDateTime startOfLastWeek = startOfCurrentWeek.minusWeeks(1);
        LocalDateTime endOfLastWeek = endOfCurrentWeek.minusWeeks(1);

        long currentWeekOrders = customerOrderRepository.countByStoreIdAndCreatedAtBetween(storeId, startOfCurrentWeek, endOfCurrentWeek);
        long lastWeekOrders = customerOrderRepository.countByStoreIdAndCreatedAtBetween(storeId, startOfLastWeek, endOfLastWeek);

        double variation = (lastWeekOrders > 0)
                ? ((double) (currentWeekOrders - lastWeekOrders) / lastWeekOrders) * 100
                : 100;

        return new WeekOrderDTO(currentWeekOrders, variation);

    }
}
