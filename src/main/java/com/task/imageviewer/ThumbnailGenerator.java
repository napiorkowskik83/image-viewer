package com.task.imageviewer;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

public class ThumbnailGenerator {
    protected static void createFromImageFile(File inputFile, File outputFile){
        try {
            Thumbnails.of(inputFile)
                    .size(50, 50)
                    .toFile(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
