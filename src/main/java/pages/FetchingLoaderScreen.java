package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import utils.LoggerUtility;
import org.apache.logging.log4j.Logger;
import utils.WaitHelper;

public class FetchingLoaderScreen {

    AndroidDriver driver;
    private static final Logger log = LoggerUtility.getLogger(FetchingLoaderScreen.class);
    WaitHelper waitHelper;

    //constructor initialization
    public FetchingLoaderScreen(AndroidDriver driver){
        this.driver=driver;
        waitHelper =new WaitHelper(driver);
    }

    public boolean isFetchingLogoVisible(){
        return driver.findElement(AppiumBy.xpath("//android.widget.ImageView[@resource-id='com.steris.vnc:id/ivLoader']")).isDisplayed();
    }

    public boolean isLoaderBarVisible(){
        return driver.findElement(AppiumBy.xpath("//android.widget.ProgressBar[@resource-id='com.steris.vnc:id/progressBarLinear']")).isDisplayed();
    }

    public boolean isFetchingtextVisible(){
        return driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/tvFetchingText']")).isDisplayed();
    }

    public boolean isMessageAvailable(){
        return driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id='com.steris.vnc:id/tvSubtitleText']")).isDisplayed();
    }
}
