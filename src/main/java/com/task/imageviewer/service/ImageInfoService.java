package com.task.imageviewer.service;

import com.task.imageviewer.domain.ImageInfo;
import com.task.imageviewer.repository.ImageInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageInfoService {
    private final ImageInfoRepository repository;

    @Autowired
    public ImageInfoService(final ImageInfoRepository repository) {
        this.repository = repository;
    }

    public void saveImageInfo(final ImageInfo image) {
        Optional<ImageInfo> sameNameImage = repository.findByName(image.getName());
        if(sameNameImage.isPresent()){
            repository.delete(sameNameImage.get());
        }
        repository.save(image);
    }

    public void deleteImageInfo(String name) {
        ImageInfo image = repository.findByName(name).get();
        if (image != null) {
            repository.delete(image);
        }
    }
}
