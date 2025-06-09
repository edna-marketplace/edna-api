package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.dtos.RevenuePeriodDTO;
import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.ClotheOrderRepository;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GetMonthlyRevenueByPeriod {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ClotheOrderRepository clotheOrderRepository;

    public List<RevenuePeriodDTO> execute(String storeId, int period) throws EdnaException {

        Store store = storeRepository.findById(storeId).orElseThrow(() -> new EdnaException("Brechó não encontrado!", HttpStatus.NOT_FOUND));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start;
        LocalDateTime end;

        if (period == 3 || period == 6 || period == 12) {
            start = now.minusMonths(period - 1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
            end = now.withDayOfMonth(1).plusMonths(1).minusNanos(1);
        } else {
            throw new EdnaException("Período inválido", HttpStatus.BAD_REQUEST);
        }

        List<ClotheOrder> orders = clotheOrderRepository.findCompletedOrdersByStoreAndPeriod(store, start, now);

        Map<String, Long> revenueByYearMonth = orders.stream()
                .collect(Collectors.groupingBy(
                        co -> {
                            int year = co.getCreatedAt().getYear();
                            int month = co.getCreatedAt().getMonthValue();
                            return year + "-" + String.format("%02d", month);
                        },
                        Collectors.summingLong(co -> co.getClothe().getPriceInCents())
                ));

        List<RevenuePeriodDTO> result = new java.util.ArrayList<>();

        for (int i = 0; i < period; i++) {
            LocalDateTime monthDate = start.plusMonths(i);
            int year = monthDate.getYear();
            int month = monthDate.getMonthValue();
            String key = year + "-" + String.format("%02d", month);

            long totalPrice = Math.round(revenueByYearMonth.getOrDefault(key, 0L) * 0.86);
            result.add(new RevenuePeriodDTO(year, month, totalPrice));
        }

        Collections.reverse(result);
        return result;
    }
}
