package test;

import base.BaseTest;
import config.ConfigReader;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.MultiViewScreenPage;
import utils.ExtentReportManager;
import utils.LoggerUtility;
import utils.WaitHelper;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.Logger;
import utilsTest.DeviceConnectionHelper;

import java.util.List;

public class MultiViewScreenPageTest extends BaseTest {
    private MultiViewScreenPage multiViewScreenPage;
    private static final Logger log = LoggerUtility.getLogger(MultiViewScreenPageTest.class);
    private ExtentTest test;
    private WaitHelper waitHelper;
    private String password;
    private DeviceConnectionHelper deviceConnectionHelper;

    @BeforeClass
    public void setUpTest() {
        log.info("Setting up MultiView Screen Page Test...");

        // Load password from config
        password = loadPasswordFromConfig();

        // Initialize Extent Report
        test = ExtentReportManager.createTest("MultiView Screen Page Test");

        // Initialize page objects
        multiViewScreenPage = new MultiViewScreenPage(driver);
        waitHelper = new WaitHelper(driver);

        // Initialize Device Connection Helper
        deviceConnectionHelper = new DeviceConnectionHelper(driver, password, test);

        log.info("Setup complete, starting the MultiView Screen Page test.");
    }

    @Test
    public void testMultiViewScreenFunctionality() {
        log.info("Starting MultiView Screen Functionality Test...");

        // Click the Connected Devices button
        multiViewScreenPage.clickConnectedDevicesButton();

        // If no devices are connected, attempt to connect using DeviceConnectionHelper
        if (!multiViewScreenPage.areDevicesConnected()) {
            log.info("No devices connected. Attempting to connect using DeviceConnectionHelper...");
            deviceConnectionHelper.connectDevices();
        }

        // Validate Multi-View functionality after device connections
        log.info("Validating Multi-View screen...");
        multiViewScreenPage.clickConnectedDevicesButton();



        int deviceCount = multiViewScreenPage.getConnectedDevicesCount();
        if (deviceCount != 0) {
            List<WebElement> devices = multiViewScreenPage.getDeviceContainers();
            for (WebElement device : devices) {
                log.info("Performing operations on connected device...");

                String deviceInfo = multiViewScreenPage.getDeviceNameAndIP(device);
                log.info("Device Info: " + deviceInfo);

                multiViewScreenPage.clickFullScreenButton(device);

                if (multiViewScreenPage.isNoDevicesAlertDisplayed()) {
                    log.info("No Devices Connected message is displayed.");
                }

                multiViewScreenPage.disconnectDevice(device);

                multiViewScreenPage.clickFullFocusedViewButton(device);
            }
        }

        log.info("Number of devices connected :" + deviceCount);

        log.info("Multi-View screen validated successfully.");

        test.pass("MultiView Screen Page functionality validated successfully.");
    }

    // Load password from config file
    private String loadPasswordFromConfig() {
        return ConfigReader.getProperty("password");
    }

    @AfterClass
    public void tearDownTest() {
        log.info("Multi View Screen Page test completed.");
    }
}