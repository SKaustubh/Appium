package test;

import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.MultiViewScreenPage;
import utils.ExtentReportManager;
import utils.LoggerUtility;
import utils.WaitHelper;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.Logger;

public class MultiViewScreenPageTest extends BaseTest {
    private MultiViewScreenPage multiViewScreenPage;
    private static final Logger log = LoggerUtility.getLogger(MultiViewScreenPageTest.class);
    private ExtentTest test;
    private WaitHelper waitHelper;

    @BeforeClass
    public void setUpTest() {
        log.info("Setting up MultiView Screen Page Test...");

        // Use the same Extent Report instance
        test = ExtentReportManager.createTest("MultiView Screen Page Test");

        // Initialize page objects (driver is already initialized in BaseTest)
        multiViewScreenPage = new MultiViewScreenPage(driver);
        waitHelper = new WaitHelper(driver);

        log.info("Setup complete, starting the MultiView Screen Page test.");
    }

    @Test
    public void testMultiViewScreenFunctionality() {
        log.info("Starting MultiView Screen Functionality Test...");

        // Click the Connected Devices button
        multiViewScreenPage.clickConnectedDevicesButton();

        // Check if devices are connected
        if (multiViewScreenPage.areDevicesConnected()) {
            int connectedDevicesCount = multiViewScreenPage.getConnectedDevicesCount();
            log.info("Number of connected devices: " + connectedDevicesCount);

            // Iterate through each connected device
            for (int i = 0; i < connectedDevicesCount; i++) {
                WebElement device = driver.findElements(MultiViewScreenPage.deviceContainerFrame).get(i);
                String deviceInfo = multiViewScreenPage.getDeviceNameAndIP(device);
                log.info("Device Info: " + deviceInfo);

                // Click the Full Screen button for each device
                multiViewScreenPage.clickFullScreenButton(device);

                // Optionally verify full screen functionality here
                // Example: Assert.assertTrue(isFullScreenModeActive(), "Device not in full-screen mode.");
            }

            // Verify that the correct number of devices are shown in the MultiView screen
            int multiViewDeviceCount = driver.findElements(MultiViewScreenPage.deviceContainerFrame).size();
            Assert.assertEquals(multiViewDeviceCount, connectedDevicesCount, "Mismatch in the number of connected devices displayed on the MultiView screen.");
            log.info("Number of devices on MultiView screen matches the number of connected devices.");

        } else {
            log.info("No devices connected. Verifying 'No Devices Connected' alert...");

            // Verify that the 'No Devices Connected' alert is displayed
            boolean isAlertDisplayed = multiViewScreenPage.isNoDevicesAlertDisplayed();
            log.info("'No Devices Connected' alert displayed: " + isAlertDisplayed);
            Assert.assertTrue(isAlertDisplayed, "'No Devices Connected' alert not displayed when no devices are connected.");
        }

        // Additional assertions based on the full-screen view functionality
        // e.g., Check if the device name, IP, and status are visible after full-screen navigation

        test.pass("MultiView Screen Page functionality validated successfully.");
    }
}
