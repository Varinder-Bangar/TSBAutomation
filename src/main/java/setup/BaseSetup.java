package setup;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ExtentReportManager;

public class BaseSetup {

    public WebDriver driver;
    private static final String BASE_URL = "https://www.tmsandbox.co.nz/";

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        driver = new ChromeDriver(options);
        driver.get(BASE_URL);
    }

    protected void given(String message) {
        log("<b>GIVEN</b> " + message);
    }

    protected void when(String message) {
        log("<b>WHEN</b> " + message);
    }

    protected void then(String message) {
        log("<b>THEN</b> " + message);
    }

    protected void and(String message) {
        log("<b>AND</b> " + message);
    }

    protected void log(String message) {
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.INFO, message);
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}