package com.messagespectrum.base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.messagespectrum.utilities.ConfigReader;
import com.messagespectrum.utilities.ExtentManager;
import com.messagespectrum.utilities.ScreenshotUtil;

import io.github.bonigarcia.wdm.WebDriverManager;


public class BaseTest {

	 protected WebDriver driver;

	    //Driver Setup

	    @BeforeMethod
	    @Parameters({"browser"})
	    public void setUp(@Optional String browserParam) {
	        String browser = (browserParam != null) ? browserParam : ConfigReader.get("browser");
	        boolean headless = ConfigReader.getBoolean("headless");

	        switch (browser.toLowerCase()) {
	            case "firefox":
	                WebDriverManager.firefoxdriver().setup();
	                driver = new FirefoxDriver();
	                break;

	            case "edge":
	                WebDriverManager.edgedriver().setup();
	                driver = new EdgeDriver();
	                break;

	            case "chrome":
	            default:
	                WebDriverManager.chromedriver().setup();
	                ChromeOptions options = new ChromeOptions();
	                if (headless) {
	                    options.addArguments("--headless=new");
	                }
	                options.addArguments("--start-maximized");
	                options.addArguments("--disable-notifications");
	                options.addArguments("--no-sandbox");
	                options.addArguments("--disable-dev-shm-usage");
	                driver = new ChromeDriver(options);
	                break;
	        }

	        driver.manage().timeouts().implicitlyWait(
	                Duration.ofSeconds(ConfigReader.getInt("implicit.wait")));
	        driver.manage().timeouts().pageLoadTimeout(
	                Duration.ofSeconds(ConfigReader.getInt("page.load.timeout")));
	        driver.manage().window().maximize();

	        // Open application URL
	        driver.get(ConfigReader.get("base.url"));
	    }

	    //Driver Teardown

	    @AfterMethod
	    public void tearDown(ITestResult result) {
	        ExtentTest test = ExtentManager.getTest();

	        if (test != null) {
	            if (result.getStatus() == ITestResult.FAILURE) {
	                // Take screenshot on failure
	                String screenshotPath = ScreenshotUtil.takeScreenshot(driver, result.getName());
	                try {
	                    test.fail("Test FAILED: " + result.getThrowable().getMessage(),
	                            MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
	                } catch (Exception e) {
	                    test.fail("Test FAILED: " + result.getThrowable().getMessage());
	                }
	            } else if (result.getStatus() == ITestResult.SUCCESS) {
	                test.log(Status.PASS, "Test PASSED ✓");
	            } else if (result.getStatus() == ITestResult.SKIP) {
	                test.log(Status.SKIP, "Test SKIPPED");
	            }
	        }

	        if (driver != null) {
	            driver.quit();
	        }
	    }

	    // Report Setup & Flush

	    @BeforeSuite
	    public void setUpReport() {
	        ExtentManager.getInstance();
	    }

	    @AfterSuite
	    public void flushReport() {
	        ExtentManager.flush();
	    }
}