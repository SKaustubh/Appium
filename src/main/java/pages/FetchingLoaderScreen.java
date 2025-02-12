package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.LoggerUtility;
import org.apache.logging.log4j.Logger;
import utils.WaitHelper;

public class FetchingLoaderScreen{

    AndroidDriver driver;
    private static final Logger log = LoggerUtility.getLogger(FetchingLoaderScreen.class);
    WaitHelper waitHelper;

    //constructor
    public FetchingLoaderScreen(AndroidDriver driver){
        this.driver=driver;
        waitHelper =new WaitHelper(driver);
    }


    private static final By fetchingLogo = By.xpath("//android.widget.ImageView[@resource-id='com.steris.vnc:id/ivLoader']");
    private static final By loaderBar = By.xpath("//android.widget.ProgressBar[@resource-id='com.steris.vnc:id/progressBarLinear']");
    private static final By fetchingText = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/tvFetchingText']");
    private static final By messageText = By.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/tvSubtitleText']");


    public boolean isFetchingLogoVisible(){
        WebElement logo = waitHelper.waitForElementToBeVisible(fetchingLogo, 30); // Wait for the logo to be visible
        return logo != null && logo.isDisplayed();
    }

    public boolean isLoaderBarVisible(){
        WebElement bar = waitHelper.waitForElementToBeVisible(loaderBar,30);
        return bar != null && bar.isDisplayed();
    }

    public boolean isFetchingTextVisible(){
        WebElement Text = waitHelper.waitForElementToBeVisible(fetchingText,30);
        return Text != null && Text.isDisplayed();
    }

    public boolean isMessageAvailable(){
        WebElement msg = waitHelper.waitForElementToBeVisible(messageText,30);
        return msg != null && msg.isDisplayed();
    }
}
