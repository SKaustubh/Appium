package utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;
import java.time.Duration;

public class WaitHelper {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public WaitHelper(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Wait for element to be visible (AppiumBy locator)
    public void waitForElementVisible(AppiumBy locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logError("Element not visible within timeout: " + locator);
            throw e;
        }
    }

    // Wait for element to be visible (By locator)
    public void waitForElementVisible(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logError("Element not visible within timeout: " + locator);
            throw e;
        }
    }

    // Wait for element to be clickable (WebElement)
    public void waitForElementClickable(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            logError("Element not clickable within timeout: " + element);
            throw e;
        }
    }

    // Wait for alert to appear
    public void waitForAlert() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
        } catch (TimeoutException e) {
            logError("Alert did not appear within timeout.");
            throw e;
        }
    }

    // Method to handle explicit waiting for element visibility with dynamic timeout
    public WebElement waitForElementToBeVisible(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait dynamicWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return dynamicWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logError("Element not visible within custom timeout: " + locator);
            throw e;
        }
    }

    // Utility method to log error messages
    private void logError(String message) {

        System.err.println(message);
    }
}
