package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.apache.logging.log4j.Logger;
import utils.LoggerUtility;
import utils.WaitHelper;
import java.util.List;

public class MultiViewScreenPage {

    AndroidDriver driver;
    private static final Logger log = LoggerUtility.getLogger(MultiViewScreenPage.class);
    WaitHelper waitHelper;

    // Constructor
    public MultiViewScreenPage(AndroidDriver driver) {
        this.driver = driver;
        waitHelper = new WaitHelper(driver);
    }

    // Locators for MultiView Screen elements
    private static final By connectedDevicesText = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/tvConnectedDevice']");
    private static final By allDevicesButton = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/tvAllDevice']");
    private static final By noDevicesMessage = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/tvDesc']");
    public static final By deviceContainerFrame = By.xpath("(//android.widget.FrameLayout[@resource-id='com.steris.vnc:id/main'])[1]/android.view.ViewGroup");
    private static final By deviceIp = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/tvDeviceIp']");
    private static final By disconnectButton = By.xpath("(//android.widget.ImageView[@resource-id='com.steris.vnc:id/ivDisconnect'])[1]");
    private static final By fullScreenButton = By.xpath("(//android.widget.ImageView[@resource-id='com.steris.vnc:id/ivBroadCast'])[1]");
    private static final By fullFocusedViewButton = By.xpath("(//android.widget.ImageView[@resource-id='com.steris.vnc:id/ivDevice'])[2]");



    // Method to click the Connected Devices button with TimeoutException handling
    public void clickConnectedDevicesButton() {
        try {
            WebElement connectedDevicesBtn = waitHelper.waitForElementToBeVisible(connectedDevicesText, 10);
            if (connectedDevicesBtn != null && connectedDevicesBtn.isDisplayed()) {
                log.info("Clicking on Connected Devices Button...");
                connectedDevicesBtn.click();
            } else {
                log.error("Connected Devices Button is not found!");
            }
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for Connected Devices Button: " + e.getMessage());
        }
    }

    // Method to click the All Devices button with TimeoutException handling
    public void clickAllDevicesButton() {
        try {
            WebElement AllDevicesBtn = waitHelper.waitForElementToBeVisible(allDevicesButton, 10);
            if (AllDevicesBtn != null && AllDevicesBtn.isDisplayed()) {
                log.info("Clicking on All Devices Button...");
                AllDevicesBtn.click();
            } else {
                log.error("All Devices Button is not found!");
            }
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for All Devices Button: " + e.getMessage());
        }
    }

    // Method to check if devices are connected with TimeoutException handling
    public boolean areDevicesConnected() {
        try {
            List<WebElement> deviceContainers = driver.findElements(deviceContainerFrame);
            return !deviceContainers.isEmpty();
        } catch (TimeoutException e) {
            log.error("Timeout while checking if devices are connected: " + e.getMessage());
            return false; // Return false if timeout occurs
        }
    }

    // Method to get the number of connected devices with TimeoutException handling
    public int getConnectedDevicesCount() {
        try {
            List<WebElement> deviceContainers = driver.findElements(deviceContainerFrame);
            return deviceContainers.size();
        } catch (TimeoutException e) {
            log.error("Timeout while getting connected devices count: " + e.getMessage());
            return 0; // Return 0 if timeout occurs
        }
    }

    // Method to get device name and IP from device container with TimeoutException handling
    public String getDeviceNameAndIP(WebElement device) {
        try {
            WebElement deviceIpElement = device.findElement(deviceIp);
            return deviceIpElement.getText().trim();
        } catch (TimeoutException e) {
            log.error("Timeout while getting device name and IP: " + e.getMessage());
            return null;
        }
    }

    // Method to click Full Screen button for a specific device with TimeoutException handling
    public void clickFullScreenButton(WebElement device) {
        try {
            WebElement fullScreenBtn = device.findElement(fullScreenButton);
            if (fullScreenBtn != null && fullScreenBtn.isDisplayed()) {
                log.info("Clicking Full Screen Button...");
                fullScreenBtn.click();
            } else {
                log.error("Full Screen Button is not found!");
            }
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for Full Screen Button: " + e.getMessage());
        }
    }

    // Method to verify if 'No Devices Connected' message is displayed with TimeoutException handling
    public boolean isNoDevicesAlertDisplayed() {
        try {
            WebElement alertMessage = waitHelper.waitForElementToBeVisible(noDevicesMessage, 10);
            return alertMessage != null && alertMessage.isDisplayed();
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for 'No Devices Connected' alert: " + e.getMessage());
            return false;
        }
    }

    // Method to disconnect a device from MultiView screen with TimeoutException handling
    public void disconnectDevice(WebElement device) {
        try {
            WebElement disconnectBtn = device.findElement(disconnectButton);
            if (disconnectBtn != null && disconnectBtn.isDisplayed()) {
                log.info("Clicking on Disconnect Button...");
                disconnectBtn.click();
            } else {
                log.error("Disconnect Button is not found!");
            }
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for Disconnect Button: " + e.getMessage());
        }
    }

    // Method to click the Full Focused View button (from All Device List) to navigate to Full Screen with TimeoutException handling
    public void clickFullFocusedViewButton(WebElement device) {
        try {
            WebElement focusedViewBtn = device.findElement(fullFocusedViewButton);
            if (focusedViewBtn != null && focusedViewBtn.isDisplayed()) {
                log.info("Clicking on Full Focused View Button...");
                focusedViewBtn.click();
            } else {
                log.error("Full Focused View Button is not found!");
            }
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for Full Focused View Button: " + e.getMessage());
        }
    }

    // Retrieve connected devices list
    public List<WebElement> getDeviceContainers() {
        return driver.findElements(deviceContainerFrame);
    }

    public By getNoDevicesMsg() {
        return noDevicesMessage;
    }
}
