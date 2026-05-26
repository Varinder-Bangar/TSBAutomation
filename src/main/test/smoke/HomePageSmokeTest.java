package smoke;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.HomePage;
import setup.BaseSetup;

public class HomePageSmokeTest extends BaseSetup {

    private HomePage homePage;

    @BeforeMethod
    public void setUpPages() {
        homePage = new HomePage(driver);
    }

    @Test(testName = "Smoke - Home Page Loads", description = "Checks whether homepage loads successfully or not.")
    public void verifyHomePageLoads() {
        given("I go to Trade Me sandbox url in Chrome");

        then("Home page should load successfully with title- " + driver.getTitle());
        Assert.assertTrue(homePage.exists(), "Couldn't load homepage");

        and("Trade Me logo is displayed");
        Assert.assertTrue(homePage.isTradeMeLogoDisplayed(), "Trade Me logo is not displayed");
    }

    @Test(testName = "Smoke - Search Box And Button Are Present", description = "Checks whether search box " +
            "and search button is present on home page")
    public void verifySearchBoxAndButtonAreDisplayed() {
        given("I am on the Trade Me homepage");

        then("Search box should be displayed");
        Assert.assertTrue(homePage.isSearchBoxDisplayed(), "Search box is not displayed on homepage");

        and("Search button should be displayed");
        Assert.assertTrue(homePage.isSearchButtonDisplayed(), "Search button is not displayed on homepage");
    }
}