package test;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.FetchingLoaderScreen;
import utils.ExtentReportManager;
import utils.LoggerUtility;
import utils.WaitHelper;
import com.aventstack.extentreports.ExtentTest;

public class FetchingLoaderScreenTest extends BaseTest {

    FetchingLoaderScreen fetchingLoaderScreen;
    private static final org.apache.logging.log4j.Logger log = LoggerUtility.getLogger(FetchingLoaderScreenTest.class);
    ExtentTest test;
    WaitHelper waitHelper;

    @BeforeClass
    public void setUpTest() throws Exception {
        log.info("Setting up Fetching Loader Screen test...");


        test = ExtentReportManager.createTest("Fetching Loader Screen Test");

        // Initialize page objects
        fetchingLoaderScreen = new FetchingLoaderScreen(driver);
        waitHelper = new WaitHelper(driver);
    }

    @Test
    public void testFetchingLoaderScreen() {
        log.info("Starting Fetching Loader Screen Test...");

        // Wait for elements to appear
        boolean isFetchingLogoVisible = fetchingLoaderScreen.isFetchingLogoVisible();
        Assert.assertTrue(isFetchingLogoVisible, "Fetching logo is not visible");
        test.pass("Fetching logo is visible");

        boolean isLoaderBarVisible = fetchingLoaderScreen.isLoaderBarVisible();
        Assert.assertTrue(isLoaderBarVisible, "Loader bar is not visible");
        test.pass("Loader bar is visible");

        boolean isFetchingTextVisible = fetchingLoaderScreen.isFetchingTextVisible();
        Assert.assertTrue(isFetchingTextVisible, "Fetching text is not visible");
        test.pass("Fetching text is visible");

        boolean isMessageAvailable = fetchingLoaderScreen.isMessageAvailable();
        Assert.assertTrue(isMessageAvailable, "Subtitle message is not visible");
        test.pass("Subtitle message is visible");

        log.info("Fetching Loader Screen elements validated successfully.");
    }

    @AfterClass
    public void tearDownTest() {
        log.info("Fetching Loader Screen test completed.");
    }
}
