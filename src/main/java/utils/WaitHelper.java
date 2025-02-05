package utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WaitHelper {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public WaitHelper(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // Set timeout to 10 seconds
    }

    // Wait for element to be visible
    public void waitForElementVisible(AppiumBy locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Wait for WebElement to be clickable
    public void waitForElementClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    // Wait for alert to appear
    public void waitForAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
    }
}
