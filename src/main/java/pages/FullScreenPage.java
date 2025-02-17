package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import utils.DisconnectedPopupHandler;
import utils.LoggerUtility;
import org.apache.logging.log4j.Logger;
import utils.WaitHelper;

public class FullScreenPage {

    AndroidDriver driver;
    private static final Logger log = LoggerUtility.getLogger(FullScreenPage.class);
    WaitHelper waitHelper;
    DisconnectedPopupHandler popupHandler;

    // Constructor
    public FullScreenPage(AndroidDriver driver) {
        this.driver = driver;
        waitHelper = new WaitHelper(driver);
        popupHandler = new DisconnectedPopupHandler(driver);
    }

    public void handleDisconnectedPopupIfPresent() {
        popupHandler.handlePopupIfPresent();
    }

    //  Locators for Full-Screen Page elements
    private static final By connectedDevicesButton = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/tvConnectedDevice']");
    private static final By deviceName = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/tvName']");
    private static final By onlineIndicator = By.xpath("//android.widget.ImageView[@resource-id='com.steris.vnc:id/ivIndicator']");
    private static final By keyboardIcon = By.xpath("//android.widget.ImageView[@resource-id='com.steris.vnc:id/ivKeyboard']");
    private static final By mirroredFrame = By.xpath("//android.view.ViewGroup[@resource-id='com.steris.vnc:id/constraintLayout']");
    private static final By disconnectButton = By.xpath("//android.widget.ImageView[@resource-id='com.steris.vnc:id/ivDisconnect']");
    private static final By popUPdisconnectBTN = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/btnDisconnect']");
    private static final By DisconnectPOPup = By.xpath("//android.widget.FrameLayout[@resource-id='com.steris.vnc:id/cvPopup']");

    // Locators for Password Prompt elements
    private static final By passwordField = By.xpath("//android.widget.EditText[@resource-id='com.steris.vnc:id/password']");
    private static final By togglePasswordVisibilityButton = By.xpath("//android.widget.ImageView[@content-desc='Toggle Password Visibility']");
    private static final By invalidPasswordMessage = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/invalidPass']");
    private static final By passwordPromptTitle = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/tvTitle']");
    private static final By cancelButton = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/btnCancel']");
    private static final By connectButton = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/btnConnect']");

    // Method to check if Password Prompt is displayed
    public boolean isPasswordPromptDisplayed() {
        try {
            WebElement promptTitle = waitHelper.waitForElementToBeVisible(passwordPromptTitle, 5);
            return promptTitle != null && promptTitle.isDisplayed();
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for Password Prompt to be visible.");
            return false;
        } catch (NoSuchElementException e) {
            return false;
        } catch (Exception e) {
            log.error("Unexpected error in isPasswordPromptDisplayed: " + e.getMessage());
            return false;
        }
    }

    // Method to enter password
    public void enterPassword(String password) {
        try {
            if (isPasswordPromptDisplayed()) {
                WebElement passwordInput = waitHelper.waitForElementToBeVisible(passwordField, 10);
                passwordInput.sendKeys(password);
                log.info("Password entered successfully.");
            }
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for password field.");
        } catch (Exception e) {
            log.error("Error entering password: " + e.getMessage());
        }
    }

    // Method to click Connect button on Password Prompt
    public void clickPasswordPromptConnectButton() {
        try {
            if (isPasswordPromptDisplayed()) {
                WebElement connectBtn = waitHelper.waitForElementToBeVisible(connectButton, 10);
                connectBtn.click();
                log.info("Clicked Connect button in password prompt.");
            }
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for Connect button.");
        } catch (Exception e) {
            log.error("Error clicking Connect button: " + e.getMessage());
        }
    }

    // Method to click cancel button in Password Prompt
    public void clickPasswordPromptCancelButton() {
        try {
            if (isPasswordPromptDisplayed()) {
                WebElement cancelBtn = waitHelper.waitForElementToBeVisible(cancelButton, 10);
                if (cancelBtn != null && cancelBtn.isDisplayed()) {
                    log.info("Clicking on Cancel Button in Password Prompt...");
                    cancelBtn.click();
                } else {
                    log.error("Cancel Button in Password Prompt is not found!");
                }
            } else {
                log.info("Password prompt is not displayed. Skipping Cancel button click.");
            }
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for Cancel button.");
        } catch (Exception e) {
            log.error("Error clicking Cancel button: " + e.getMessage());
        }
    }

    // Method to click toggle visibility button inside password form
    public void clickVisibilityButton() {
        try {
            if (isPasswordPromptDisplayed()) {
                WebElement toggleBtn = waitHelper.waitForElementToBeVisible(togglePasswordVisibilityButton, 10);
                if (toggleBtn != null && toggleBtn.isDisplayed()) {
                    log.info("Clicking on Toggle Button in Password Prompt...");
                    toggleBtn.click();
                } else {
                    log.error("Toggle Button in Password Prompt is not found!");
                }
            } else {
                log.info("Password prompt is not displayed. Skipping Toggle button click.");
            }
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for Toggle button.");
        } catch (Exception e) {
            log.error("Error clicking Toggle button: " + e.getMessage());
        }
    }

    // Method to get invalid password message
    public String getInvalidPasswordMessage() {
        try {
            if (isPasswordPromptDisplayed()) {
                WebElement messageElement = waitHelper.waitForElementToBeVisible(invalidPasswordMessage, 10);
                if (messageElement != null && messageElement.isDisplayed()) {
                    return messageElement.getText();
                } else {
                    log.warn("Alert Message is not displayed.");
                    return null;
                }
            } else {
                log.info("Password prompt is not displayed. Skipping invalid password message retrieval.");
                return null;
            }
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for Invalid Password message.");
            return null;
        } catch (Exception e) {
            log.error("Error retrieving Invalid Password message: " + e.getMessage());
            return null;
        }
    }

    // Method to get device name and IP from Password Prompt title
    public String getDeviceNameAndIPFromPrompt() {
        try {
            if (isPasswordPromptDisplayed()) {
                WebElement titleElement = waitHelper.waitForElementToBeVisible(passwordPromptTitle, 10);
                if (titleElement != null && titleElement.isDisplayed()) {
                    return titleElement.getText();
                } else {
                    log.warn("Password Prompt Title is not displayed.");
                    return null;
                }
            } else {
                log.info("Password prompt is not displayed. Skipping device name and IP retrieval.");
                return null;
            }
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for Password Prompt title.");
            return null;
        } catch (Exception e) {
            log.error("Error retrieving device name and IP: " + e.getMessage());
            return null;
        }
    }

    // Check if Full-Screen Page is loaded
    public boolean isFullScreenLoaded() {
        try {
            WebElement frameView = waitHelper.waitForElementToBeVisible(mirroredFrame, 5);
            return frameView != null && frameView.isDisplayed();
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for Full-Screen Page to load.");
            return false;
        } catch (NoSuchElementException e) {
            log.error("Full-Screen Page did not load.");
            return false;
        } catch (Exception e) {
            log.error("Unexpected error in isFullScreenLoaded: " + e.getMessage());
            return false;
        }
    }

    // Check if the device name is visible
    public boolean isDeviceNameVisible() {
        try {
            WebElement device = waitHelper.waitForElementToBeVisible(deviceName, 30);
            return device != null && device.isDisplayed();
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for device name.");
            return false;
        } catch (Exception e) {
            log.error("Error checking device name visibility: " + e.getMessage());
            return false;
        }
    }

    // Check if Online Indicator is visible
    public boolean isOnlineIndicatorVisible() {
        try {
            WebElement indicator = waitHelper.waitForElementToBeVisible(onlineIndicator, 30);
            return indicator != null && indicator.isDisplayed();
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for online indicator.");
            return false;
        } catch (Exception e) {
            log.error("Error checking online indicator visibility: " + e.getMessage());
            return false;
        }
    }

    // Check if Keyboard Icon is visible
    public boolean isKeyboardIconVisible() {
        try {
            WebElement keyboard = waitHelper.waitForElementToBeVisible(keyboardIcon, 30);
            return keyboard != null && keyboard.isDisplayed();
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for keyboard icon.");
            return false;
        } catch (Exception e) {
            log.error("Error checking keyboard icon visibility: " + e.getMessage());
            return false;
        }
    }

    // Click on Keyboard Icon to open the keyboard
    public void clickKeyboardIcon() {
        try {
            WebElement keyboard = waitHelper.waitForElementToBeVisible(keyboardIcon, 30);
            if (keyboard != null && keyboard.isDisplayed()) {
                log.info("Clicking on Keyboard Icon...");
                keyboard.click();
            } else {
                log.error("Keyboard Icon is not found!");
            }
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for Keyboard icon.");
        } catch (Exception e) {
            log.error("Error clicking Keyboard icon: " + e.getMessage());
        }
    }

    // Disconnect from Full-Screen View
    public void disconnectFullScreen() {
        try {
            WebElement disconnect = waitHelper.waitForElementToBeVisible(disconnectButton, 30);
            if (disconnect != null && disconnect.isDisplayed()) {
                log.info("Clicking on Disconnect Button...");
                disconnect.click();
            } else {
                log.error("Disconnect Button not found!");
            }
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for Disconnect button.");
        } catch (Exception e) {
            log.error("Error clicking Disconnect button: " + e.getMessage());
        }
    }

    // Disconnect popup form appears
    public boolean disconnectPOPup() {
        try {
            WebElement disconnectPopUp = waitHelper.waitForElementToBeVisible(DisconnectPOPup, 30);
            return disconnectPopUp != null && disconnectPopUp.isDisplayed();
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for Disconnect popup.");
            return false;
        } catch (Exception e) {
            log.error("Error checking Disconnect popup: " + e.getMessage());
            return false;
        }
    }

    // Disconnect from Full-Screen View inside the Disconnect popup
    public void disconnectBTNinsidePopUPform() {
        try {
            WebElement disBTN = waitHelper.waitForElementToBeVisible(popUPdisconnectBTN, 30);
            if (disBTN != null && disBTN.isDisplayed()) {
                log.info("Clicking on Disconnect Button inside the Disconnect POP UP...");
                disBTN.click();
            } else {
                log.error("Disconnect Button not found inside Disconnect POP UP!");
            }
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for Disconnect button inside popup.");
        } catch (Exception e) {
            log.error("Error clicking Disconnect button inside popup: " + e.getMessage());
        }
    }

    // Clicking on Connected device button on the Full screen page
    public void ConnectedFullScreen() {
        try {
            WebElement connected = waitHelper.waitForElementToBeVisible(connectedDevicesButton, 30);
            if (connected != null && connected.isDisplayed()) {
                log.info("Clicking on Connected Devices Button...");
                connected.click();
            } else {
                log.error("Connected Devices Button not found!");
            }
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for Connected Devices Button.");
        } catch (Exception e) {
            log.error("Error clicking Connected Devices Button: " + e.getMessage());
        }
    }
}
