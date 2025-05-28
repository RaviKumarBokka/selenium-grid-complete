package tests;

import base.DriverFactory;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import org.testng.annotations.*;
import pages.GoogleHomePage;
import utils.*;

import java.util.List;
import java.util.Map;

public class DataDrivenGoogleTest {
    private WebDriver driver;
    private static ExtentReports extent;
    private ExtentTest test;
    private static String baseUrl;

    @BeforeSuite
    @Parameters({"env"})
    public void setUpSuite(@Optional("dev") String env) throws Exception {
        baseUrl = PropertyUtil.getEnvUrl(env);
        @SuppressWarnings("deprecation")
		ExtentHtmlReporter reporter = new ExtentHtmlReporter("target/extent-data-report.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
    }

    @Parameters("browser")
    @BeforeMethod
    public void setup(String browser) throws Exception {
        driver = DriverFactory.initDriver(browser, baseUrl);
        test = extent.createTest("DataDriven Test on " + browser);
    }

    @DataProvider(name = "jsonData")
    public Object[][] jsonData() throws Exception {
        List<Map<String,String>> list = DataUtil.readJson("testdata/data.json");
        Object[][] arr = new Object[list.size()][1];
        for (int i = 0; i < list.size(); i++) {
            arr[i][0] = list.get(i).get("searchTerm");
        }
        return arr;
    }

    @DataProvider(name = "excelData")
    public Object[][] excelData() throws Exception {
        String[][] data = DataUtil.readExcel("testdata/data.xlsx");
        return data;
    }

    @Test(dataProvider = "jsonData")
    public void testJsonDriven(String term) {
        test.info("JSON term: " + term);
        GoogleHomePage page = new GoogleHomePage(driver);
        page.searchFor(term);
        Assert.assertTrue(page.getTitle().toLowerCase().contains(term.toLowerCase()));
        test.pass("JSON search passed for: " + term);
    }

    @Test(dataProvider = "excelData")
    public void testExcelDriven(String term) {
        test.info("Excel term: " + term);
        GoogleHomePage page = new GoogleHomePage(driver);
        page.searchFor(term);
        Assert.assertTrue(page.getTitle().toLowerCase().contains(term.toLowerCase()));
        test.pass("Excel search passed for: " + term);
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws Exception {
        if (result.getStatus() == ITestResult.FAILURE) {
            String path = TestUtil.takeScreenshot(result.getName());
            test.fail("Failed").addScreenCaptureFromPath(path);
        }
        DriverFactory.quitDriver();
    }

    @AfterSuite
    public void tearDownSuite() {
        extent.flush();
    }
}