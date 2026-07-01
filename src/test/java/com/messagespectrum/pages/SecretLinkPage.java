package com.messagespectrum.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.messagespectrum.utilities.ConfigReader;
import org.openqa.selenium.support.ui.Select;
import java.util.ArrayList;
import java.util.List;

public class SecretLinkPage {

	 private WebDriver driver;
	    private WebDriverWait wait;

	    //Constructor

	    public SecretLinkPage(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver,
	                Duration.ofSeconds(ConfigReader.getInt("explicit.wait")));
	        PageFactory.initElements(driver, this);
	    }

	    //Locators

	    @FindBy(id = "ctl00_ContentPlaceHolder1_txtSecretText")
	    private WebElement bodyTextArea;

	    @FindBy(id = "ctl00_ContentPlaceHolder1_btnGenerateRandomPassword")
	    private WebElement generatePasswordButton;
	    

	    @FindBy(id = "ctl00_ContentPlaceHolder1_chkCreatePassword")
	    private WebElement generateLinkWithPasswordCheckbox;

	    @FindBy(id = "ctl00_ContentPlaceHolder1_txtLinkPassword")
	    private WebElement passwordFillBox;

	    @FindBy(xpath = "//div[@class='recaptcha-checkbox-border']")
	    private WebElement captcha;

	    @FindBy(id = "ctl00_ContentPlaceHolder1_btnGenerateSecretLink")
	    private WebElement generateSecretLinkButton;

	    @FindBy(id = "ctl00_ContentPlaceHolder1_PageHead")
	    private WebElement pageHeading;

	    
	    @FindBy(id = "ctl00_ContentPlaceHolder1_lblErrorMsg")
	    private WebElement errorMessage;
	    
	    @FindBy(id = "ctl00_ContentPlaceHolder1_lblSecretKeyWarningMsg")
	    private WebElement warningMessage;
	    
	    @FindBy(id = "ctl00_ContentPlaceHolder1_chkAllowMultipleView")
	    private WebElement allowMultipleViewsCheckbox;
	    

	    @FindBy(id = "ctl00_ContentPlaceHolder1_ddlExpiryDays")
	    private WebElement expiryDropdown;

	    @FindBy(id = "ctl00_ContentPlaceHolder1_txtExpiryDays")
	    private WebElement customExpiryInput;



	    //Actions

	    /**
	     * Clicks the Generate Password button
	     */
	    public void clickGeneratePassword() {
	        wait.until(ExpectedConditions.elementToBeClickable(generatePasswordButton)).click();
	    }

	    /**
	     * Returns the current value of the password field
	     */
	    public String getBodyAreaValue() {
	        wait.until(driver -> {
	            String val = bodyTextArea.getAttribute("value");
	            return val != null && !val.isEmpty();
	        });
	        return bodyTextArea.getAttribute("value");
	    }
	    
	    
	    /**
	     * Clicks Generate Password and returns the generated value
	     */
	    public String clickGeneratePasswordAndGetValue() {
	        wait.until(ExpectedConditions.elementToBeClickable(generatePasswordButton)).click();
	        wait.until(driver -> {
	            String val = bodyTextArea.getAttribute("value");
	            return val != null && !val.isEmpty();
	        });
	        return bodyTextArea.getAttribute("value");
	    }

	    /**
	     * Clears the body textarea
	     */
	    public void clearBodyArea() {
	        bodyTextArea.clear();
	    }

	    /**
	     * Returns the current page URL
	     */
	    public String getCurrentUrl() {
	        return driver.getCurrentUrl();
	    }

	    /**
	     * Returns the current page title
	     */
	    public String getPageTitle() {
	        return driver.getTitle();
	    }
	    
	    
	    
	    /**
	     * Enters text into the body textarea
	     */
	    public void enterBodyText(String text) {
	        wait.until(ExpectedConditions.visibilityOf(bodyTextArea));
	        bodyTextArea.clear();
	        bodyTextArea.sendKeys(text);
	    }

	    /**
	     * Returns current value of body textarea
	     */
	    public String getBodyAreaValueRelatedToBodyAreaTestCase() {
	        wait.until(ExpectedConditions.visibilityOf(bodyTextArea));
	        return bodyTextArea.getAttribute("value");
	    }

	    /**
	     * Returns the length of text currently in body textarea
	     */
	    public int getBodyAreaTextLength() {
	        return getBodyAreaValue().length();
	    }
	    
	 
	    /**
	     * Returns all options from Expires In dropdown as a List
	     */
	    public List<String> getAllExpiryOptions() {
	        wait.until(ExpectedConditions.visibilityOf(expiryDropdown));
	        Select select = new Select(expiryDropdown);
	        List<String> options = new ArrayList<>();
	        for (WebElement option : select.getOptions()) {
	            options.add(option.getText().trim());
	        }
	        return options;
	    }

	    /**
	     * Selects an option from Expires In dropdown by visible text
	     */
	    public void selectExpiry(String visibleText) {
	        wait.until(ExpectedConditions.visibilityOf(expiryDropdown));
	        Select select = new Select(expiryDropdown);
	        select.selectByVisibleText(visibleText);
	    }

	    /**
	     * Returns true if custom expiry input box is visible
	     */
	    public boolean isCustomExpiryInputVisible() {
	        try {
	            wait.until(ExpectedConditions.visibilityOf(customExpiryInput));
	            return customExpiryInput.isDisplayed();
	        } catch (Exception e) {
	            return false;
	        }
	    }

	    /**
	     * Enters value into custom expiry input box
	     */
	    public void enterCustomExpiryValue(String value) {
	        wait.until(ExpectedConditions.visibilityOf(customExpiryInput));
	        customExpiryInput.clear();
	        customExpiryInput.sendKeys(value);
	    }

	    /**
	     * Returns current value of custom expiry input box
	     */
	    public String getCustomExpiryValue() {
	        wait.until(ExpectedConditions.visibilityOf(customExpiryInput));
	        return customExpiryInput.getAttribute("value");
	    }

	    /**
	     * Returns max length allowed in custom expiry input box
	     */
	    public String getCustomExpiryMaxLength() {
	        return customExpiryInput.getAttribute("maxlength");
	    }
	    
	    /**
	     * Returns the currently selected option text from Expires dropdown
	     */
	    public String getSelectedExpiryOption() {
	        wait.until(ExpectedConditions.visibilityOf(expiryDropdown));
	        Select select = new Select(expiryDropdown);
	        return select.getFirstSelectedOption().getText().trim();
	    }

	    /**
	     * Clicks Generate Link with Password checkbox
	     */
	    public void clickGenerateLinkWithPasswordCheckbox() {
	        wait.until(ExpectedConditions.elementToBeClickable(
	            generateLinkWithPasswordCheckbox)).click();
	    }

	    /**
	     * Returns true if Generate Link with Password checkbox is selected
	     */
	    public boolean isGenerateLinkWithPasswordChecked() {
	        return generateLinkWithPasswordCheckbox.isSelected();
	    }

	    /**
	     * Returns true if password fill box is visible
	     */
	    public boolean isPasswordFillBoxVisible() {
	        try {
	            wait.until(ExpectedConditions.visibilityOf(passwordFillBox));
	            return passwordFillBox.isDisplayed();
	        } catch (Exception e) {
	            return false;
	        }
	    }

	    /**
	     * Enters password into the password fill box
	     */
	    public void enterPasswordInFillBox(String password) {
	        wait.until(ExpectedConditions.visibilityOf(passwordFillBox));
	        passwordFillBox.clear();
	        passwordFillBox.sendKeys(password);
	    }

	    /**
	     * Returns value from password fill box
	     */
	    public String getPasswordFillBoxValue() {
	        wait.until(ExpectedConditions.visibilityOf(passwordFillBox));
	        return passwordFillBox.getAttribute("value");
	    }

	    /**
	     * Clicks the reCAPTCHA checkbox
	     */
	    public void clickCaptcha() {
	        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
	            By.cssSelector("iframe[title='reCAPTCHA']")));
	        wait.until(ExpectedConditions.elementToBeClickable(captcha)).click();
	        driver.switchTo().defaultContent();
	    }

	    /**
	     * Clicks Generate Secret Link button
	     */
	    public void clickGenerateSecretLink() {
	        wait.until(ExpectedConditions.elementToBeClickable(
	            generateSecretLinkButton)).click();
	    }

	    /**
	     * Returns text of page heading
	     */
	    public String getPageHeadingText() {
	        wait.until(ExpectedConditions.visibilityOf(pageHeading));
	        return pageHeading.getText().trim();
	    }
	    
	    
	    /**
	     * Returns error message text
	     */
	    public String getErrorMessageText() {
	        try {
	            wait.until(ExpectedConditions.visibilityOf(errorMessage));
	            return errorMessage.getText().trim();
	        } catch (Exception e) {
	            return "";
	        }
	    }
	    
	    /**
	     * Returns warning message text below Create Secret Link button
	     */
	    public String getWarningMessageText() {
	        try {
	            wait.until(ExpectedConditions.visibilityOf(warningMessage));
	            return warningMessage.getText().trim();
	        } catch (Exception e) {
	            return "";
	        }
	    }

	    /**
	     * Clicks Allow Multiple Views checkbox
	     */
	    public void clickAllowMultipleViewsCheckbox() {
	        wait.until(ExpectedConditions.elementToBeClickable(
	            allowMultipleViewsCheckbox)).click();
	    }

	    /**
	     * Returns true if Allow Multiple Views checkbox is selected
	     */
	    public boolean isAllowMultipleViewsChecked() {
	        return allowMultipleViewsCheckbox.isSelected();
	    }

}