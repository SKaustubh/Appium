package pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.apache.logging.log4j.Logger;
import utils.LoggerUtility;
import utils.WaitHelper;

public class LoginPage {
    AndroidDriver driver;
    private static final Logger log = LoggerUtility.getLogger(LoginPage.class);
    WaitHelper waitHelper;

    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitHelper = new WaitHelper(driver);
    }


//    public void clickStartButton() {
//
//        AppiumBy startButtonXPath = (AppiumBy) AppiumBy.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/btnStart']");
//        try {
//            log.info("Waiting for the Start button to be clickable...");
//
//
//
//            waitHelper.waitForElementClickable(startButtonXPath);
//
//
//            WebElement startButton = driver.findElement(startButtonXPath);
//
//            log.info("Start button is clickable. Clicking now.");
//            startButton.click();
//            log.info("Start button clicked successfully!");
//
//        } catch (Exception e) {
//            log.error("Error while clicking the Start button: " + e.getMessage());
//        }
//
//    }

    public WebElement getStartButton() {
        return driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/btnStart']"));
    }

    public void clickStartButton() {
         driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/btnStart']")).click();
    }
}
