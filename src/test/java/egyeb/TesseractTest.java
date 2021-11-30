package egyeb;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.jupiter.api.Test;

import java.io.File;

class TesseractTest {

    @Test
    void ocr() throws TesseractException {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("c:\\temp\\ocr\\");
        File file = new File("image.jpg");

        // the path of your tess data folder
        // inside the extracted file
        String text
                = tesseract.doOCR(file);

        // path of your image file
        System.out.print(text);
    }

    @Test
    void tessa() throws TesseractException {
        File image = new File("c:\\temp\\ocr\\Screen-Shot-2020-02-28-at-6.29.53-PM (1).png");
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
        tesseract.setLanguage("eng");
//        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);
        tesseract.setTessVariable("user_defined_dpi", "70");
        String result = tesseract.doOCR(image);
        System.out.println(result);
    }

}
