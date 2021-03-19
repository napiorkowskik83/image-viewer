package com.task.imageviewer;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.Route;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Route("")
public class MainView extends VerticalLayout {
    private static final String IMAGE_FOLDER = "uploaded-images";

    public MainView() {

        createUploadFolderIfNotExists();
        setSizeFull();

        Upload upload = new Upload((Receiver) (filename, mimeType) -> {
            File file = new File(new File(IMAGE_FOLDER), filename);
            try {
                return new FileOutputStream(file);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
                return null;
            }
        });

        add(upload);
    }

    private static File createUploadFolderIfNotExists() {
        File folder = new File(IMAGE_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }
}
