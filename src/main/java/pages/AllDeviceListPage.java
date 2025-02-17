package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.DisconnectedPopupHandler;
import utils.LoggerUtility;
import org.apache.logging.log4j.Logger;
import utils.WaitHelper;

import java.util.ArrayList;
import java.util.List;

public class AllDeviceListPage {

    AndroidDriver driver;
    private static final Logger log = LoggerUtility.getLogger(AllDeviceListPage.class);
    WaitHelper waitHelper;
    DisconnectedPopupHandler popupHandler;

    // Constructor
    public AllDeviceListPage(AndroidDriver driver) {
        this.driver = driver;
        waitHelper = new WaitHelper(driver);
        popupHandler = new DisconnectedPopupHandler(driver);
    }

    public void handleDisconnectedPopupIfPresent() {
        popupHandler.handlePopupIfPresent();
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


    // Locator for device containers
    private static final By deviceContainersLocator = By.xpath("(//android.view.ViewGroup[@resource-id='com.steris.vnc:id/clBg'])[position() > 1]");

    // Method to retrieve all devices with their names and IP addresses
    public List<Device> getAllDevices() {
        List<Device> devices = new ArrayList<>();
        List<WebElement> deviceContainers = driver.findElements(deviceContainersLocator);

        if (deviceContainers.isEmpty()) {
            return devices;
        }

        for (WebElement container : deviceContainers) {
            try {
                String deviceName = container.findElement(By.xpath(".//android.widget.TextView[@resource-id='com.steris.vnc:id/tvDevice']")).getText().trim();
                String ipAddress = container.findElement(By.xpath(".//android.widget.TextView[@resource-id='com.steris.vnc:id/tvIpAddress']")).getText().trim();
                devices.add(new Device(deviceName, ipAddress, container));

            } catch (Exception e) {
                log.error("Error retrieving device details: " + e.getMessage());
            }
        }
        return devices;
    }

    // Method to click on the Connect button for a specific device by IP address
    public void connectToDeviceByIp(String ipAddress) {
        List<Device> devices = getAllDevices();

        for (Device device : devices) {
            if (device.getIpAddress().equalsIgnoreCase(ipAddress)) {
                log.info("Found device with : {}", ipAddress);
                WebElement connectButton = device.getContainer().findElement(By.xpath(".//android.widget.TextView[@resource-id='com.steris.vnc:id/btnConnect']"));
                if (connectButton != null) {
                    connectButton.click();
                    log.info("Clicked on the Connect button for device with IP: {}", ipAddress);
                    return ;
                } else {
                    log.error("No Connect button found for device with IP: {}", ipAddress);
                }
            }
        }
        log.error("Device with : " + ipAddress + " not found in the list.");
    }

    // Inner class to represent a Device
    public static class Device {
        private String name;
        private String ipAddress;
        private WebElement container;

        public Device(String name, String ipAddress, WebElement container) {
            this.name = name;
            this.ipAddress = ipAddress;
            this.container = container;
        }

        public String getName() {
            return name;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public WebElement getContainer() {
            return container;
        }
    }
}
