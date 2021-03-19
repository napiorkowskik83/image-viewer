package com.task.imageviewer.service;

import com.task.imageviewer.domain.ImageInfo;
import com.task.imageviewer.repository.ImageInfoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageInfoServiceTestSuite {

    @InjectMocks
    ImageInfoService service;

    @Mock
    ImageInfoRepository repository;

    @Test
    void saveImageInfo() {
        //Given
        ImageInfo imageInfo = new ImageInfo("test.png");

        //When
        service.saveImageInfo(imageInfo);

        //Then
        verify(repository, times(1)).save(imageInfo);
    }

    @Test
    void saveImageInfoWithNameAlreadyInDatabase() {
        //Given
        ImageInfo imageInfo1 = new ImageInfo("test.png");
        ImageInfo imageInfo2 = new ImageInfo("test.png");

        when(repository.findByName(imageInfo1.getName())).thenReturn(Optional.of(imageInfo2));

        //When
        service.saveImageInfo(imageInfo1);

        //Then
        verify(repository, times(1)).delete(imageInfo2);
        verify(repository, times(1)).save(imageInfo1);
    }

    @Test
    void deleteImageInfo() {
        //Given
        ImageInfo imageInfo = new ImageInfo("test.png");
        when(repository.findByName(imageInfo.getName())).thenReturn(Optional.of(imageInfo));

        //When
        service.deleteImageInfo(imageInfo.getName());

        //Then
        verify(repository, times(1)).delete(imageInfo);
    }
}