package com.spring.edna.utils;

import com.spring.edna.google.GoogleDistanceMatrix;
import com.spring.edna.models.dtos.CoordinatesDTO;
import com.spring.edna.models.entities.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetDistanceBetweenCustomerAndStore {

    @Autowired
    private GoogleDistanceMatrix googleDistanceMatrix;

    public String execute(CoordinatesDTO customerCoordinates, Address storeAddress) {

        String customerCoordinatesString = customerCoordinates.getLatitude() + "," + customerCoordinates.getLongitude();

        String addressStreetFormated = formatString(storeAddress.getStreet());
        String addressCityFormated = formatString(storeAddress.getCity());

        String storeAddressString = storeAddress.getNumber()
                + "," + addressStreetFormated
                + "," + addressCityFormated
                + "," + storeAddress.getCep();

        String distance = googleDistanceMatrix.getDistance(customerCoordinatesString, storeAddressString);

        return distance;
    }

    private static String formatString(String string) {
        return string.replaceAll(" ", "+");
    }
}
