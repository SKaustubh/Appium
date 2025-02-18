package utilsTest;

import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import pages.AllDeviceListPage;
import pages.FullScreenPage;
import pages.MultiViewScreenPage;
import com.aventstack.extentreports.ExtentTest;
import utils.LoggerUtility;

import java.util.List;

public class DeviceConnectionHelper {
    private AndroidDriver driver;
    private MultiViewScreenPage multiViewScreenPage;
    private AllDeviceListPage allDeviceListPage;
    private FullScreenPage fullScreenPage;
    private String password;
    private static final Logger log = LoggerUtility.getLogger(DeviceConnectionHelper.class);
    private ExtentTest test;

    public DeviceConnectionHelper(AndroidDriver driver, String password, ExtentTest test) {
        this.driver = driver;
        this.password = password;
        this.test = test;
        this.multiViewScreenPage = new MultiViewScreenPage(driver);
        this.allDeviceListPage = new AllDeviceListPage(driver);
        this.fullScreenPage = new FullScreenPage(driver);
    }

    public void connectDevices() {
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

            allDeviceListPage.connectToDeviceByIp(device.getIpAddress());

            if (fullScreenPage.isPasswordPromptDisplayed()) {
                log.info("Password prompt displayed for device: {}", device.getName());
                fullScreenPage.clickVisibilityButton();
                fullScreenPage.enterPassword(password);
                fullScreenPage.clickPasswordPromptConnectButton();

                String invalidPasswordMsg = fullScreenPage.getInvalidPasswordMessage();
                if (invalidPasswordMsg != null) {
                    log.error("Invalid password message: {}", invalidPasswordMsg);
                    test.fail("Failed to connect due to incorrect password.");
                    fullScreenPage.clickPasswordPromptCancelButton();
                    continue;
                }
            }

            if (!fullScreenPage.isFullScreenLoaded()) {
                log.error("Full-Screen Page did not load for device: {}", device.getName());
                test.fail("Full-Screen Page did not load.");
            } else {
                log.info("Full-Screen Page loaded successfully for device: {}", device.getName());
                test.pass("Full-Screen Page loaded successfully.");

                // Return to Multi-View Screen
                multiViewScreenPage.clickConnectedDevicesButton();
                log.info("Navigated to Connected Devices on Full-Screen Page for device {}", device.getName());
                multiViewScreenPage.clickAllDevicesButton();
                log.info("Navigated to All Devices on Full-Screen Page for device {}", device.getName());
            }
        }
    }
}
