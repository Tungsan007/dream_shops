package com.dailycodework.dream_shops.service.image;

import com.dailycodework.dream_shops.dto.ImageDto;
import com.dailycodework.dream_shops.exceptions.ResourcesNotFoundException;
import com.dailycodework.dream_shops.model.Image;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.repository.ImageRepository;
import com.dailycodework.dream_shops.repository.ProductRepository;
import com.dailycodework.dream_shops.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final ProductService productService;
    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new ResourcesNotFoundException("Image not found"));
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        //return DTO to client
        //map qua multipart file, set du lieu cho new image,
        //save to db ->
        //set du lieu for new ImageDto
        //return savedImageDto
        Product product = productService.getProductById(productId);
        List<ImageDto> imageDtos = new ArrayList<>();

        for(MultipartFile file: files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "api/v1/images/download/";
                String downloadUrl = buildDownloadUrl + image.getId();
                Image savedImage = imageRepository.save(image);

                image.setDownloadUrl(buildDownloadUrl + savedImage.getId());
                imageRepository.save(image);

                ImageDto imageDto = new ImageDto();
                imageDto.setId(image.getId());
                imageDto.setFileName(image.getFileName());
                imageDto.setDownloadUrl(image.getDownloadUrl());
                imageDtos.add(imageDto);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return imageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);

        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));

            imageRepository.save(image);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> { throw new ResourcesNotFoundException("Not found for the image");});
    }
}
