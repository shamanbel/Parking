package com.sinian.parking.ui;

import com.sinian.parking.service.osr.OCRService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Route("upload")
public class UploadView extends VerticalLayout {

    private final OCRService ocrService;

    private final TextField licensePlateField;
    private final Image uploadedImage;
    private final MemoryBuffer buffer;

    @Autowired
    public UploadView(OCRService ocrService) {
        this.ocrService = ocrService;

        // Initialize components
        buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        upload.addSucceededListener(event -> {
            try {
                processUpload(buffer.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        licensePlateField = new TextField("Recognized License Plate:");
        licensePlateField.setEnabled(false);

        uploadedImage = new Image();
        uploadedImage.setMaxHeight("200px");
        uploadedImage.setMaxWidth("300px");

        Button recognizeButton = new Button("Recognize", event -> recognizeLicensePlate(buffer.getInputStream()));
        // Button to trigger vehicle entry
        Button entryButton = new Button("Entry Vehicle", event -> entryVehicle(buffer.getInputStream()));
        // Add components to the layout
        add(upload, uploadedImage, recognizeButton, licensePlateField, entryButton);
    }

    private void processUpload(InputStream inputStream) throws IOException {
        // Display uploaded image
        byte[] imageBytes = inputStream.readAllBytes();
        StreamResource resource = new StreamResource("uploaded-image", () -> new ByteArrayInputStream(imageBytes));
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

    private void entryVehicle(InputStream inputStream) {
        try {
            // Simulate file conversion if needed
            // File imageFile = convertMultipartFileToFile(image);
            // Call EntryController API to enter vehicle into the database
            Notification.show("Sending request to enter vehicle into the database...");
            // For example:
            // ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/entry", imageFile, String.class);
            // Handle response as needed
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Error processing entry request", 3000, Notification.Position.MIDDLE);
        }
    }
}
