package com.sinian.parking.service.osr;


import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.file.Paths;

@Service
public class OCRService {
    private final ITesseract tesseract;

    public OCRService() {
        tesseract = new Tesseract();
        String datapath = Paths.get("src", "main", "resources", "tessdata").toAbsolutePath().toString();
        tesseract.setDatapath(datapath);
        tesseract.setLanguage("eng");
    }
    public String recognizeLicensePlate(InputStream inputStream) throws TesseractException {
        try {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            return tesseract.doOCR(bufferedImage).replaceAll("\\s+","");
        } catch (Exception e) {
            throw new TesseractException("Failed to read image from input stream", e);
        }
    }
}
