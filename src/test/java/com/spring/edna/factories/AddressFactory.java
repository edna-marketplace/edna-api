package com.spring.edna.factories;

import com.spring.edna.models.entities.Address;
import com.spring.edna.models.entities.Store;

import java.util.UUID;

public class AddressFactory {

    // Cria um endereço sem store associado
    public static Address create() {
        Address a = new Address();
        a.setNumber(String.valueOf((int) (Math.random() * 100)));
        a.setCep("88064-001");
        a.setStreet("Street " + UUID.randomUUID());
        a.setNeighborhood("Neighborhood " + UUID.randomUUID());
        a.setCity("City " + UUID.randomUUID());
        return a;
    }

    // Cria um endereço com store associado
    public static Address create(Store store) {
        Address address = create();
        address.setStore(store);
        return address;
    }
}
