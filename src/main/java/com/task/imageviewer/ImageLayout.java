package com.task.imageviewer;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.io.File;
import java.io.IOException;

public class ImageLayout extends HorizontalLayout {
    private final MainView view;

    public ImageLayout(MainView view, File thumbnailFile) throws IOException {
        this.view = view;

        VerticalLayout vLayout = new VerticalLayout();
        Image image = ImageGenerator.generateImage(thumbnailFile);
        image.addClickListener(event -> showFile(thumbnailFile));

        vLayout.add(image);
        Label fileName = new Label(thumbnailFile.getName());
        fileName.getStyle().set("font-size", "10px");
        vLayout.add(fileName);
        vLayout.getStyle().set("padding-right", "0px");

        Icon deleteIcon = new Icon(VaadinIcon.CLOSE);
        deleteIcon.getStyle().set("padding-left", "0px");
        deleteIcon.addClickListener(event -> {
            File file = new File(new File(view.getImageFolder()), thumbnailFile.getName());
            file.delete();
            thumbnailFile.delete();
            view.getService().deleteImageInfo(thumbnailFile.getName());
            view.refreshImagesLayout();
        });
        add(vLayout, deleteIcon);
    }
    private void showFile(File thumbnailFile) {
        Dialog dialog = new Dialog();
        Icon closeIcon = new Icon(VaadinIcon.CLOSE);
        closeIcon.addClickListener(event -> dialog.close());
        File file = new File(new File(view.getImageFolder()), thumbnailFile.getName());
        Image image = ImageGenerator.generateImage(file);
        image.setMaxHeight("700px");
        image.setMaxWidth("1000px");
        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.add(closeIcon, image);
        dialogLayout.setHorizontalComponentAlignment(Alignment.END, closeIcon);
        dialogLayout.getStyle().set("padding-top", "0px");
        dialog.add(dialogLayout);
        dialog.open();
    }
}
