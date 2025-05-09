package com.spring.edna.utils;

import com.spring.edna.google.GoogleDistanceMatrix;
import com.spring.edna.models.dtos.CoordinatesDTO;
import com.spring.edna.models.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GetDistanceBetweenCustomerAndStore {

    @Autowired
    private GoogleDistanceMatrix googleDistanceMatrix;

    private final Map<String, CachedDistance> distanceCache = new ConcurrentHashMap<>();

    private static final long CACHE_EXPIRATION_MS = 60 * 60 * 1000; // 1 hour

    private static final long BUFFER_TIME_MS = 5 * 60 * 1000; // 5 minutes

    private static final double COORDINATE_THRESHOLD_METERS = 500.0;

    public String execute(CoordinatesDTO customerCoordinates, Address storeAddress) {

        String storeKey = createStoreKey(storeAddress);

        String cachedDistance = findCachedDistance(customerCoordinates, storeKey);
        if (cachedDistance != null) {
            System.out.println("RETORNOU CACHE: " + cachedDistance);
            return cachedDistance;
        }

        String customerCoordinatesString = customerCoordinates.getLatitude() + "," + customerCoordinates.getLongitude();
        String storeAddressString = formatStoreAddress(storeAddress);

        String distance = googleDistanceMatrix.getDistance(customerCoordinatesString, storeAddressString);

        storeInCache(customerCoordinates, storeKey, distance);

        return distance;
    }

    private String findCachedDistance(CoordinatesDTO customerCoordinates, String storeKey) {
        long currentTime = System.currentTimeMillis();
        double customerLat = Double.parseDouble(customerCoordinates.getLatitude());
        double customerLng = Double.parseDouble(customerCoordinates.getLongitude());

        for (Map.Entry<String, CachedDistance> entry : distanceCache.entrySet()) {
            CachedDistance cachedItem = entry.getValue();

            if (!cachedItem.getStoreKey().equals(storeKey)) {
                continue;
            }

            if (currentTime > cachedItem.getExpirationTime() - BUFFER_TIME_MS) {
                continue;
            }

            double cachedLat = cachedItem.getCustomerLatitude();
            double cachedLng = cachedItem.getCustomerLongitude();
            double distanceInMeters = calculateDistanceInMeters(customerLat, customerLng, cachedLat, cachedLng);

            if (distanceInMeters <= COORDINATE_THRESHOLD_METERS) {
                return cachedItem.getDistance();
            }
        }

        return null;
    }

    private void storeInCache(CoordinatesDTO customerCoordinates, String storeKey, String distance) {
        long currentTime = System.currentTimeMillis();
        long expirationTime = currentTime + CACHE_EXPIRATION_MS;

        String cacheKey = createCacheKey(customerCoordinates, storeKey);
        double customerLat = Double.parseDouble(customerCoordinates.getLatitude());
        double customerLng = Double.parseDouble(customerCoordinates.getLongitude());

        CachedDistance cachedDistance = new CachedDistance(
                customerLat,
                customerLng,
                storeKey,
                distance,
                expirationTime
        );

        distanceCache.put(cacheKey, cachedDistance);
    }

    private String createCacheKey(CoordinatesDTO coordinates, String storeKey) {
        return coordinates.getLatitude() + "_" + coordinates.getLongitude() + "_" + storeKey;
    }

    private String createStoreKey(Address storeAddress) {
        return storeAddress.getNumber() + "_"
                + storeAddress.getStreet() + "_"
                + storeAddress.getCity() + "_"
                + storeAddress.getCep();
    }

    private String formatStoreAddress(Address storeAddress) {
        String addressStreetFormated = formatString(storeAddress.getStreet());
        String addressCityFormated = formatString(storeAddress.getCity());

        return storeAddress.getNumber()
                + "," + addressStreetFormated
                + "," + addressCityFormated
                + "," + storeAddress.getCep();
    }

    private static String formatString(String string) {
        return string.replaceAll(" ", "+");
    }

    /**
     * Calculates the distance in meters between two coordinates using the Haversine formula
     */
    private double calculateDistanceInMeters(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS_KM = 6371; // Earth's radius in kilometers

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c * 1000; // Convert to meters
    }

    @Data
    @AllArgsConstructor
    private static class CachedDistance {
        private final double customerLatitude;
        private final double customerLongitude;
        private final String storeKey;
        private final String distance;
        private final long expirationTime;
    }
}
