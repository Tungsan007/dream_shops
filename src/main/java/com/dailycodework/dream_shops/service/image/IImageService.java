package com.dailycodework.dream_shops.service.image;

import com.dailycodework.dream_shops.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface IImageService {
    Image getImageById(Long id);
    Image saveImage(MultipartFile file, Long productId);
    void updateImage(MultipartFile file, Long imageId);
    void deleteImageById(Long id);
}
