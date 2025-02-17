package test;

import base.BaseTest;
import config.ConfigReader;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AllDeviceListPage;
import pages.FullScreenPage;
import pages.MultiViewScreenPage;
import utils.DisconnectedPopupHandler;
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
    private DisconnectedPopupHandler disconnectedPopupHandler;

    @BeforeClass
    public void setUpTest() {
        log.info("Setting up MultiView Screen Page Test...");

        password = "5209";
        test = ExtentReportManager.createTest("MultiView Screen Page Test");
        multiViewScreenPage = new MultiViewScreenPage(driver);
        allDeviceListPage = new AllDeviceListPage(driver);
        fullScreenPage = new FullScreenPage(driver);
        waitHelper = new WaitHelper(driver);
        disconnectedPopupHandler = new DisconnectedPopupHandler(driver);

        log.info("Setup complete, starting the MultiView Screen Page test.");
    }

    @DataProvider(name = "deviceDataProvider")
    public Object[][] deviceDataProvider() {
        List<AllDeviceListPage.Device> deviceList = allDeviceListPage.getAllDevices();

        if (deviceList.isEmpty()) {
            return new Object[0][0];
        }

        Object[][] deviceData = new Object[deviceList.size()][1];
        for (int i = 0; i < deviceList.size(); i++) {
            deviceData[i][0] = deviceList.get(i);
        }
        return deviceData;
    }

    @Test(dataProvider = "deviceDataProvider")
    public void testMultiViewScreenFunctionality(AllDeviceListPage.Device testDevice) {
        log.info("Starting MultiView Screen Functionality Test for Device: {}", testDevice.getName());

        disconnectedPopupHandler.handlePopupIfPresent();
        multiViewScreenPage.clickConnectedDevicesButton();

        if (multiViewScreenPage.areDevicesConnected()) {
            int connectedDevicesCount = multiViewScreenPage.getConnectedDevicesCount();
            log.info("Number of connected devices: " + connectedDevicesCount);

            for (int i = 0; i < connectedDevicesCount; i++) {
                WebElement deviceElement = driver.findElements(MultiViewScreenPage.deviceContainerFrame).get(i);
                String deviceInfo = multiViewScreenPage.getDeviceNameAndIP(deviceElement);
                log.info("Device Info: " + deviceInfo);
                Assert.assertNotNull(deviceInfo, "Device info should not be null.");
            }
        } else {
            log.info("No devices connected. Proceeding to All Device List Page...");
            multiViewScreenPage.clickAllDevicesButton();
            List<AllDeviceListPage.Device> availableDevices = allDeviceListPage.getAllDevices();

            if (availableDevices.isEmpty()) {
                log.warn("No devices available in All Device List Page.");
                test.fail("No devices found to connect.");
                return;
            }

            log.info("Found {} devices in All Device List Page. Connecting them...", availableDevices.size());

            for (AllDeviceListPage.Device availableDevice : availableDevices) {
                log.info("Attempting to connect to device: {} | IP: {}", availableDevice.getName(), availableDevice.getIpAddress());
                allDeviceListPage.connectToDeviceByIp(availableDevice.getIpAddress());

                if (fullScreenPage.isPasswordPromptDisplayed()) {
                    log.info("Password prompt displayed for device: {}", availableDevice.getName());
                    fullScreenPage.clickVisibilityButton();
                    fullScreenPage.enterPassword(password);
                    fullScreenPage.clickPasswordPromptConnectButton();

                    String invalidPasswordMsg = fullScreenPage.getInvalidPasswordMessage();
                    if (invalidPasswordMsg != null) {
                        log.error("Invalid password message: {}", invalidPasswordMsg);
                        test.fail("Failed to connect due to incorrect password.");
                        fullScreenPage.clickPasswordPromptCancelButton();
                        return;
                    }
                }

                if (!fullScreenPage.isFullScreenLoaded()) {
                    log.error("Full-Screen Page did not load for device: {}", availableDevice.getName());
                    test.fail("Full-Screen Page did not load.");
                    return;
                }

                fullScreenPage.ConnectedFullScreen();
                test.pass("Clicked on connected Button.");
            }
        }

        // Ensure that MultiView Screen functionality is validated
        multiViewScreenPage.clickConnectedDevicesButton();
        test.pass("MultiView Screen Page functionality validated successfully.");
    }

    @AfterClass
    public void tearDownTest() {
        log.info("Multi View Screen Page test completed.");
    }
}
