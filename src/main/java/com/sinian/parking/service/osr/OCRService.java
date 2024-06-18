package com.sinian.parking.service.osr;


import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

@Service
public class OCRService {
    private final ITesseract tesseract;

    public OCRService() {
        tesseract = new Tesseract();
        // Укажите правильный путь к tessdata
        tesseract.setDatapath("src/main/resources/tessdata");
        tesseract.setLanguage("eng"); // Установите нужный язык распознавания
    }
    public String recognizeLicensePlate(InputStream inputStream) throws TesseractException {
        try {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            return tesseract.doOCR(bufferedImage);
        } catch (Exception e) {
            throw new TesseractException("Failed to read image from input stream", e);
        }
    }
}
