package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchResultsPage extends BasePage {

    @FindBy(css = "[class*='search-header-result']")
    private WebElement resultsHeading;

    @FindBy(xpath = "//div[@class='tm-search-header__refinements']//input[@name='search']")
    private WebElement resultRefinementsSearchBox;

    @FindBy(css = ".tm-no-results__heading")
    private WebElement noResultsMessageHeading;

    @FindBy(css = "[class*='tm-marketplace-search-card__card']")
    private List<WebElement> searchResultCards;

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public boolean resultsHeaderContainsKeyword(String searchKeyword) {
        return resultsHeading.getText().contains("results for '" + searchKeyword + "'");
    }

    public String noResultMessageHeadingText() {
        return noResultsMessageHeading.getText();
    }

    public boolean minimumOneSearchResultCardIsDisplayed() {
        return !searchResultCards.isEmpty();
    }

    @Override
    public boolean exists() {
        return isTitle("for sale | Trade Me");
    }
}