package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();  // ThreadLocal for individual test instances

    // Create ExtentReports instance
    public static ExtentReports createInstance(String reportName) {
        String reportPath = "reports/" + reportName + ".html";

        // Ensure "reports" directory exists
        java.io.File reportsDir = new java.io.File("reports");
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Test Execution Report");
        sparkReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        return extent;
    }

    // Create a new test in the report
    public static ExtentTest createTest(String testName) {
        ExtentTest newTest = extent.createTest(testName);
        test.set(newTest); // Store the test for the current thread
        return newTest;
    }

    // Retrieve the current test instance for the current thread
    public static ExtentTest getTest() {
        return test.get();  // Retrieve the test instance associated with the current thread
    }

    // Save the report
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
