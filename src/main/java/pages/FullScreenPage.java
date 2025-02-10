package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.LoggerUtility;
import org.apache.logging.log4j.Logger;
import utils.WaitHelper;

public class FullScreenPage {

    AndroidDriver driver;
    private static final Logger log = LoggerUtility.getLogger(FullScreenPage.class);
    WaitHelper waitHelper;

    // Constructor
    public FullScreenPage(AndroidDriver driver) {
        this.driver = driver;
        waitHelper = new WaitHelper(driver);
    }

    //  Locators for Full-Screen Page elements
    private static final By connectedDevicesButton = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/tvConnectedDevice']");
    private static final By deviceName = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/tvName']");
    private static final By onlineIndicator = By.xpath("//android.widget.ImageView[@resource-id='com.steris.vnc:id/ivIndicator']");
    private static final By keyboardIcon = By.xpath("//android.widget.ImageView[@resource-id='com.steris.vnc:id/ivKeyboard']");
    private static final By mirroredFrame = By.xpath("//android.view.View[@resource-id='com.steris.vnc:id/frame_view']");
    private static final By disconnectButton = By.xpath("//android.widget.ImageView[@resource-id='com.steris.vnc:id/ivDisconnect']");

    // Check if Full-Screen Page is loaded
    public boolean isFullScreenLoaded() {
        WebElement frameView = waitHelper.waitForElementToBeVisible(mirroredFrame, 30);
        return frameView != null && frameView.isDisplayed();
    }

    // Check if the device name is visible
    public boolean isDeviceNameVisible() {
        WebElement device = waitHelper.waitForElementToBeVisible(deviceName, 30);
        return device != null && device.isDisplayed();
    }

    // Check if Online Indicator is visible
    public boolean isOnlineIndicatorVisible() {
        WebElement indicator = waitHelper.waitForElementToBeVisible(onlineIndicator, 30);
        return indicator != null && indicator.isDisplayed();
    }

    // Check if Keyboard Icon is visible
    public boolean isKeyboardIconVisible() {
        WebElement keyboard = waitHelper.waitForElementToBeVisible(keyboardIcon, 30);
        return keyboard != null && keyboard.isDisplayed();
    }

    // Click on Keyboard Icon to open the keyboard
    public void clickKeyboardIcon() {
        WebElement keyboard = waitHelper.waitForElementToBeVisible(keyboardIcon, 30);
        if (keyboard != null && keyboard.isDisplayed()) {
            log.info("Clicking on Keyboard Icon...");
            keyboard.click();
        } else {
            log.error("Keyboard Icon is not found!");
        }
    }

    // Disconnect from Full-Screen View
    public void disconnectFullScreen() {
        WebElement disconnect = waitHelper.waitForElementToBeVisible(disconnectButton, 30);
        if (disconnect != null && disconnect.isDisplayed()) {
            log.info("Clicking on Disconnect Button...");
            disconnect.click();
        } else {
            log.error("Disconnect Button not found!");
        }
    }
}
