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
    private static final By refreshBTN =By.xpath("//android.widget.ImageView[@resource-id='com.steris.vnc:id/ivRefresh']");

    // Locators for Password Prompt elements
    private static final By passwordField = By.xpath("//android.widget.EditText[@resource-id='com.steris.vnc:id/password']");
    private static final By togglePasswordVisibilityButton = By.xpath("//android.widget.ImageView[@content-desc='Toggle Password Visibility']");
    private static final By invalidPasswordMessage = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/invalidPass']");
    private static final By passwordPromptTitle = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/tvTitle']");
    private static final By cancelButton = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/btnCancel']");
    private static final By connectButton = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/btnConnect']");

    // Method to click the "Connect" button on the start page
    public void clickConnectButton() {
        WebElement connectBtn = waitHelper.waitForElementToBeVisible(connectButton, 10);
        if (connectBtn != null && connectBtn.isDisplayed()) {
            log.info("Clicking on Connect Button on Start Page...");
            connectBtn.click();
        } else {
            log.error("Connect Button on Start Page is not found!");
        }
    }


    // Method to check if Password Prompt is displayed
    public boolean isPasswordPromptDisplayed() {
        WebElement promptTitle = waitHelper.waitForElementToBeVisible(passwordPromptTitle, 10);
        return promptTitle != null && promptTitle.isDisplayed();
    }

    // Method to enter password
    public void enterPassword(String password) {
        WebElement passwordInput = waitHelper.waitForElementToBeVisible(passwordField, 10);
        if (passwordInput != null && passwordInput.isDisplayed()) {
            log.info("Entering password...");
            passwordInput.sendKeys(password);
        } else {
            log.error("Password field is not found!");
        }
    }

    // Method to click Connect button on Password Prompt
    public void clickPasswordPromptConnectButton() {
        WebElement connectBtn = waitHelper.waitForElementToBeVisible(connectButton, 10);
        if (connectBtn != null && connectBtn.isDisplayed()) {
            log.info("Clicking on Connect Button in Password Prompt...");
            connectBtn.click();
        } else {
            log.error("Connect Button in Password Prompt is not found!");
        }
    }

    // Method to get invalid password message
    public String getInvalidPasswordMessage() {
        WebElement messageElement = waitHelper.waitForElementToBeVisible(invalidPasswordMessage, 10);
        if (messageElement != null && messageElement.isDisplayed()) {
            String message = messageElement.getText();
            log.info("Reason for not connection : " + message);
            return message;
        } else {
            log.warn("Alert Message is not displayed.");
            return null;
        }
    }

    // Method to get device name and IP from Password Prompt title
    public String getDeviceNameAndIPFromPrompt() {
        WebElement titleElement = waitHelper.waitForElementToBeVisible(passwordPromptTitle, 10);
        if (titleElement != null && titleElement.isDisplayed()) {
            String titleText = titleElement.getText();
            log.info("Password Prompt Title: " + titleText);
            return titleText;
        } else {
            log.warn("Password Prompt Title is not displayed.");
            return null;
        }
    }

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
