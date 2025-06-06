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
import java.util.Comparator;
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

        if (period == 3) {
            start = now.minusMonths(3);
        } else if (period == 6) {
            start = now.minusMonths(6);
        } else if (period == 12) {
            start = now.minusMonths(12);
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

        List<RevenuePeriodDTO> result = revenueByYearMonth.entrySet().stream()
                .map(e -> {
                    String[] parts = e.getKey().split("-");
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    long totalPrice = Math.round(e.getValue() * 0.86);;
                    return new RevenuePeriodDTO(year, month, totalPrice);
                })
                .sorted(Comparator.comparing(RevenuePeriodDTO::getYear)
                        .thenComparing(RevenuePeriodDTO::getMonth))
                .toList();

        return result;
    }
}
