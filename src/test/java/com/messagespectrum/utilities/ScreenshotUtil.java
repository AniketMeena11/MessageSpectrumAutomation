package com.messagespectrum.utilities;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {
	
	 /**
     * Takes screenshot and saves to test-output/screenshots/
     * @param driver  WebDriver instance
     * @param testName  Name of the test (used in filename)
     * @return  Absolute path of saved screenshot
     */
    public static String takeScreenshot(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = testName + "_" + timestamp + ".png";
        String folderPath = ConfigReader.get("screenshot.path");
        String fullPath = folderPath + fileName;

        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(fullPath);
            destFile.getParentFile().mkdirs();
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            System.err.println("Could not save screenshot: " + e.getMessage());
        }

        return fullPath;
    }

}
