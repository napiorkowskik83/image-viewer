package com.task.imageviewer.repository;

import com.task.imageviewer.domain.ImageInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ImageInfoRepositoryTestSuite {

    @Autowired
    ImageInfoRepository repository;

    @Test
    void saveAndFindById() {
        //Given
        ImageInfo imageInfo1 = new ImageInfo("test1.jpg");
        repository.save(imageInfo1);

        Long id1 = imageInfo1.getId();

        //When
        Optional<ImageInfo> imageInfoOptional = repository.findById(id1);

        //Then
        assertTrue(imageInfoOptional.isPresent());

        //Clean Up
        repository.deleteById(id1);

    }

    @Test
    void findByName() {
        //Given
        ImageInfo imageInfo1 = new ImageInfo("test1.jpg");
        repository.save(imageInfo1);

        Long id1 = imageInfo1.getId();

        //When
        Optional<ImageInfo> imageInfoOptional = repository.findByName(imageInfo1.getName());

        //Then
        assertTrue(imageInfoOptional.isPresent());

        //Clean Up
        repository.deleteById(id1);
    }

    @Test
    void testFindAll() {
        //Given
        ImageInfo imageInfo1 = new ImageInfo("test1.jpg");
        ImageInfo imageInfo2 = new ImageInfo("test2.png");
        repository.save(imageInfo1);
        repository.save(imageInfo2);

        Long id1 = imageInfo1.getId();
        Long id2 = imageInfo2.getId();

        //When
        List<ImageInfo> list = repository.findAll();

        //Then
        assertTrue(list.size() >= 2);

        //Clean Up
        repository.deleteById(id1);
        repository.deleteById(id2);
    }
}