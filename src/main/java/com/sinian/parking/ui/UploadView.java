package com.sinian.parking.ui;

import com.sinian.parking.service.osr.OCRService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


@Route("upload")
public class UploadView extends VerticalLayout {

    private final OCRService ocrService;

    private final TextField licensePlateField;
    private final Image uploadedImage;
    private final MemoryBuffer buffer;

    private Span freeSpacesLabel = new Span();

    @Autowired
    private RestTemplate restTemplate;
    private String recognizedLicensePlate;
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
        licensePlateField.setReadOnly(true);

        uploadedImage = new Image();
        uploadedImage.setMaxHeight("200px");
        uploadedImage.setMaxWidth("300px");

        Button recognizeButton = new Button("Recognize", event -> recognizeLicensePlate(buffer.getInputStream()));
        // Button to trigger vehicle entry
        Button entryButton = new Button("Entry Vehicle", event -> {
            if (recognizedLicensePlate != null) {
                try {
                    entryVehicle(recognizedLicensePlate);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Notification.show("No license plate recognized.", 3000, Notification.Position.MIDDLE);
            }
        });
        Button checkAvailabilityButton = new Button("Check Availability", event -> {
            int availableSpaces = getAvailableParkingSpaces();
            freeSpacesLabel.setText("Available Parking Spaces: " + availableSpaces);
        });
        // Add components to the layout
        add(upload, uploadedImage, recognizeButton, licensePlateField, entryButton,checkAvailabilityButton);
    }

    private void processUpload(InputStream inputStream) throws IOException {
        // Display uploaded image
        byte[] imageBytes = inputStream.readAllBytes();
        StreamResource resource = new StreamResource("uploaded-image", () -> new ByteArrayInputStream(imageBytes));
        uploadedImage.setSrc(resource);
    }

    private void recognizeLicensePlate(InputStream inputStream) {
        try {
            recognizedLicensePlate = ocrService.recognizeLicensePlate(inputStream);
            licensePlateField.setValue(recognizedLicensePlate);
            Notification.show("License plate recognized: " + recognizedLicensePlate);
            System.out.println("Recognized License Plate: " + recognizedLicensePlate);
        } catch (TesseractException e) {
            e.printStackTrace();
            Notification.show("Error recognizing license plate", 3000, Notification.Position.MIDDLE);
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Unexpected error occurred", 3000, Notification.Position.MIDDLE);
        }
    }

    public void entryVehicle(String licensePlate) throws IOException {
        URL url = new URL("http://localhost:8080/entry"); // URL вашего Endpoint на сервере
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "text/plain");
        con.setDoOutput(true);

        String requestBody = licensePlate;

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int status = con.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            System.out.println("Request successful");
        } else {
            System.out.println("Request failed with status: " + status);
        }
    }


    private int getAvailableParkingSpaces() {
        // URL for accessing EntryController's findAvailableParkingSpot method
        String url = "http://localhost:8080/entry/findAvailableParkingSpot"; // Update with your actual URL
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return 1; // Assuming available spot is found, return 1
        } else {
            return 0; // Return 0 if no available spot
        }
    }
}
