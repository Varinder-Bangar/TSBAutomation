package api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import setup.BaseApiSetup;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CategoriesTest extends BaseApiSetup {

    private static final String BASE_URL = "https://api.trademe.co.nz/v1";
    private static final String ALL_CATEGORIES_JSON = "/Categories.json";
    private static final String ALL_CATEGORIES_XML = "/Categories.xml";
    private static final String MOTORS_CATEGORY = "/Categories/0001-.json";
    private static final String MOTORBIKES_CATEGORY = "/Categories/0001-0026-1255-.json";
    private static final int PERFORMANCE_THRESHOLD_MS = 2000;
    SoftAssert softAssert;

    @BeforeClass
    public void setUpApi() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test(testName = "API - All Categories", description = "Verify retrieving all categories returns 200 OK")
    public void verifyAllCategoriesReturns200() {
        Response response = given().when().get(ALL_CATEGORIES_JSON);

        Assert.assertEquals(response.getStatusCode(), 200,
                "Expected 200 but got: " + response.getStatusCode());
    }

    @Test(testName = "API - All Categories JSON", description = "Verify response content type is JSON")
    public void verifyAllCategoriesJsonReturnContentInJsonFormat() {
        Response response = given().when().get(ALL_CATEGORIES_JSON);

        Assert.assertTrue(response.getContentType().contains("application/json"),
                "Expected JSON content type but got: " + response.getContentType()
        );
    }

    @Test(testName = "API - All Categories XML", description = "Verify response content type is XML")
    public void verifyAllCategoriesXmlReturnsContentInXmlFormat() {
        Response response = given().when().get(ALL_CATEGORIES_XML);

        softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), 200,
                "Expected 200 but got: " + response.getStatusCode());
        softAssert.assertTrue(response.getContentType().contains("text/xml"),
                "Expected XML content type but got: " + response.getContentType());

        softAssert.assertAll();
    }

    @Test(testName = "API - Field Data Types", description = "Verify all fields return correct data types",
    dataProvider = "getList", dataProviderClass = APIDataProviders.class)
    public void verifyFieldDataTypes(JsonArray fieldsList) {
        Response response = given().when().get(ALL_CATEGORIES_JSON);

        softAssert = new SoftAssert();
        softAssert.assertTrue(response.jsonPath().get("Name") instanceof String, "Name should be a String");
        softAssert.assertTrue(response.jsonPath().get("Number") instanceof String, "Number should be a String");
        softAssert.assertTrue(response.jsonPath().get("Path") instanceof String, "Path should be a String");

        List<String> fields = new ArrayList<>();
        for (JsonElement element : fieldsList) {
             fields.add(element.getAsString());
        }
        for (String field : fields) {
            softAssert.assertNotNull(response.jsonPath().get(field), field + " returned null value");
            softAssert.assertTrue(response.jsonPath().get(field) instanceof Boolean,
                    field + " should be a Boolean");
        }

        softAssert.assertAll();
    }

    @Test(testName = "API - Motors Subcategories", description = "Verify Motors category returns correct subcategories")
    public void verifyMotorsCategoryReturnsSubcategories() {
        Response response = given().when().get(MOTORS_CATEGORY);

        softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), 200, "Motors category did not return 200");
        softAssert.assertFalse(response.jsonPath().getList("Subcategories").isEmpty(),
                "Motors category has no subcategories");
        softAssert.assertEquals(response.jsonPath().getString("Number"), "0001-",
                "Category number does not match Motors");

        softAssert.assertAll();
    }

    @Test(testName = "API - Motorbikes Subcategory", description = "Verify Motorbikes subcategory " +
            "returns correct data")
    public void verifyMotorbikesCategoryReturnsCorrectData() {
        Response response = given().when().get(MOTORBIKES_CATEGORY);

        softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), 200,
                "Motorbikes category did not return 200");
        softAssert.assertEquals(response.jsonPath().getString("Number"), "0001-0026-1255-",
                "Category number does not match Motorbikes");

        softAssert.assertAll();
    }

    @Test(testName = "API - Depth Parameter", description = "Verify depth=0 returns single " +
            "category with no subcategories")
    public void verifyDepthZeroReturnsSingleCategory() {
        Response response = given().queryParam("depth", 0).when().get(ALL_CATEGORIES_JSON);

        softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), 200, "Depth parameter did not return 200");
        softAssert.assertNull(response.jsonPath().get("Subcategories"),
                "Depth 0 should return no subcategories");

        softAssert.assertAll();
    }

    @Test(testName = "API - Depth One", description = "Verify depth=1 returns " +
            "category with one level of subcategories")
    public void verifyDepthOneReturnsCategoryWithSubcategories() {
        Response response = given().queryParam("depth", 1).when().get(ALL_CATEGORIES_JSON);

        softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), 200, "Depth 1 did not return 200");
        softAssert.assertFalse(response.jsonPath().getList("Subcategories").isEmpty(),
                "Depth 1 should return subcategories");

        softAssert.assertAll();
    }

    @Test(testName = "API - With Counts True", description = "Verify with_counts=true returns Count " +
            "field in response")
    public void verifyWithCountsReturnsCountField() {
        Response response = given().queryParam("with_counts", true).when().get(ALL_CATEGORIES_JSON);

        softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), 200,
                "with_counts parameter did not return 200");
        softAssert.assertNotNull(response.jsonPath().get("Count"),
                "Count field should be present when with_counts=true");

        softAssert.assertAll();
    }

    @Test(testName = "API - Invalid Category Number", description = "Verify invalid category number " +
            "returns 404 Not Found")
    public void verifyInvalidCategoryReturns404() {
        Response response = given().when().get("/Categories/9999-.json");

        Assert.assertEquals(response.getStatusCode(), 404, "Invalid category did not return 404");
    }

    @Test(testName = "API - Invalid Format", description = "Verify unsupported format returns appropriate error")
    public void verifyInvalidFormatReturnsError() {
        Response response = given().when().get("/Categories.csv");

        Assert.assertNotEquals(response.getStatusCode(), 200, "Invalid format should not return 200");
    }

    @Test(testName = "API - SQL Injection", description = "Verify SQL injection attempt in category number is " +
            "handled safely")
    public void verifySqlInjectionHandledSafely() {
        Response response = given().when().get("/Categories/' OR '1'='1.json");

        softAssert = new SoftAssert();
        softAssert.assertNotEquals(response.getStatusCode(), 200,
                "SQL injection should not return successful response");
        softAssert.assertNotEquals(response.getStatusCode(), 500,
                "SQL injection should not cause server error");

        softAssert.assertAll();
    }

    @Test(testName = "API - XSS Attack", description = "Verify XSS attack in query parameter is handled safely")
    public void verifyXssAttackHandledSafely() {
        Response response = given().queryParam("depth", "<script>alert('xss')</script>").when()
                .get(ALL_CATEGORIES_JSON);

        softAssert = new SoftAssert();
        softAssert.assertNotEquals(response.getStatusCode(), 500,
                "XSS attack should not cause server error");
        softAssert.assertFalse(response.getBody().asString().contains("<script>alert('xss')</script>"),
                "Response should not reflect XSS script back");

        softAssert.assertAll();
    }

    @Test(testName = "API - Empty Category Number", description = "Verify empty category number returns root category tree")
    public void verifyEmptyCategoryNumberReturnsRoot() {
        Response response = given().when().get(ALL_CATEGORIES_JSON);

        softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), 200, "Root categories did not return 200");
        softAssert.assertFalse(response.jsonPath().getList("Subcategories").isEmpty(),
                "Root should have subcategories");

        softAssert.assertAll();
    }

    @Test(testName = "API - Response Structure", description = "Verify response structure exactly matches " +
            "API documentation")
    public void verifyResponseStructureMatchesDocumentation() {
        Response response = given().when().get(ALL_CATEGORIES_JSON);
        String responseBody = response.getBody().asString();

        softAssert = new SoftAssert();
        softAssert.assertTrue(responseBody.contains("Name"), "Name field missing from response");
        softAssert.assertTrue(responseBody.contains("Number"), "Number field missing from response");
        softAssert.assertTrue(responseBody.contains("Path"), "Path field missing from response");
        softAssert.assertTrue(responseBody.contains("Subcategories"),
                "Subcategories field missing from response");
        softAssert.assertTrue(responseBody.contains("IsRestricted"),
                "IsRestricted field missing from response");
        softAssert.assertTrue(responseBody.contains("HasLegalNotice"),
                "HasLegalNotice field missing from response");
        softAssert.assertTrue(responseBody.contains("HasClassifieds"),
                "HasClassifieds field missing from response");
        softAssert.assertTrue(responseBody.contains("AreaOfBusiness"),
                "AreaOfBusiness field missing from response");
        softAssert.assertTrue(responseBody.contains("CanHaveSecondCategory"),
                "CanHaveSecondCategory field missing from response");
        softAssert.assertTrue(responseBody.contains("CanBeSecondCategory"),
                "CanBeSecondCategory field missing from response");
        softAssert.assertTrue(responseBody.contains("IsLeaf"), "IsLeaf field missing from response");

        softAssert.assertAll();
    }

    @Test(testName = "API - Valid Subcategory Number Format", description = "Verify subcategory numbers follow " +
            "expected format")
    public void verifySubcategoryNumberFormat() {
        Response response = given().when().get(ALL_CATEGORIES_JSON);
        String firstSubcategoryNumber = response.jsonPath().getString("Subcategories[0].Number");

        softAssert = new SoftAssert();
        softAssert.assertNotNull(firstSubcategoryNumber, "Subcategory number should not be null");
        softAssert.assertTrue(firstSubcategoryNumber.contains("-"), "Category number should contain dashes: "
                        + firstSubcategoryNumber);

        softAssert.assertAll();
    }

    @Test(testName = "API - HTTPS is enforced", description = "Verify API is only accessible via HTTPS")
    public void verifyHttpsIsEnforced() {
        Response response = given().when().get("http://api.trademe.co.nz/v1/Categories.json");

        Assert.assertNotEquals(response.getStatusCode(), 200,
                "HTTP should not return 200 — HTTPS should be enforced");
    }
}