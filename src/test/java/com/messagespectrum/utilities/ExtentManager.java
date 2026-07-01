package com.messagespectrum.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;



public class ExtentManager {

	 private static ExtentReports extent;
	    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

	    public static ExtentReports getInstance() {
	        if (extent == null) {
	            String reportPath = ConfigReader.get("report.path");
	            new java.io.File(reportPath).getParentFile().mkdirs();

	            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
	            spark.config().setTheme(Theme.DARK);
	            spark.config().setDocumentTitle("Message Spectrum Secret - Automation Test Report");
	            spark.config().setReportName("Message Sepectrum Test Results");
	            spark.config().setTimeStampFormat("dd MMM yyyy HH:mm:ss");

	            extent = new ExtentReports();
	            extent.attachReporter(spark);
	            extent.setSystemInfo("Application", "Message Spectrum Secret");
	            extent.setSystemInfo("URL", ConfigReader.get("base.url"));
	            extent.setSystemInfo("Browser", ConfigReader.get("browser"));
	            extent.setSystemInfo("Tester", "Aniket");
	            extent.setSystemInfo("Environment", "Automation Test Engineer");
	        }
	        return extent;
	    }

	    public static ExtentTest getTest() {
	        return testThread.get();
	    }

	    public static void setTest(ExtentTest test) {
	        testThread.set(test);
	    }

	    public static void flush() {
	        if (extent != null) {
	            extent.flush();
	        }
	    }
}