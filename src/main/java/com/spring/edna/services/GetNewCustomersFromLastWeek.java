package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.WeekCustomerDTO;
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
public class GetNewCustomersFromLastWeek {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private StoreRepository storeRepository;

    public WeekCustomerDTO execute(String storeId) throws EdnaException {

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime startOfCurrentWeek = now.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1).toLocalDate().atStartOfDay();
        LocalDateTime endOfCurrentWeek = startOfCurrentWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59);

        LocalDateTime startOfLastWeek = startOfCurrentWeek.minusWeeks(1);
        LocalDateTime endOfLastWeek = endOfCurrentWeek.minusWeeks(1);

        long lastWeekCustomers = customerOrderRepository.countNewCustomersByStoreIdAndDateRange(storeId, startOfLastWeek, endOfLastWeek);
        long currentWeekCustomers = customerOrderRepository.countNewCustomersByStoreIdAndDateRange(storeId, startOfCurrentWeek, endOfCurrentWeek);

        double variation = (lastWeekCustomers > 0)
                ? ((double) (currentWeekCustomers - lastWeekCustomers) / lastWeekCustomers) * 100
                : 100;

        return new WeekCustomerDTO(currentWeekCustomers, variation);
    }
}
