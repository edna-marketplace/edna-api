package com.spring.edna.services.presenters;

import com.spring.edna.models.repositories.CustomerOrderRepository;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetWeekOrder {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private StoreRepository storeRepository;

}
