package utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import java.util.List;

public class DisconnectedPopupHandler {
    private AndroidDriver driver;

    public DisconnectedPopupHandler(AndroidDriver driver) {
        this.driver = driver;
    }

    // Method to check and handle the pop-up
    public void handlePopupIfPresent() {
        try {
            // Check if pop-up is present
            WebElement popup = driver.findElement(By.id("com.steris.vnc:id/cvPopup"));
            if (popup.isDisplayed()) {
                System.out.println("Devices Disconnected pop-up detected!");

                // Count the number of disconnected devices
                List<WebElement> deviceList = driver.findElements(By.id("com.steris.vnc:id/tvDeviceName"));
                int deviceCount = deviceList.size();
                System.out.println("Number of disconnected devices: " + deviceCount);

                // Click the "Disconnect" button
                WebElement disconnectButton = driver.findElement(By.id("com.steris.vnc:id/btnDisconnect"));
                disconnectButton.click();
                System.out.println("Clicked on the Disconnect button. Pop-up dismissed.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Devices Disconnected pop-up is NOT present. Proceeding normally.");
        }
    }
}
