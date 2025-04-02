package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.WeekRevenueDTO;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.CustomerOrderRepository;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Service
public class GetRevenueLastSevenDays {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private StoreRepository storeRepository;

    public WeekRevenueDTO execute(String storeId) throws EdnaException {

        Store store = storeRepository.findById(storeId).orElseThrow(() -> new EdnaException("Store not found", HttpStatus.BAD_REQUEST));

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime startOfCurrentWeek = now.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1).toLocalDate().atStartOfDay();
        LocalDateTime endOfCurrentWeek = startOfCurrentWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59);

        LocalDateTime startOfLastWeek = startOfCurrentWeek.minusWeeks(1);
        LocalDateTime endOfLastWeek = endOfCurrentWeek.minusWeeks(1);

        int currentWeekTotal = customerOrderRepository.getRevenueInPeriod(store, startOfCurrentWeek, endOfCurrentWeek);
        int lastWeekTotal = customerOrderRepository.getRevenueInPeriod(store, startOfLastWeek, endOfLastWeek);

        int currentWeekRevenue = (int) (currentWeekTotal * 0.86);
        int lastWeekRevenue = (int) (lastWeekTotal * 0.86);

        int variation = (lastWeekRevenue > 0)
                ? ((currentWeekRevenue - lastWeekRevenue) / lastWeekRevenue) * 100
                : 100;

        return new WeekRevenueDTO(currentWeekRevenue, variation);
    }
}
