package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportManager {

    private static ExtentReports extentReports;
    private static ExtentTest extentTest;
    private final static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static String testReportFolderPath;

    private static String generateReportFolderPath() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        return "test-reports/report_TSB_" + timestamp + "/";
    }

    public static String getTestReportFolderPath() {
        return testReportFolderPath;
    }

    public static ExtentReports getInstance() {
        if (extentReports == null) {
            createInstance();
        }
        return extentReports;
    }

    private static void createInstance() {
        testReportFolderPath = generateReportFolderPath();
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(testReportFolderPath + "ExtentReport.html");

        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("TSB Automation Report");
        sparkReporter.config().setReportName("TSB Automation Report");
        sparkReporter.config().setEncoding("utf-8");

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Application", "Trade Me Sandbox");
        extentReports.setSystemInfo("Environment", "Test");
        extentReports.setSystemInfo("Browser", "Chrome");
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    private static void setTest(ExtentTest extentTest) {
        test.set(extentTest);
    }

    public static void startTest(String testName, String description) {
        extentTest = extentReports.createTest(testName, description);
        setTest(extentTest);
    }

    public static void startTestWithFailureLog(String methodName, String failureMessage) {
        extentTest = extentReports.createTest(methodName);
        extentTest.fail(failureMessage);
        setTest(extentTest);
    }

    public static void logFailureAndPublishScreenshot(WebDriver driver, String methodName, Throwable throwableMessage) {
        try {
            getTest().fail(throwableMessage);
            Media screenshotFile = MediaEntityBuilder.createScreenCaptureFromPath(
                            FailureScreenshot.takeScreenshot(driver, methodName)).build();
            getTest().fail(methodName + " failed", screenshotFile);
        } catch (Exception e) {
            getTest().fail(throwableMessage);
        }
    }

    public static void flush() {
        if (extentReports != null) {
            extentReports = null;
        }
    }
}