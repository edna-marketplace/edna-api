package com.spring.edna.services;

import com.spring.edna.exception.EdnaException;
import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.Customer;
import com.spring.edna.models.entities.ClotheOrder;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.repositories.ClotheRepository;
import com.spring.edna.models.repositories.ClotheOrderRepository;
import com.spring.edna.models.repositories.CustomerRepository;
import com.spring.edna.models.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CreateClotheOrder {

    @Autowired
    private ClotheRepository clotheRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ClotheOrderRepository clotheOrderRepository;

    public HttpStatus execute(String clotheId, String paymentIntentId, String customerId) throws EdnaException {
        ClotheOrder clotheOrderInDB = clotheOrderRepository.findByClotheId(clotheId).orElse(null);

        if(clotheOrderInDB != null) {
            throw new EdnaException("Essa peça já está em um pedidos.", HttpStatus.CONFLICT);
        }

        Clothe clothe = clotheRepository.findById(clotheId).orElseThrow(
                () -> new EdnaException("Peça não encontrada.",HttpStatus.BAD_REQUEST)
        );
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new EdnaException("Cliente não encontrado.",HttpStatus.BAD_REQUEST)
        );

        ClotheOrder clotheOrder = new ClotheOrder();
        clotheOrder.setCustomer(customer);
        clotheOrder.setClothe(clothe);
        clotheOrder.setStore(clothe.getStore());
        clotheOrder.setPaymentIntentId(paymentIntentId);

        boolean alreadyOrdered = clotheOrderRepository.existsByCustomerIdAndStoreId(clotheOrder.getCustomer().getId(),
                clotheOrder.getStore().getId());
        clotheOrder.setFirstOrder(!alreadyOrdered);

        clotheOrderRepository.save(clotheOrder);

        clothe.setOrdered(true);
        clotheRepository.save(clothe);

        return HttpStatus.CREATED;
    }
}
