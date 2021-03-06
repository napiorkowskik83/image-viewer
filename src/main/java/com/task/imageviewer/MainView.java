package com.task.imageviewer;

import com.task.imageviewer.domain.ImageInfo;
import com.task.imageviewer.service.ImageInfoService;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import elemental.json.Json;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Route("")
public class MainView extends VerticalLayout {
    private static final String IMAGE_FOLDER = "uploaded-images";
    private static final String THUMBNAIL_FOLDER = "uploaded-thumbnails";
    private File uploadedFile;
    private String uploadedFileName;
    private final VerticalLayout imagesLayout = new VerticalLayout();
    private final ImageInfoService service;

    @Autowired
    public MainView(ImageInfoService service) {
        this.service = service;

        createUploadFolderIfNotExists();
        createThumbnailFolderIfNotExists();
        setSizeFull();

        Label infoLabel = new Label("Enter image name to enable upload:");
        infoLabel.getStyle().set("font-weight", "bold");

        TextField textField = new TextField();

        Upload upload = new Upload((Receiver) (filename, mimeType) -> {
            if (isImage(filename)) {
                uploadedFileName = textField.getValue() + filename.substring(filename.length() - 4);
                uploadedFile = new File(new File(IMAGE_FOLDER), uploadedFileName);
                try {
                    return new FileOutputStream(uploadedFile);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                    return null;
                }
            } else {
                Notification.show("You've tried to upload file which is not an image!");
                return null;
            }
        });
        upload.setVisible(false);

        upload.addStartedListener(e -> upload.getElement().setPropertyJson("files",
                Json.createArray()));
        upload.addSucceededListener(e -> {
            upload.setVisible(false);
            File thumbnail = new File(new File(THUMBNAIL_FOLDER), uploadedFileName);
            ThumbnailGenerator.createFromImageFile(uploadedFile, thumbnail);
            service.saveImageInfo(new ImageInfo(uploadedFileName));
            refreshImagesLayout();
        });

        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.addValueChangeListener(e -> upload.setVisible(textField.getValue().length() > 0));

        HorizontalLayout uploadLayout = new HorizontalLayout(infoLabel, textField, upload);
        uploadLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        refreshImagesLayout();
        add(uploadLayout, imagesLayout);

        imagesLayout.getStyle().set("padding-top", "3em");
        getStyle().set("padding-left", "4em");
        getStyle().set("padding-top", "3em");
    }

    private void createUploadFolderIfNotExists() {
        File folder = new File(IMAGE_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    private void createThumbnailFolderIfNotExists() {
        File folder = new File(THUMBNAIL_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    private boolean isImage(String fileName) {
        return fileName.toLowerCase().endsWith(".png")
                || fileName.toLowerCase().endsWith(".jpg")
                || fileName.toLowerCase().endsWith(".gif");
    }

    protected void refreshImagesLayout() {
        imagesLayout.removeAll();
        File[] thumbnailFiles = new File(THUMBNAIL_FOLDER).listFiles();
        assert thumbnailFiles != null;
        for (File file : thumbnailFiles) {
            try {
                imagesLayout.add(new ImageLayout(this, file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected String getImageFolder() {
        return IMAGE_FOLDER;
    }

    public ImageInfoService getService() {
        return service;
    }
}
