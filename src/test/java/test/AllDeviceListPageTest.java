package test;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AllDeviceListPage;
import pages.MultiViewScreenPage;
import utils.ExtentReportManager;
import utils.LoggerUtility;
import utils.WaitHelper;
import com.aventstack.extentreports.ExtentTest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllDeviceListPageTest extends BaseTest {

    AllDeviceListPage allDeviceListPage;
    MultiViewScreenPage multiViewScreenPage;
    private static final org.apache.logging.log4j.Logger log = LoggerUtility.getLogger(AllDeviceListPageTest.class);
    ExtentTest test;
    WaitHelper waitHelper;

    @BeforeClass
    public void setUpTest() throws Exception {
        log.info("Setting up All Device List Page test...");
        test = ExtentReportManager.createTest("All Device List Page Test");

        // Initialize page objects
        allDeviceListPage = new AllDeviceListPage(driver);
        multiViewScreenPage =new MultiViewScreenPage(driver);
        waitHelper = new WaitHelper(driver);
    }

    @Test(priority = 1)
    public void testAllDeviceListPage() {

        log.info("Starting All Device List Page Test...");


        // Handle Disconnected Popup if present
        log.info("Checking for disconnected popup...");
        allDeviceListPage.handleDisconnectedPopupIfPresent();

        // Check if all elements are visible
        boolean isRefreshButtonVisible = allDeviceListPage.isRefreshButtonVisible();
        Assert.assertTrue(isRefreshButtonVisible, "Refresh button is not visible");
        test.pass("Refresh button is visible");

        boolean isConnectedDevicesButtonVisible = allDeviceListPage.isConnectedDevicesButtonVisible();
        Assert.assertTrue(isConnectedDevicesButtonVisible, "Connected Devices button is not visible");
        test.pass("Connected Devices button is visible");

        boolean isAddDeviceTextVisible = allDeviceListPage.isAddDeviceTextVisible();
        Assert.assertTrue(isAddDeviceTextVisible, "Add Device text is not visible");
        test.pass("Add Device text is visible");

        boolean isAddDeviceButtonVisible = allDeviceListPage.isAddDeviceButtonVisible();
        Assert.assertTrue(isAddDeviceButtonVisible, "Add Device button is not visible");
        test.pass("Add Device button is visible");

        log.info("Starting test to print all devices...");

        // Call method to print all devices
        List<AllDeviceListPage.Device> devices = allDeviceListPage.getAllDevices();

        // Print total number of devices
        log.info("Total Devices Found: " + devices.size());

        // Print details of each device
        for (AllDeviceListPage.Device device : devices) {
            log.info("Device Name: " + device.getName() + " | " + device.getIpAddress());
        }

        log.info("All device details printed successfully.");
        test.pass("All device details printed successfully.");

        log.info("All Device List Page elements validated successfully.");
    }

    @Test(priority = 2)
    public void testUniqueIPAddress(){
        log.info("Starting Unique IP address functionality");
        List<AllDeviceListPage.Device> devices = allDeviceListPage.getAllDevices();

        // Set to track unique IPs
        Set<String> uniqueIPs = new HashSet<>();
        boolean hasDuplicates = false;

        for (AllDeviceListPage.Device device : devices) {
            String ipAddress = device.getIpAddress();

            // Check if the IP is already in the set
            if (!uniqueIPs.add(ipAddress)) {
                hasDuplicates = true;
                log.info("Duplicate IP found: " + ipAddress);
            }
        }

        // TestNG Assertion to fail the test if duplicates are found
        Assert.assertFalse(hasDuplicates, "Duplicate IP addresses found!");
        test.pass("Unique IP functionality checked");
    }

    // test case for finding new IP and also finding IPs which are removed after refresh
    @Test(priority = 3)
    public void RefreshForNewDevices(){
        log.info("Starting refresh btn test case");
        Set<String> beforeRefreshIPs = allDeviceListPage.getUniqueIPAddresses();


        allDeviceListPage.clickRefreshBtn();
        log.info("Clicked on refresh BTN");

        Set<String> afterRefreshIPs =allDeviceListPage.getUniqueIPAddresses();

        if(!allDeviceListPage.isAddDeviceButtonVisible()) return;
        // identifying new IP address devices
        Set<String> newDevices =new HashSet<>(afterRefreshIPs);
        newDevices.removeAll(beforeRefreshIPs);

        Set<String> removedDevices = new HashSet<>(beforeRefreshIPs);
        removedDevices.removeAll(afterRefreshIPs);

        if (newDevices.isEmpty() && removedDevices.isEmpty()){
            log.info("No new devices found . After refresh Device List is same");
        } else {
            if (!newDevices.isEmpty()) {
                System.out.println("New devices found: " + newDevices);
            }
            if (!removedDevices.isEmpty()) {
                System.out.println("Devices removed: " + removedDevices);
            }
        }
        test.pass("Refresh functionality checked");
    }


    @Test(priority = 4)
    public void clickConnectedBtn(){
        log.info("Starting Connected device functionality");
        By  connectBtnLocator = allDeviceListPage.getConnectedDevicesButton();
        if(!allDeviceListPage.isConnectedDevicesButtonVisible()) return;

        WebElement connectBtn = driver.findElement(connectBtnLocator);
        connectBtn.click();
        By NodevicesMsgLocator =  multiViewScreenPage.getNoDevicesMsg();
        WebElement alertMsg = driver.findElement(NodevicesMsgLocator);
        if(!multiViewScreenPage.isNoDevicesAlertDisplayed()) {
            log.info("Devices are connected");
            return;
        }
        else log.info(alertMsg);

        test.pass("Connected device functionality done");

    }



    @AfterClass
    public void tearDownTest() {
        log.info("All Device List Page test completed.");
    }
}
