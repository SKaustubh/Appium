package test;

import base.BaseTest;
import config.ConfigReader;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AllDeviceListPage;
import pages.FullScreenPage;
import pages.MultiViewScreenPage;
import utils.ExtentReportManager;
import utils.LoggerUtility;
import utils.WaitHelper;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MultiViewScreenPageTest extends BaseTest {
    private MultiViewScreenPage multiViewScreenPage;
    private AllDeviceListPage allDeviceListPage;
    private FullScreenPage fullScreenPage;
    private static final Logger log = LoggerUtility.getLogger(MultiViewScreenPageTest.class);
    private ExtentTest test;
    private WaitHelper waitHelper;
    private String password;
   private FullScreenPageTest fullScreenPageTest;

    @BeforeClass
    public void setUpTest() {
        log.info("Setting up MultiView Screen Page Test...");



        // Use loadPasswordFromConfig() method safely
        this.password = ConfigReader.getProperty("device.password");



        // Use the same Extent Report instance
        test = ExtentReportManager.createTest("MultiView Screen Page Test");

        // Initialize page objects
        multiViewScreenPage = new MultiViewScreenPage(driver);
        allDeviceListPage = new AllDeviceListPage(driver);
        fullScreenPage = new FullScreenPage(driver);
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

            for (int i = 0; i < connectedDevicesCount; i++) {
                WebElement device = driver.findElements(MultiViewScreenPage.deviceContainerFrame).get(i);
                String deviceInfo = multiViewScreenPage.getDeviceNameAndIP(device);
                log.info("Device Info: " + deviceInfo);

                // Verify if the device is displayed correctly
                Assert.assertNotNull(deviceInfo, "Device info should not be null.");
                log.info("Device {} is displayed correctly.", deviceInfo);
            }

            int multiViewDeviceCount = driver.findElements(MultiViewScreenPage.deviceContainerFrame).size();
            Assert.assertEquals(multiViewDeviceCount, connectedDevicesCount, "Mismatch in number of connected devices displayed on MultiView screen.");
            log.info("Number of devices on MultiView screen matches the connected devices.");
        } else {
            log.info("No devices connected. Proceeding to All Device List Page...");

            // Navigate to All Device List Page
            multiViewScreenPage.clickAllDevicesButton();
            List<AllDeviceListPage.Device> devices = allDeviceListPage.getAllDevices();

            if (devices.isEmpty()) {
                log.warn("No devices available in All Device List Page.");
                test.fail("No devices found to connect.");
                return;
            }

            log.info("Found {} devices in All Device List Page. Connecting them...", devices.size());

            for (AllDeviceListPage.Device device : devices) {
                log.info("Attempting to connect to device: {} | IP: {}", device.getName(), device.getIpAddress());

                // Connect to device
                allDeviceListPage.connectToDeviceByIp(device.getIpAddress());

                // Handle password prompt if displayed
                if (fullScreenPage.isPasswordPromptDisplayed()) {
                    log.info("Password prompt displayed for device: {}", device.getName());
                    fullScreenPage.clickVisibilityButton();
                    fullScreenPage.enterPassword(password);
                    fullScreenPage.clickPasswordPromptConnectButton();

                    String invalidPasswordMsg = fullScreenPage.getInvalidPasswordMessage();
                    if (invalidPasswordMsg != null) {
                        log.error("Invalid password message: {}", invalidPasswordMsg);
                        test.fail("Failed to connect due to incorrect password.");
                        return;
                    }
                }

                // Ensure Full-Screen Page is loaded
                if (fullScreenPage.isFullScreenLoaded()) {
                    log.error("Full-Screen Page did not load for device: {}", device.getName());
                    test.fail("Full-Screen Page did not load.");
                    return;
                }

                log.info("Full-Screen Page loaded successfully for device: {}", device.getName());
                test.pass("Full-Screen Page loaded successfully.");

                multiViewScreenPage.clickConnectedDevicesButton();
                log.info("Navigated to Connected Devices on Full-Screen Page.");
            }

            // Re-run the if-block logic after all devices are connected
            log.info("All devices are now connected. Re-running MultiView screen validation...");
            testMultiViewScreenFunctionality();
        }

        test.pass("MultiView Screen Page functionality validated successfully.");
    }

    @AfterClass
    public void tearDownTest() {
        log.info("Multi View Screen Page test completed.");
    }
}
