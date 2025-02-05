package test;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.StartScreen;
import utils.ExtentReportManager;
import utils.LoggerUtility;
import utils.WaitHelper;
import com.aventstack.extentreports.ExtentTest;

public class StartScreenTest extends BaseTest {
    StartScreen startScreen;
    private static final org.apache.logging.log4j.Logger log = LoggerUtility.getLogger(StartScreenTest.class);
    ExtentTest test;
    WaitHelper waitHelper;

    @BeforeClass
    public void setUpTest() throws Exception {
        // Initialize Extent Report
        ExtentReportManager.createInstance("LoginTestReport");
        test = ExtentReportManager.createTest("Login Test");

        // Setup WebDriver and necessary pages
        setup();
        startScreen = new StartScreen(driver);

        // Initialize WaitHelper
        waitHelper = new WaitHelper(driver);

        log.info("Setup complete, starting the Regression Test Suite.");
    }

    @Test
    public void testLogin() {
        log.info("Starting the App process...");

        boolean isLogoVisible = startScreen.isLogoVisible();
        log.info("Checking if STERIS Logo is visible: " + isLogoVisible);
        Assert.assertTrue(isLogoVisible, "STERIS Logo is not visible");
        test.pass("STERIS Logo is visible");

        boolean isTextVisible = startScreen.isSterisTextVisible();
        log.info("Checking if STERIS text is visible: " + isTextVisible);
        Assert.assertTrue(isTextVisible, "STERIS text is not visible");
        test.pass("STERIS text is visible");

        boolean isStartButtonClickable = startScreen.isStartButtonClickable();
        log.info("Checking if Start button is clickable: " + isStartButtonClickable);
        Assert.assertTrue(isStartButtonClickable, "Start button is not clickable");
        test.pass("Start button is clickable");

        startScreen.clickStartButton();
        test.pass("Start button clicked successfully!");
        log.info("Start button clicked successfully!");
    }

    @AfterClass
    public void tearDownTest() {
        log.info("Test completed, tearing down.");

        // Capture report and cleanup
        ExtentReportManager.flushReport();
        tearDown();
    }
}
