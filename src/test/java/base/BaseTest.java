package base;

import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.*;
import config.ConfigReader;
import utils.ExtentReportManager;
import com.aventstack.extentreports.Status;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {

    public static AndroidDriver driver;

    @BeforeSuite
    public void setup() throws MalformedURLException{
        DesiredCapabilities cap = new DesiredCapabilities();
        ExtentReportManager.createInstance("TestReport");

        cap.setCapability("deviceName", ConfigReader.getProperty("deviceName"));
        cap.setCapability("udid", ConfigReader.getProperty("udid"));
        cap.setCapability("platformName", ConfigReader.getProperty("platformName"));
        cap.setCapability("platformVersion", ConfigReader.getProperty("platformVersion"));
        cap.setCapability("automationName", ConfigReader.getProperty("automationName"));
        cap.setCapability("noReset", ConfigReader.getProperty("noReset"));

        cap.setCapability("appPackage", ConfigReader.getProperty("appPackage"));
        cap.setCapability("appActivity", ConfigReader.getProperty("appActivity"));

        // Extra capabilities to avoid app closing issue
        cap.setCapability("autoGrantPermissions", true);
        cap.setCapability("appWaitActivity", "com.steris.vnc.ui.home.StartActivity,com.steris.vnc.ui.vnc.VncActivity"); // Wait for next activity


        System.out.println("Capabilities set...");

        try {
            URL url = new URL(ConfigReader.getProperty("serverURL"));
            driver = new AndroidDriver(url, cap);


            // Explicit wait for StartActivity to load after Splash



            System.out.println("App started successfully!");
        } catch (Exception e) {
            System.out.println("Error while starting Appium: " + e.getMessage());
        }
    }

    @AfterMethod
    public void checkForAppCrash(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("❌ Test Failed: " + result.getName());
            logFailure(result);
        }
    }

    private void logFailure(ITestResult result) {
        String testName = result.getName();
        Throwable error = result.getThrowable();

        System.out.println("❌ Error Details: " + error.getMessage());

        // Capture Screenshot on Failure
        captureScreenshot(testName);

        // Log failure in Extent Reports
        ExtentReportManager.getTest().log(Status.FAIL, "Test Failed: " + error.getMessage());
    }

    public void captureScreenshot(String testName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = "screenshots/" + testName + ".png";
        try {
            FileUtils.copyFile(screenshot, new File(screenshotPath));
            System.out.println("📸 Screenshot captured: " + screenshotPath);
        } catch (IOException e) {
            System.out.println("❌ Error saving screenshot: " + e.getMessage());
        }
    }


    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("App has been closed");
        }
        // Ensure that the Extent Report is flushed only once after all tests
        ExtentReportManager.flushReport();
    }
}