package pages;


import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.LoggerUtility;
import org.apache.logging.log4j.Logger;
import utils.WaitHelper;
import java.util.List;

public class AllDeviceListPage {

    AndroidDriver driver;
    private static final Logger log = LoggerUtility.getLogger(AllDeviceListPage.class);
    WaitHelper waitHelper;

    // Constructor
    public AllDeviceListPage(AndroidDriver driver) {
        this.driver = driver;
        waitHelper = new WaitHelper(driver);
    }

    // Locators present on the screen
    private static final By refreshButton = By.xpath("//android.widget.ImageView[@resource-id='com.steris.vnc:id/ivRefresh']");
    private static final By connectedDevicesButton = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/tvConnectedDevice']");
    private static final By addDeviceText = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/tvConnectedDevice']");
    private static final By addDeviceButton = By.xpath("//android.widget.ImageView[@resource-id='com.steris.vnc:id/imageView2']");
    private static final By allDevicesList = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/tvDevice']");
    private static final By allDeviceEditButtons = By.xpath("//android.widget.ImageView[@resource-id='com.steris.vnc:id/ivDevice']");
    private static final By allIpAddresses = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/tvIpAddress']");
    private static final By allConnectButtons = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/btnConnect']");


    public boolean isRefreshButtonVisible() {
        WebElement element = waitHelper.waitForElementToBeVisible(refreshButton, 30);
        return element != null && element.isDisplayed();
    }

    public boolean isConnectedDevicesButtonVisible() {
        WebElement element = waitHelper.waitForElementToBeVisible(connectedDevicesButton, 30);
        return element != null && element.isDisplayed();
    }

    public boolean isAddDeviceTextVisible() {
        WebElement element = waitHelper.waitForElementToBeVisible(addDeviceText, 30);
        return element != null && element.isDisplayed();
    }

    public boolean isAddDeviceButtonVisible() {
        WebElement element = waitHelper.waitForElementToBeVisible(addDeviceButton, 30);
        return element != null && element.isDisplayed();
    }

    public boolean isAllDevicesListVisible() {
        WebElement element = waitHelper.waitForElementToBeVisible(allDevicesList, 30);
        return element != null && element.isDisplayed();
    }

    public boolean isEditButtonVisible() {
        WebElement element = waitHelper.waitForElementToBeVisible(allDeviceEditButtons, 30);
        return element != null && element.isDisplayed();
    }

    public boolean isIpAddressTextVisible() {
        WebElement element = waitHelper.waitForElementToBeVisible(allIpAddresses, 30);
        return element != null && element.isDisplayed();
    }

    public boolean isConnectButtonVisible() {
        WebElement element = waitHelper.waitForElementToBeVisible(allConnectButtons, 30);
        return element != null && element.isDisplayed();
    }

    public void printAllDevices() {
        // Find all device containers excluding "Add Device" container
        List<WebElement> deviceContainers = driver.findElements(By.xpath("(//android.view.ViewGroup[@resource-id='com.steris.vnc:id/clBg'])[position() > 1]"));

        if (deviceContainers.isEmpty()) {
            log.info("No devices found in the list.");
            return;
        }

        log.info("Total Devices Found: " + deviceContainers.size());

        // Iterate over each container and print device details
        for (WebElement container : deviceContainers) {
            try {
                // Extract device name (from the text)
                WebElement deviceNameElement = container.findElement(By.xpath(".//android.widget.TextView[@resource-id='com.steris.vnc:id/tvDevice']"));
                String deviceName = deviceNameElement.getText().trim();

                // Extract IP address (from the text)
                WebElement ipAddressElement = container.findElement(By.xpath(".//android.widget.TextView[@resource-id='com.steris.vnc:id/tvIpAddress']"));
                String ipAddress = ipAddressElement.getText().trim();

                // Log the device name and IP address
                log.info("Device Name: " + deviceName + " | IP Address: " + ipAddress);
            } catch (Exception e) {
                log.error("Error retrieving device details: " + e.getMessage());
            }
        }
        }
}
