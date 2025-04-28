package com.spring.edna.utils;

import com.spring.edna.models.entities.StoreImage;
import com.spring.edna.models.enums.StoreImageType;
import com.spring.edna.storage.GetImageUrl;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetStoreImagesUrls {

    @Data
    @AllArgsConstructor
    public static class GetStoreImagesUrlsResponse {
        String bannerImageUrl = null;
        String profileImageUrl = null;
    }

    @Autowired
    private GetImageUrl getImageUrl;

    public GetStoreImagesUrlsResponse execute(List<StoreImage> images) {

        StoreImage bannerImageInDB = images.stream().filter(image -> image.getType().equals(StoreImageType.BANNER))
                .findFirst().orElse(null);
        StoreImage profileImageInDB = images.stream().filter(image -> image.getType().equals(StoreImageType.PROFILE))
                .findFirst().orElse(null);

        String bannerImageUrl = (bannerImageInDB != null) ? getImageUrl.execute(bannerImageInDB.getUrl()) : null;
        String profileImageUrl = (profileImageInDB != null) ? getImageUrl.execute(profileImageInDB.getUrl()) : null;

        return new GetStoreImagesUrlsResponse(bannerImageUrl, profileImageUrl);
    }
}
