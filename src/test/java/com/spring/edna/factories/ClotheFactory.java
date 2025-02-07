package com.spring.edna.factories;

import com.spring.edna.models.entities.Clothe;
import com.spring.edna.models.entities.ClotheImage;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.enums.ClotheBrand;
import com.spring.edna.models.enums.ClotheCategory;
import com.spring.edna.models.enums.ClotheGender;
import com.spring.edna.models.enums.ClotheSize;

import java.util.Collections;
import java.util.UUID;

public class ClotheFactory {
    public static Clothe create(Store store) {
        Clothe clothe = new Clothe();
        clothe.setId(UUID.randomUUID().toString());
        clothe.setName("Clothe " + UUID.randomUUID());
        clothe.setPriceInCents((int) (Math.random() * 10000));
        clothe.setFabric("Fabric " + UUID.randomUUID());
        clothe.setColor("Color " + UUID.randomUUID());
        clothe.setLengthInCm((int) (Math.random() * 200));
        clothe.setWidthInCm((int) (Math.random() * 100));
        clothe.setCategory(ClotheCategory.values()[(int) (Math.random() * ClotheCategory.values().length)]);
        clothe.setSize(ClotheSize.values()[(int) (Math.random() * ClotheSize.values().length)]);
        clothe.setGender(ClotheGender.values()[(int) (Math.random() * ClotheGender.values().length)]);
        clothe.setBrand(ClotheBrand.values()[(int) (Math.random() * ClotheBrand.values().length)]);
        clothe.setStore(store);
        clothe.setDeleted(false);
        clothe.setImages(Collections.singletonList(new ClotheImage()));
        return clothe;
    }
}
