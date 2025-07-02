package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.SummaryRevenueDTO;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.ClotheOrderRepository;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Optional;

@Service
public class GetRevenueLastSevenDays {

    @Autowired
    private ClotheOrderRepository clotheOrderRepository;

    @Autowired
    private StoreRepository storeRepository;

    public SummaryRevenueDTO execute(String storeId) throws EdnaException {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EdnaException("Loja não encontrada", HttpStatus.BAD_REQUEST));

        LocalDateTime now = LocalDateTime.now();
        double variation = 0;

        LocalDateTime startOfCurrentWeek = now.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1)
                .toLocalDate().atStartOfDay();
        LocalDateTime endOfCurrentWeek = startOfCurrentWeek.plusDays(6)
                .withHour(23).withMinute(59).withSecond(59);

        LocalDateTime startOfLastWeek = startOfCurrentWeek.minusWeeks(1);
        LocalDateTime endOfLastWeek = endOfCurrentWeek.minusWeeks(1);

        int currentWeekTotal = Optional.ofNullable(
                clotheOrderRepository.getRevenueInPeriod(store, startOfCurrentWeek, endOfCurrentWeek)
        ).orElse(0);

        int lastWeekTotal = Optional.ofNullable(
                clotheOrderRepository.getRevenueInPeriod(store, startOfLastWeek, endOfLastWeek)
        ).orElse(0);

        double currentWeekRevenue = currentWeekTotal * 0.86;
        double lastWeekRevenue = lastWeekTotal * 0.86;

        long currentWeekRevenueRounded = Math.round(currentWeekRevenue);
        long lastWeekRevenueRounded = Math.round(lastWeekRevenue);

//        variation = (lastWeekRevenue > 0)
//                ? ((double)(currentWeekRevenue - lastWeekRevenue) / lastWeekRevenue) * 100
//                : 100;

        if (lastWeekRevenueRounded == 0 && currentWeekRevenueRounded == 0) {
            variation = 0;
        } else if (lastWeekRevenueRounded > 0) {
            variation = ((double) (currentWeekRevenueRounded - lastWeekRevenueRounded) / lastWeekRevenueRounded) * 100;
        } else {
            variation = 100;
        }

        long variationRounded = Math.round(variation);

        return new SummaryRevenueDTO(currentWeekRevenueRounded, variationRounded);
    }
}
