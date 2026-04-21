package base;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class ScreenshotUtil {
    private static final String SCREENSHOT_FOLDER = "screenshots";
    private static final String REPORT_FOLDER = "reports";
    private static final List<String> screenshotPaths = new ArrayList<>();
    public static String captureScreenshot(WebDriver driver, String testName) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = testName + "_" + timeStamp + ".png";
        String filePath = SCREENSHOT_FOLDER + File.separator + fileName;
        try {
            File folder = new File(SCREENSHOT_FOLDER);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            FileUtils.copyFile(srcFile, destFile);
            screenshotPaths.add(destFile.getAbsolutePath());
            System.out.println("Screenshot saved: " + destFile.getAbsolutePath());
            return destFile.getAbsolutePath();
        } catch (Exception e) {
            System.out.println("Screenshot capture failed: " + e.getMessage());
            return null;
        }
    }
    public static void generatePdfFromScreenshots(String pdfName) {
        if (screenshotPaths.isEmpty()) {
            System.out.println("No screenshots available to create PDF.");
            return;
        }
        File pdfFolder = new File(REPORT_FOLDER);
        if (!pdfFolder.exists()) {
            pdfFolder.mkdirs();
        }
        String pdfPath = REPORT_FOLDER + File.separator + pdfName + ".pdf";
        try (PDDocument document = new PDDocument()) {
            PDType1Font fontBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            PDType1Font fontNormal = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            PDType1Font fontItalic = new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE);
            // Cover page
            PDPage coverPage = new PDPage(PDRectangle.A4);
            document.addPage(coverPage);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, coverPage)) {
                contentStream.beginText();
                contentStream.setFont(fontBold, 24);
                contentStream.newLineAtOffset(150, 750);
                contentStream.showText("Automation Test Report");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(fontBold, 18);
                contentStream.newLineAtOffset(170, 710);
                contentStream.showText(pdfName);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(fontNormal, 12);
                contentStream.newLineAtOffset(170, 660);
                contentStream.showText("Generated On: "
                        + new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a").format(new Date()));
                contentStream.endText();
                contentStream.beginText();
                contentStream.setFont(fontNormal, 12);
                contentStream.newLineAtOffset(170, 635);
                contentStream.showText("Total Screenshots: " + screenshotPaths.size());
                contentStream.endText();
                contentStream.beginText();
                contentStream.setFont(fontItalic, 12);
                contentStream.newLineAtOffset(90, 580);
                contentStream.showText("This report contains screenshots captured during automation test execution.");
                contentStream.endText();
            }
            // Screenshot pages
            int pageNumber = 1;
            for (String imagePath : screenshotPaths) {
                File imageFile = new File(imagePath);
                if (!imageFile.exists()) {
                    continue;
                }
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);
                PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, document);
                float pageWidth = page.getMediaBox().getWidth();
                float pageHeight = page.getMediaBox().getHeight();
                float margin = 40;
                float titleTop = pageHeight - 30;
                float fileNameTop = pageHeight - 48;
                float footerY = 20;
                float availableWidth = pageWidth - (2 * margin);
                float availableHeight = pageHeight - 140;
                float imageWidth = pdImage.getWidth();
                float imageHeight = pdImage.getHeight();
                float scale = Math.min(availableWidth / imageWidth, availableHeight / imageHeight);
                float finalWidth = imageWidth * scale;
                float finalHeight = imageHeight * scale;
                float x = (pageWidth - finalWidth) / 2;
                float y = (pageHeight - finalHeight) / 2 - 20;
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    // Page title
                    contentStream.beginText();
                    contentStream.setFont(fontBold, 14);
                    contentStream.newLineAtOffset(50, titleTop);
                    contentStream.showText("Screenshot " + pageNumber);
                    contentStream.endText();

                    // File name
                    contentStream.beginText();
                    contentStream.setFont(fontNormal, 10);
                    contentStream.newLineAtOffset(50, fileNameTop);
                    contentStream.showText(imageFile.getName());
                    contentStream.endText();
                    // Image
                    contentStream.drawImage(pdImage, x, y, finalWidth, finalHeight);
                    // Footer
                    contentStream.beginText();
                    contentStream.setFont(fontItalic, 10);
                    contentStream.newLineAtOffset(pageWidth - 90, footerY);
                    contentStream.showText("Page " + (pageNumber + 1));
                    contentStream.endText();
                }

                pageNumber++;
            }
            document.save(pdfPath);
            System.out.println("PDF created successfully: " + new File(pdfPath).getAbsolutePath());

        } catch (IOException e) {
            System.out.println("PDF creation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}