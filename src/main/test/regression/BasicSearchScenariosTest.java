package regression;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.HomePage;
import pageobjects.SearchResultsPage;
import setup.BaseSetup;

public class BasicSearchScenariosTest extends BaseSetup {

    private HomePage homePage;
    private SearchResultsPage searchResultsPage;

    @BeforeMethod
    public void setUpPages() {
        homePage = new HomePage(driver);
        searchResultsPage = new SearchResultsPage(driver);
    }

    @Test(testName = "Search - Empty", description = "Verify empty search does not crash and handles gracefully")
    public void verifyEmptySearchHandlesGracefully() {
        given("I am on the Trade Me homepage");

        when("I submit a search with empty input");
        homePage.searchFor("");

        then("Site should stay on the homepage");
        Assert.assertTrue(homePage.exists(), "Empty search did not stay on homepage");
    }

    @Test(testName = "Search - Spaces Only", description = "Verify search with only spaces handles gracefully")
    public void verifySpacesOnlySearchHandlesGracefully() {
        given("I am on the Trade Me homepage");

        when("I search with spaces only");
        homePage.searchFor("   ");

        then("Site should navigate to search results page");
        Assert.assertTrue(searchResultsPage.exists(), "Spaces only search did not navigate to " +
                "search results");

        and("Page should return search results");
        Assert.assertTrue(searchResultsPage.exists(), "Spaces only search did not return search results");
    }

    @Test(testName = "Search - Special Characters", description = "Verify search with special characters " +
            "does not return results")
    public void verifySpecialCharactersHandledGracefully() {
        given("I am on the Trade Me homepage");

        when("I search with special characters '!@#$%^&*()'");
        homePage.searchFor("!@#$%^&*()");

        then("Site should navigate to search results page");
        Assert.assertTrue(searchResultsPage.exists(), "Special characters search did not navigate to " +
                "search results");

        and("No results found message is displayed");
        Assert.assertEquals(searchResultsPage.noResultMessageHeadingText(), "No results found",
                "No results found message is not displayed");
    }

    @Test(testName = "Search - Numeric", description = "Verify search with numbers returns results")
    public void verifyNumericSearchReturnsResults() {
        given("I am on the Trade Me homepage");

        when("I search with numbers '1234'");
        homePage.searchFor("1234");

        then("Site should navigate to search results page");
        Assert.assertTrue(searchResultsPage.exists(), "Numeric search navigates to results page");

        and("Search results header contains '1234'");
        Assert.assertTrue(searchResultsPage.resultsHeaderContainsKeyword("1234"),
                "Search results header does not contain '1234'");

        and("Result cards are displayed");
        Assert.assertTrue(searchResultsPage.minimumOneSearchResultCardIsDisplayed(), "No results are returned");
    }

    @Test(testName = "Search - Very Long String", description = "Verify search with very long string does not crash")
    public void verifyVeryLongStringHandledGracefully() {
        given("I am on the Trade Me homepage");

        when("I search with a very long string of 500 characters");
        homePage.searchFor("a".repeat(500));

        then("Site should navigate to search results page");
        Assert.assertTrue(searchResultsPage.exists(), "Very long string search navigates to results page");

        and("No results found message is displayed");
        Assert.assertEquals(searchResultsPage.noResultMessageHeadingText(), "No results found",
                "No results found message is not displayed");
    }

    @Test(testName = "Search - Single Character", description = "Verify search with a single character " +
            "may or may not return results")
    public void verifySingleCharacterSearch() {
        given("I am on the Trade Me homepage");

        when("I search with single character 'a'");
        homePage.searchFor("a");

        then("Site should navigate to search results page");
        Assert.assertTrue(searchResultsPage.exists(), "Single character search did not return results page");

        and("No results found message is displayed");
        Assert.assertEquals(searchResultsPage.noResultMessageHeadingText(), "No results found",
                "No results found message is not displayed");

        when("I search with single character 'b'");
        homePage.searchFor("b");

        then("Site should navigate to search results page");
        Assert.assertTrue(searchResultsPage.exists(), "Single character search did not return results page");

        and("Result cards are displayed");
        Assert.assertTrue(searchResultsPage.minimumOneSearchResultCardIsDisplayed(), "No results are returned");
    }
}
