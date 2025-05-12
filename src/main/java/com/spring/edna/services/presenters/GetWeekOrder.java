package com.spring.edna.services.presenters;

import com.spring.edna.models.repositories.ClotheOrderRepository;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetWeekOrder {

    @Autowired
    private ClotheOrderRepository clotheOrderRepository;

    @Autowired
    private StoreRepository storeRepository;

}
