package pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.apache.logging.log4j.Logger;
import utils.LoggerUtility;
import utils.WaitHelper;

public class StartScreen {
    AndroidDriver driver;
    private static final Logger log = LoggerUtility.getLogger(StartScreen.class);
    WaitHelper waitHelper;

    public StartScreen(AndroidDriver driver) {
        this.driver = driver;
        waitHelper = new WaitHelper(driver);
    }

    private static final By startButtonXPath = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/btnStart']");
    private static final By logo = By.xpath("//android.widget.ImageView[@resource-id='com.steris.vnc:id/ivLogo']");
    private static final By sterisText = By.xpath("//android.widget.ImageView[@resource-id='com.steris.vnc:id/ivLogo']");

    // Explicit wait integrated method for logo visibility
    public boolean isLogoVisible() {
        WebElement logoElement = waitHelper.waitForElementToBeVisible(logo, 30); // Wait for the logo to be visible
        return logoElement != null && logoElement.isDisplayed();
    }

    // Explicit wait integrated method for Steris text visibility
    public boolean isSterisTextVisible() {
        WebElement textElement = waitHelper.waitForElementToBeVisible(sterisText, 30); // Wait for the Steris text to be visible
        return textElement != null && textElement.isDisplayed();
    }

        public WebElement getStartButton() {
        return driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/btnStart']"));
    }

    public boolean isStartButtonClickable() {
        WebElement startButton =getStartButton();
        boolean clickable = startButton.isDisplayed() && startButton.isEnabled();

        if (clickable) {
            log.info("Start button is clickable.");
        } else {
            log.error("Start button is NOT clickable.");
        }
        return clickable;
    }

    public void clickStartButton() {
        if (isStartButtonClickable()) {
            log.info("Clicking Start button...");
            getStartButton().click();
            log.info("Start button clicked successfully!");
        } else {
            log.error("Start button is not clickable.");
            throw new IllegalStateException("Start button is not clickable");
        }
    }


}