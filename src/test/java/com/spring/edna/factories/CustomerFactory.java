package com.spring.edna.factories;

import com.spring.edna.models.entities.Customer;
import com.spring.edna.utils.CPFGenerator;
import com.spring.edna.utils.NameGenerator;
import com.spring.edna.utils.PhoneNumberGenerator;

import java.util.ArrayList;
import java.util.UUID;

public class CustomerFactory {
    public static Customer create() {
        Customer c = new Customer();
        c.setName(NameGenerator.generateName());
        c.setCpf(CPFGenerator.generateValidCPF());
        c.setEmail(NameGenerator.generateName() + (UUID.randomUUID()) + "@gmail.com");
        c.setPhone(PhoneNumberGenerator.generateValidPhoneNumber());
        c.setSavedClothes(new ArrayList<>());


        return c;
    }
}
