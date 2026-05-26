package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.with;

public abstract class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public abstract boolean exists();

    protected boolean isTitle(String pageTitle) {
        try {
            with().pollInterval(1, TimeUnit.SECONDS).await().atMost(30, TimeUnit.SECONDS)
                    .until(() -> driver.getTitle().contains(pageTitle));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}