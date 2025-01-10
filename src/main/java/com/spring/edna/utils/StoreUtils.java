package com.spring.edna.utils;

import com.spring.edna.models.entities.Store;

import java.util.List;
import java.util.stream.Collectors;

public class StoreUtils {

    public static List<Store> removeDeletedStores(List<Store> stores) {
        return stores
                .stream()
                .filter(store -> !store.isDeleted())
                .collect(Collectors.toList());
    }
}
