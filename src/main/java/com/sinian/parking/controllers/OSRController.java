/*package com.sinian.parking.controllers;

import com.sinian.parking.service.osr.OCRService;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class OSRController {
    @Autowired
    private OCRService ocrService;

    @PostMapping("/recognize")
    public ResponseEntity<String> recognizeLicensePlate(@RequestParam("image") MultipartFile image) {
        try {
            File imageFile = convertMultipartFileToFile(image);
            String licensePlate = ocrService.recognizeLicensePlate(imageFile);
            return new ResponseEntity<>(licensePlate, HttpStatus.OK);
        } catch (TesseractException | IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        file.transferTo(convertedFile);
        return convertedFile;
    }
}*/
