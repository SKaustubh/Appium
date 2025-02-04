package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;

    // Create ExtentReports instance
    public static ExtentReports createInstance(String reportName) {
        // Use ExtentSparkReporter instead of ExtentHtmlReporter
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("reports/" + reportName + ".html");

        // Configure report settings
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Test Execution Report");
        sparkReporter.config().setTheme(Theme.STANDARD);

        // Create ExtentReports instance
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        return extent;
    }

    // Create a new test in the report
    public static ExtentTest createTest(String testName) {
        test = extent.createTest(testName);
        return test;
    }

    // Save the report
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
