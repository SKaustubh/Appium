package test;

import base.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ExtentReportManager;
import utils.LoggerUtility;
import utils.WaitHelper;
import com.aventstack.extentreports.ExtentTest;

public class LoginTest extends BaseTest {
    LoginPage loginPage;
    private static final org.apache.logging.log4j.Logger log = LoggerUtility.getLogger(LoginTest.class);
    ExtentTest test;
    WaitHelper waitHelper;

    @BeforeClass
    public void setUpTest() throws Exception {
        // Initialize Extent Report
        ExtentReportManager.createInstance("LoginTestReport");
        test = ExtentReportManager.createTest("Login Test");

        // Setup WebDriver and necessary pages
        setup();
        loginPage = new LoginPage(driver);

        // Initialize WaitHelper
        waitHelper = new WaitHelper(driver);

        log.info("Setup complete, starting the Login Test.");
    }

    @Test
    public void testLogin() {
        log.info("Starting the login process...");




        waitHelper.waitForElementClickable(loginPage.getStartButton());
        loginPage.clickStartButton();


        test.pass("Login button clicked successfully!");
        log.info("Login button clicked successfully!");
    }

    @AfterClass
    public void tearDownTest() {
        log.info("Test completed, tearing down.");

        // Capture report and cleanup
        ExtentReportManager.flushReport();
        tearDown();
    }
}
