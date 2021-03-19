package com.task.imageviewer;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.value.ValueChangeMode;
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

        Label infoLabel = new Label("Enter image name to enable upload:");
        infoLabel.getStyle().set("font-weight", "bold");

        TextField textField = new TextField();

        Upload upload = new Upload((Receiver) (filename, mimeType) -> {
            String uploadedFileName = textField.getValue() + filename.substring(filename.length()-4);
            File file = new File(new File(IMAGE_FOLDER), uploadedFileName);
            try {
                return new FileOutputStream(file);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
                return null;
            }
        });
        upload.setVisible(false);

        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.addValueChangeListener(e -> {
            if(textField.getValue().length() > 0){
                upload.setVisible(true);
            } else{
                upload.setVisible(false);
            }
        });

        HorizontalLayout uploadLayout = new HorizontalLayout(infoLabel, textField, upload);
        uploadLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        add(uploadLayout);
    }

    private static File createUploadFolderIfNotExists() {
        File folder = new File(IMAGE_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }
}
