package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.LoggerUtility;
import org.apache.logging.log4j.Logger;
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

    // Method to click the Connected Devices button
    public void clickConnectedDevicesButton() {
        WebElement connectedDevicesBtn = waitHelper.waitForElementToBeVisible(connectedDevicesText, 10);
        if (connectedDevicesBtn != null && connectedDevicesBtn.isDisplayed()) {
            log.info("Clicking on Connected Devices Button...");
            connectedDevicesBtn.click();
        } else {
            log.error("Connected Devices Button is not found!");
        }
    }

    // Method to check if devices are connected (by checking if containers are present)
    public boolean areDevicesConnected() {
        List<WebElement> deviceContainers = driver.findElements(deviceContainerFrame);
        return !deviceContainers.isEmpty();
    }

    // Method to get the number of connected devices
    public int getConnectedDevicesCount() {
        List<WebElement> deviceContainers = driver.findElements(deviceContainerFrame);
        return deviceContainers.size();
    }

    // Method to get device name and IP from device container
    public String getDeviceNameAndIP(WebElement device) {
        WebElement deviceIpElement = device.findElement(deviceIp);
        return deviceIpElement.getText().trim();
    }

    // Method to click Full Screen button for a specific device
    public void clickFullScreenButton(WebElement device) {
        WebElement fullScreenBtn = device.findElement(fullScreenButton);
        if (fullScreenBtn != null && fullScreenBtn.isDisplayed()) {
            log.info("Clicking Full Screen Button...");
            fullScreenBtn.click();
        } else {
            log.error("Full Screen Button is not found!");
        }
    }

    // Method to verify if 'No Devices Connected' message is displayed
    public boolean isNoDevicesAlertDisplayed() {
        WebElement alertMessage = waitHelper.waitForElementToBeVisible(noDevicesMessage, 10);
        return alertMessage != null && alertMessage.isDisplayed();
    }

    // Method to disconnect a device from MultiView screen
    public void disconnectDevice(WebElement device) {
        WebElement disconnectBtn = device.findElement(disconnectButton);
        if (disconnectBtn != null && disconnectBtn.isDisplayed()) {
            log.info("Clicking on Disconnect Button...");
            disconnectBtn.click();
        } else {
            log.error("Disconnect Button is not found!");
        }
    }

    // Method to click the Full Focused View button (from All Device List) to navigate to Full Screen
    public void clickFullFocusedViewButton(WebElement device) {
        WebElement focusedViewBtn = device.findElement(fullFocusedViewButton);
        if (focusedViewBtn != null && focusedViewBtn.isDisplayed()) {
            log.info("Clicking on Full Focused View Button...");
            focusedViewBtn.click();
        } else {
            log.error("Full Focused View Button is not found!");
        }
    }
}
