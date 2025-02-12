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
        password = loadPasswordFromConfig();
        test = ExtentReportManager.createTest("Full Screen Page Test");
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
        log.info("Starting test for device: {} | IP: {}", device.getName(), device.getIpAddress());

        // Connect button XPath will be based on the IP address (device.getIpAddress())
        WebElement connectButton = device.getContainer().findElement(By.xpath(".//android.widget.TextView[@resource-id='com.steris.vnc:id/btnConnect']"));
        connectButton.click();

        // Handle the password prompt if displayed
        if (fullScreenPage.isPasswordPromptDisplayed()) {
            log.info("Password prompt displayed for device: {}", device.getName());
            fullScreenPage.clickVisibilityButton();
            fullScreenPage.enterPassword(password);

            String title = fullScreenPage.getDeviceNameAndIPFromPrompt();
            if (title != null) {
                log.info("Title on password form: {}", title);
                test.pass("Device title displayed correctly.");
            }

            fullScreenPage.clickPasswordPromptConnectButton();
            String invalidPasswordMsg = fullScreenPage.getInvalidPasswordMessage();
            if (invalidPasswordMsg != null) {
                log.error("Invalid password message: {}", invalidPasswordMsg);
                test.fail("Failed to connect due to: " + invalidPasswordMsg);
                fullScreenPage.clickPasswordPromptCancelButton();
                return;
            }
        }

        // Check if Full-Screen page is loaded successfully
        if (!fullScreenPage.isFullScreenLoaded()) {
            log.error("Full-Screen Page did not load for device: {}", device.getName());
            test.fail("Full-Screen Page did not load.");
            return;
        }

        log.info("Full-Screen Page loaded successfully for device: {}", device.getName());
        test.pass("Full-Screen Page loaded successfully.");

        // Verify key UI elements
        Assert.assertTrue(fullScreenPage.isDeviceNameVisible(), "Device Name is not visible.");
        test.pass("Device Name is visible.");
        Assert.assertTrue(fullScreenPage.isOnlineIndicatorVisible(), "Online Indicator is not visible.");
        test.pass("Online Indicator is visible.");
        Assert.assertTrue(fullScreenPage.isKeyboardIconVisible(), "Keyboard Icon is not visible.");
        test.pass("Keyboard Icon is visible.");

        // Click on the keyboard icon and then disconnect
        fullScreenPage.clickKeyboardIcon();
        test.pass("Clicked on Keyboard Icon.");
        fullScreenPage.clickKeyboardIcon();

        fullScreenPage.disconnectFullScreen();
        test.pass("Clicked on Disconnect Button.");

        // Handle disconnect confirmation popup
        if (fullScreenPage.disconnectPOPup()) {
            fullScreenPage.disconnectBTNinsidePopUPform();
            test.pass("Confirmed disconnection.");
        }
    }

    // Load password from config file
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
