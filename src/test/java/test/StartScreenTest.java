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
    public void setUpTest() {
        log.info("Setting up Start Screen Test...");

        // Use the same Extent Report instance
        test = ExtentReportManager.createTest("Start Screen Test");

        // Initialize page objects (driver is already initialized in BaseTest)
        startScreen = new StartScreen(driver);
        waitHelper = new WaitHelper(driver);

        log.info("Setup complete, starting the Start Screen test.");
    }

    @Test
    public void testLogin() {
        log.info("Starting Start Screen Test...");

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
        log.info("Start screen test completed.");

    }
}
