package setup;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeClass;

public class BaseApiSetup {

    @BeforeClass
    public void setUpApi() {
        RestAssured.baseURI = "https://api.trademe.co.nz/v1";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
}