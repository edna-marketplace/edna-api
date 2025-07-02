package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.WeekCustomerDTO;
import com.spring.edna.models.repositories.ClotheOrderRepository;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Service
public class GetNewCustomersFromLastWeek {

    @Autowired
    private ClotheOrderRepository clotheOrderRepository;

    @Autowired
    private StoreRepository storeRepository;

    public WeekCustomerDTO execute(String storeId) throws EdnaException {

        LocalDateTime now = LocalDateTime.now();
        double variation = 0;

        LocalDateTime startOfCurrentWeek = now.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1).toLocalDate().atStartOfDay();
        LocalDateTime endOfCurrentWeek = startOfCurrentWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59);

        LocalDateTime startOfLastWeek = startOfCurrentWeek.minusWeeks(1);
        LocalDateTime endOfLastWeek = endOfCurrentWeek.minusWeeks(1);

        long lastWeekCustomers = clotheOrderRepository.countNewCustomersByStoreIdAndDateRange(storeId, startOfLastWeek, endOfLastWeek);
        long currentWeekCustomers = clotheOrderRepository.countNewCustomersByStoreIdAndDateRange(storeId, startOfCurrentWeek, endOfCurrentWeek);

        if (lastWeekCustomers == 0 && currentWeekCustomers == 0) {
            variation = 0;
        } else if (lastWeekCustomers > 0) {
            variation = ((double) (currentWeekCustomers - lastWeekCustomers) / lastWeekCustomers) * 100;
        } else {
            variation = 100;
        }

        return new WeekCustomerDTO(currentWeekCustomers, variation);
    }
}
