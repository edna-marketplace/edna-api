package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.SummaryRevenueDTO;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.ClotheOrderRepository;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class GetMonthRevenue {

    @Autowired
    private ClotheOrderRepository clotheOrderRepository;

    @Autowired
    private StoreRepository storeRepository;

    public SummaryRevenueDTO execute(String storeId) throws EdnaException {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EdnaException("Store not found", HttpStatus.BAD_REQUEST));

        LocalDateTime now = LocalDateTime.now();

        LocalDate firstDayOfCurrentMonth = now.withDayOfMonth(1).toLocalDate();
        LocalDateTime startOfCurrentMonth = firstDayOfCurrentMonth.atStartOfDay();
        LocalDateTime endOfCurrentMonth = firstDayOfCurrentMonth
                .plusMonths(1)
                .minusDays(1)
                .atTime(23, 59, 59);

        LocalDateTime startOfLastMonth = startOfCurrentMonth.minusMonths(1);
        LocalDateTime endOfLastMonth = endOfCurrentMonth.minusMonths(1);

        int currentMonthTotal = Optional.ofNullable(
                clotheOrderRepository.getRevenueInPeriod(store, startOfCurrentMonth, endOfCurrentMonth)).orElse(0);

        int lastMonthTotal = Optional.ofNullable(
                clotheOrderRepository.getRevenueInPeriod(store, startOfLastMonth, endOfLastMonth)).orElse(0);

        // Calcula com double
        double currentMonthRevenue = currentMonthTotal * 0.86;
        double lastMonthRevenue = lastMonthTotal * 0.86;

        long currentMonthRevenueRounded = Math.round(currentMonthRevenue);
        long lastMonthRevenueRounded = Math.round(lastMonthRevenue);

        double variation = (lastMonthRevenueRounded > 0)
                ? ((double) (currentMonthRevenueRounded - lastMonthRevenueRounded) / lastMonthRevenueRounded) * 100
                : 100;

        double variationRounded = Math.round(variation * 100.0) / 100.0;

        return new SummaryRevenueDTO(currentMonthRevenueRounded, variationRounded);
    }
}
