package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import setup.BaseSetup;

public class TestListener implements ITestListener {

    private final static ExtentReports extentReports = ExtentReportManager.getInstance();

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).testName();
        String description = result.getMethod().getDescription();

        if (testName == null || testName.isEmpty() || description == null || description.isEmpty()) {
            String methodName = result.getMethod().getMethodName();
            String message = methodName +
                    " is missing testName or description in @Test(testName = '...', description = '...')";
            ExtentReportManager.startTestWithFailureLog(methodName, message);
            result.setStatus(ITestResult.FAILURE);
        }
        ExtentReportManager.startTest(testName, "<b>Scenario:</b> " + description);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.getTest().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (ExtentReportManager.getTest() == null) {
            return;
        }

        Object testInstance = result.getInstance();
        if (!(testInstance instanceof BaseSetup) || ((BaseSetup) testInstance).driver == null) {
            ExtentReportManager.getTest().log(Status.FAIL, result.getThrowable());
            return;
        }

        WebDriver driver = ((BaseSetup) testInstance).driver;
        String methodName = result.getMethod().getMethodName();
        ExtentReportManager.logFailureAndPublishScreenshot(driver, methodName, result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getTest().log(Status.SKIP, "Test Skipped: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
        ExtentReportManager.flush();
    }
}