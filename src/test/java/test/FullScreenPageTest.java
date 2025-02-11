package test;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AllDeviceListPage;
import pages.FullScreenPage;
import utils.ExtentReportManager;
import utils.LoggerUtility;
import utils.WaitHelper;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FullScreenPageTest extends BaseTest {
    private FullScreenPage fullScreenPage;
    private static final Logger log = LoggerUtility.getLogger(FullScreenPageTest.class);
    private ExtentTest test;
    private WaitHelper waitHelper;
    private String password;
    private AllDeviceListPage allDeviceListPage;

    @BeforeClass
    public void setUpTest() {
        log.info("Setting up Full Screen Page Test...");

        // Load password from config.properties
        password = loadPasswordFromConfig();

        // Use the same Extent Report instance
        test = ExtentReportManager.createTest("Full Screen Page Test");

        // Initialize page objects (driver is already initialized in BaseTest)
        fullScreenPage = new FullScreenPage(driver);
        waitHelper = new WaitHelper(driver);
        allDeviceListPage = new AllDeviceListPage(driver);

        log.info("Setup complete, starting the Full Screen Page test.");
    }

    @DataProvider(name = "deviceDataProvider")
    public Object[][] deviceDataProvider() {
        List<AllDeviceListPage.Device> devices = allDeviceListPage.getAllDevices();
        Object[][] deviceData = new Object[devices.size()][1];
        for (int i = 0; i < devices.size(); i++) {
            deviceData[i][0] = devices.get(i);
        }
        return deviceData;
    }

    @Test(dataProvider = "deviceDataProvider")
    public void testFullScreenPageFunctionality(AllDeviceListPage.Device device) {
        log.info("Starting Full Screen Page Functionality Test for Device: " + device.getName() + " | IP: " + device.getIpAddress());

        // Click the Connect button for the specific device
        WebElement connectButton = device.getContainer().findElement(By.xpath(".//android.widget.TextView[@resource-id='com.steris.vnc:id/btnConnect']"));
        connectButton.click();

        // Check if Password Prompt is displayed
        if (fullScreenPage.isPasswordPromptDisplayed()) {
            log.info("Password prompt is displayed for device: " + device.getName());

            // Enter the password
            fullScreenPage.enterPassword(password);

            // Click the Connect button on the password prompt
            fullScreenPage.clickPasswordPromptConnectButton();

            // Check for invalid password message
            String invalidPasswordMsg = fullScreenPage.getInvalidPasswordMessage();
            if (invalidPasswordMsg != null) {
                log.error("Alert message on the Password field: " + invalidPasswordMsg);
                Assert.fail("Failed to connect to device: " + device.getName() + " due to: " + invalidPasswordMsg);
            }
        } else {
            log.info("No password prompt displayed for device: " + device.getName() + ". Proceeding to full-screen view.");
        }

        // Verify Full-Screen Page is loaded
        boolean isFullScreenLoaded = fullScreenPage.isFullScreenLoaded();
        log.info("Full-Screen Page loaded for device: " + device.getName() + ": " + isFullScreenLoaded);
        Assert.assertTrue(isFullScreenLoaded, "Full-Screen Page did not load for device: " + device.getName());
        test.pass("Full-Screen Page loaded successfully for device: " + device.getName());

        // Verify Device Name is visible
        boolean isDeviceNameVisible = fullScreenPage.isDeviceNameVisible();
        log.info("Device Name visible: " + isDeviceNameVisible);
        Assert.assertTrue(isDeviceNameVisible, "Device Name is not visible.");
        test.pass("Device Name is visible.");

        // Verify Online Indicator is visible
        boolean isOnlineIndicatorVisible = fullScreenPage.isOnlineIndicatorVisible();
        log.info("Online Indicator visible: " + isOnlineIndicatorVisible);
        Assert.assertTrue(isOnlineIndicatorVisible, "Online Indicator is not visible.");
        test.pass("Online Indicator is visible.");

        // Verify Keyboard Icon is visible and test its functionality
        boolean isKeyboardIconVisible = fullScreenPage.isKeyboardIconVisible();
        log.info("Keyboard Icon visible: " + isKeyboardIconVisible);
        Assert.assertTrue(isKeyboardIconVisible, "Keyboard Icon is not visible.");
        test.pass("Keyboard Icon is visible.");

        fullScreenPage.clickKeyboardIcon();
        test.pass("Clicked on Keyboard Icon.");

        fullScreenPage.disconnectFullScreen();
        test.pass("Clicked on Disconnect Button.");
    }

    private String loadPasswordFromConfig() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                log.error("Unable to find config.properties");
                return null;
            }
            properties.load(input);
            return properties.getProperty("password");
        } catch (IOException ex) {
            log.error("Error reading config.properties", ex);
            return null;
        }
    }
}
