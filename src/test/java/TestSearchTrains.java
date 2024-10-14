import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import pages.HomePage;
import pages.SearchResults;
import utils.ExcelUtils;
import utils.Log;
import utils.PropertyUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ScreenshotUtil;

import java.io.File;
import java.io.IOException;

public class TestSearchTrains {

    private WebDriver driver;
    private HomePage homePage;
    private PropertyUtils propertyUtils;
    private ExtentReports extentReports;
    private ExtentTest test;
    private ExcelUtils excelUtils;
    private ScreenshotUtil ScreenshotUtil;

    @BeforeClass
    public void setUp() {
        // Initialize PropertyUtils
        propertyUtils = new PropertyUtils();

        // Initialize WebDriver
         driver = new ChromeDriver();
         driver.manage().window().maximize();
        driver.get(propertyUtils.getProperty("base.url"));

        // Initialize Pages
        homePage = new HomePage(driver);
        ScreenshotUtil = new ScreenshotUtil(driver);

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(propertyUtils.getProperty("report.path"));
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("IRCTC Test Automation Report");
        sparkReporter.config().setReportName("IRCTC Test Report");

        // Initialize ExtentReports
        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Tester", "G Santhosh Kumar");
        test = extentReports.createTest("Yatra Test - ");
        test.info("Browser is opened.");
    }

    @Test
    public void testSearchTrains() {
        try {
            // Load test data from Excel
            excelUtils = new ExcelUtils(propertyUtils.getProperty("excel.file.path"));
            int rowCount = excelUtils.getRowCount(0);

            for (int i = 1; i < rowCount; i++) { // Start from 1 to skip header row
                String fromStation = excelUtils.getCellData(0, i, 0); // Column 0
                String toStation = excelUtils.getCellData(0, i, 1); // Column 1

                //Search Trains
                homePage.searchTrains(fromStation, toStation);

                // Get date after 4 days
                String travelDate = homePage.selectDateAfterDays(4);
                Log.info("Searching trains from " + fromStation + " to " + toStation + " on " + travelDate);
                test.info("Searching trains from " + fromStation + " to " + toStation + " on " + travelDate);

                // Wait for results
                Thread.sleep(5000); // Replace with WebDriverWait for better synchronization

                // Validate results
                SearchResults searchResultsPage = new SearchResults(driver);
                int trainCount = searchResultsPage.getTrainCount();
                Log.info("Number of trains displayed: " + trainCount);
                test.info("Number of trains displayed: " + trainCount);
                searchResultsPage.printTrainNames();

                // Verify page title
                Assert.assertEquals(searchResultsPage.getPageTitle(), "IRCTC Next Generation eTicketing System");
                Log.info("Page title verified.");
                test.pass("Page title verified.");

                // Capture screenshot
                ScreenshotUtil.takeScreenshot("searchTrainsResult");
            }
        } catch (Exception e) {
            ScreenshotUtil.takeScreenshot("testFailure");
            Log.error("An error occurred: " + e.getMessage());
            test.fail("An error occurred: " + e.getMessage());
        }
    }

    private void captureScreenshot() {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File(propertyUtils.getProperty("screenshot.path") + "search_results.png"));
            test.addScreenCaptureFromPath(propertyUtils.getProperty("screenshot.path") + "search_results.png");
            Log.info("Screenshot Captured");
        } catch (IOException e) {
            Log.error("Failed to capture screenshot: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        excelUtils.closeWorkbook();
        extentReports.flush(); // Flush the report

    }
}
