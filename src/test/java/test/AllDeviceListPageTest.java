package test;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AllDeviceListPage;
import utils.ExtentReportManager;
import utils.LoggerUtility;
import utils.WaitHelper;
import com.aventstack.extentreports.ExtentTest;

import java.util.List;

public class AllDeviceListPageTest extends BaseTest {

    AllDeviceListPage allDeviceListPage;
    private static final org.apache.logging.log4j.Logger log = LoggerUtility.getLogger(AllDeviceListPageTest.class);
    ExtentTest test;
    WaitHelper waitHelper;

    @BeforeClass
    public void setUpTest() throws Exception {
        log.info("Setting up All Device List Page test...");
        test = ExtentReportManager.createTest("All Device List Page Test");

        // Initialize page objects
        allDeviceListPage = new AllDeviceListPage(driver);
        waitHelper = new WaitHelper(driver);
    }

    @Test
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

    @AfterClass
    public void tearDownTest() {
        log.info("All Device List Page test completed.");
    }
}
