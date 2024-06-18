package com.sinian.parking.ui;

import com.sinian.parking.service.osr.OCRService;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Route("upload")
public class UploadView extends VerticalLayout {
    @Autowired
    private OCRService ocrService;

    private TextField licensePlateField;
    private Image uploadedImage;
    private MemoryBuffer buffer;

    public UploadView(OCRService ocrService) {
        this.ocrService = ocrService;

        // Initialize components
        buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        upload.addSucceededListener(event -> processUpload(buffer.getInputStream()));

        licensePlateField = new TextField("Recognized License Plate:");
        licensePlateField.setReadOnly(true);

        uploadedImage = new Image() {
        };
        uploadedImage.setMaxHeight("200px");
        uploadedImage.setMaxWidth("300px");

        Button recognizeButton = new Button("Recognize", event -> recognizeLicensePlate(buffer.getInputStream()));

        // Add components to the layout
        add(upload, uploadedImage, recognizeButton, licensePlateField);
    }

    private void processUpload(InputStream inputStream) {
        // Display uploaded image
        StreamResource resource = new StreamResource("uploaded-image",
                () -> new ByteArrayInputStream(buffer.getInputStream().readAllBytes()));
        uploadedImage.setSrc(resource);
    }

    private void recognizeLicensePlate(InputStream inputStream) {
        try {
            String recognizedText = ocrService.recognizeLicensePlate(inputStream);
            licensePlateField.setValue(recognizedText);
            Notification.show("License plate recognized: " + recognizedText);
        } catch (TesseractException e) {
            e.printStackTrace();
            Notification.show("Error recognizing license plate", 3000, Notification.Position.MIDDLE);
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Unexpected error occurred", 3000, Notification.Position.MIDDLE);
        }
    }
}
