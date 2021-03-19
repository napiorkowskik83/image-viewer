package com.task.imageviewer;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageGenerator {
    public static Image generateImage(File file) {
        return new Image(new StreamResource(
                file.getName(), (InputStreamFactory) () -> {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }), file.getName());
    }
}
