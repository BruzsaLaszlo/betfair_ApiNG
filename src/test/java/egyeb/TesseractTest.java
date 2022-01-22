package egyeb;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

class TesseractTest {

    Tesseract tesseract = new Tesseract();

    {
        // the path of your tess data folder
        tesseract.setDatapath("c:\\Program Files\\Tesseract-OCR\\tessdata");
    }

    @Test
    @DisplayName("05221859")
    void ocr() throws TesseractException {
        File file = new File("c:\\temp\\ocr\\numbers_keretes.png");

        tesseract.setLanguage("osd");
//        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(10);
//        tesseract.setVariable("user_defined_dpi", "70");

        String text = tesseract.doOCR(file);

        // path of your image file
        System.out.print(text);
    }

    @Test
    void tessa() throws TesseractException {
        File image = new File("c:\\temp\\ocr\\Screen-Shot-2020-02-28-at-6.29.53-PM (1).png");
        tesseract.setLanguage("eng");
//        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);
        tesseract.setVariable("user_defined_dpi", "70");
        String result = tesseract.doOCR(image);
        System.out.println(result);
    }

}
