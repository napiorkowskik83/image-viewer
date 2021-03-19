package com.task.imageviewer.repository;

import com.task.imageviewer.domain.ImageInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface ImageInfoRepository extends CrudRepository<ImageInfo, Long> {

    @Override
    List<ImageInfo> findAll();

    @Override
    ImageInfo save(ImageInfo imageInfo);

    @Override
    Optional<ImageInfo> findById(Long id);

    Optional<ImageInfo> findByName(String imageName);
}
