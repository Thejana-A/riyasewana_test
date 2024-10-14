package page_object_model.utilities;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PDFUtility {

    public static void createPDF(String logFilePath, String pdfFilePath) {
        try (PdfWriter writer = new PdfWriter(pdfFilePath);
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc)) {

            // Initial y position for text
            float yPosition = 750;
            float margin = 50;
            float lineHeight = 15;

            try (BufferedReader br = new BufferedReader(new FileReader(logFilePath))) {
                String line;

                while ((line = br.readLine()) != null) {
                    // Create a new paragraph with the text
                    Paragraph paragraph = new Paragraph(new Text(line).setFontSize(12));
                    document.add(paragraph);

                    // Adjust position for the next line
                    yPosition -= lineHeight;

                    // Check if we need a new page
                    if (yPosition < margin) {
                        document.add(new Paragraph()); // Add a blank paragraph to create space
                        pdfDoc.addNewPage(); // Add a new page
                        yPosition = 750; // Reset position for the new page
                    }
                }
            }

            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
