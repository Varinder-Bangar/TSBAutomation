package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FailureScreenshot {

    private static final String SCREENSHOT_DIR = ExtentReportManager.getTestReportFolderPath() + "screenshots/";

    public static String takeScreenshot(WebDriver driver, String testName) {

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String fileName = testName + "_" + timestamp + ".png";
        String relativePath = "screenshots/" + fileName;
        String absolutePath = SCREENSHOT_DIR + fileName;

        Path screenshotPath = Paths.get(SCREENSHOT_DIR);
        if (!Files.exists(screenshotPath)) {
            try {
                Files.createDirectories(screenshotPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File failureScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            Files.copy(failureScreenshot.toPath(), Paths.get(absolutePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return relativePath;
    }
}