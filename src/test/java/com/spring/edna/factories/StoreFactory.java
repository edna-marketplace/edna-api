package com.spring.edna.factories;

import com.spring.edna.models.entities.Store;

import java.util.UUID;

public class StoreFactory {
    public static Store create() {
        Store s = new Store();
        s.setName("Brecho " + UUID.randomUUID());
        s.setCnpj("93.544.959/0001-79");
        s.setEmail("brecho@" + UUID.randomUUID() + ".com");
        s.setPhone("123123123");
        s.setPassword("123");

       return s;
    }
}
