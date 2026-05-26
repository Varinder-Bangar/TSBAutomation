package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{

    @FindBy(id = "search")
    private WebElement searchBox;

    @FindBy(xpath = "//button[@aria-label='Search all of Trade Me']")
    private WebElement searchButton;

    @FindBy(xpath = "//img[@title='Trade Me - Life lives here']")
    private WebElement tradeMeLogo;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isSearchBoxDisplayed() {
        return searchBox.isDisplayed();
    }

    public boolean isSearchButtonDisplayed() {
        return searchButton.isDisplayed();
    }

    public boolean isSearchButtonEnabled() {
        return searchButton.isEnabled();
    }

    public boolean isTradeMeLogoDisplayed() {
        return tradeMeLogo.isDisplayed();
    }

    public void searchFor(String searchKeyword) {
        searchBox.sendKeys(searchKeyword);
        searchButton.click();
    }

    @Override
    public boolean exists() {
        return isTitle("Buy & Sell on NZ's #1 Auction & Classifieds Site | Trade Me");
    }
}