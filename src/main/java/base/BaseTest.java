package base;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import config.ConfigReader;
import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {

    public static AndroidDriver driver;

    public static void setup() throws MalformedURLException {
        DesiredCapabilities cap = new DesiredCapabilities();

        cap.setCapability("deviceName", ConfigReader.getProperty("deviceName"));
        cap.setCapability("udid", ConfigReader.getProperty("udid"));
        cap.setCapability("platformName", ConfigReader.getProperty("platformName"));
        cap.setCapability("platformVersion", ConfigReader.getProperty("platformVersion"));
        cap.setCapability("automationName", ConfigReader.getProperty("automationName"));
        cap.setCapability("noReset", ConfigReader.getProperty("noReset"));

        cap.setCapability("appPackage", ConfigReader.getProperty("appPackage"));
        cap.setCapability("appActivity", ConfigReader.getProperty("appActivity"));

        System.out.println("Capabilities set...");

        try {
            URL url = new URL(ConfigReader.getProperty("serverURL"));
            driver = new AndroidDriver(url, cap);



            System.out.println("App started successfully!");
        } catch (Exception e) {
            System.out.println("Error while starting Appium: " + e.getMessage());
        }
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }

    }
}
