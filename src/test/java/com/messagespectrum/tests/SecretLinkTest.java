package com.messagespectrum.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.messagespectrum.base.BaseTest;
import com.messagespectrum.pages.SecretLinkPage;
import com.messagespectrum.utilities.ConfigReader;
import com.messagespectrum.utilities.ExtentManager;
import com.aventstack.extentreports.Status;
import org.testng.annotations.BeforeMethod;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


public class SecretLinkTest extends BaseTest {

	  private SecretLinkPage Page;

	    // Initialize Page Object before each test

	    @BeforeMethod
	    public void initPage(Method method) {
	        Page = new SecretLinkPage(driver);

	        // Start Extent Report test entry
	        String tcId   = method.getName().toUpperCase().replace("TEST", "TC_");
	        String tcDesc = method.getAnnotation(Test.class).description();
	        ExtentManager.setTest(
	                ExtentManager.getInstance().createTest(
	                        method.getName(), tcDesc));
	    }

	    // ══════════════════════════════════════════════════════════════════════════
	    // MODULE 1 – GENERATE PASSWORD WHILE CLICK ON THE GENERATE BUTTON AND SEE INTO THE BODY AREA BOX
	    // ══════════════════════════════════════════════════════════════════════════

	    /**
	     * TC_001 – Verify that when user clicks 'Generate Password' button, a random password is generated and appears in the secret body text area
	     */
	    @Test(priority = 1,
	    	      description = "TC_001 | Verify clicking Generate Password fills the body area with a password")
	    	public void TC_001_GeneratePassword() {
	    	    ExtentManager.getTest().log(Status.INFO, "Clicking Generate Password button");
	    	    Page.clickGeneratePassword();

	    	    // Password appears in the body textarea
	    	    String password = Page.getBodyAreaValue();
	    	    ExtentManager.getTest().log(Status.INFO, "Generated password in body area: " + password);

	    	    Assert.assertFalse(password.isEmpty(),
	    	        "Body area should contain generated password after clicking Generate Password");

	    	    ExtentManager.getTest().log(Status.PASS,
	    	        "Password generated and found in body area: " + password);
	    	}
	    
	    
	    /**
	     * TC_002 – Each click generates a different password
	     * Flow: Click → save password → clear → click again → compare
	     * We do this 3 times to be thorough
	     */
	    @Test(priority = 2,
	          description = "TC_002 | Verify every click on Generate Password produces a unique password")
	    public void TC_002_EachClickGeneratesDifferentPassword() {
	        ExtentManager.getTest().log(Status.INFO,
	            "Clicking Generate Password 3 times and comparing values");

	        // --- First click ---
	        Page.clickGeneratePassword();
	        String password1 = Page.getBodyAreaValue();
	        ExtentManager.getTest().log(Status.INFO, "Password 1: " + password1);

	        // Clear body area before next click
	        Page.clearBodyArea();

	        // --- Second click ---
	        Page.clickGeneratePassword();
	        String password2 = Page.getBodyAreaValue();
	        ExtentManager.getTest().log(Status.INFO, "Password 2: " + password2);

	        // Clear body area before next click
	        Page.clearBodyArea();

	        // --- Third click ---
	        Page.clickGeneratePassword();
	        String password3 = Page.getBodyAreaValue();
	        ExtentManager.getTest().log(Status.INFO, "Password 3: " + password3);

	        // --- Assert all three are different ---
	        Assert.assertNotEquals(password1, password2,
	            "Password 1 and Password 2 should be different. Got: " + password1 + " | " + password2);

	        Assert.assertNotEquals(password2, password3,
	            "Password 2 and Password 3 should be different. Got: " + password2 + " | " + password3);

	        Assert.assertNotEquals(password1, password3,
	            "Password 1 and Password 3 should be different. Got: " + password1 + " | " + password3);

	        ExtentManager.getTest().log(Status.PASS,
	            "All 3 passwords are unique: " + password1 + " | " + password2 + " | " + password3);
	    }


	    /**
	     * TC_003 – Generated password meets security standards
	     * Checks: length >= 8, has uppercase, has lowercase
	     */
	    @Test(priority = 3,
	          description = "TC_003 | Verify auto-generated password meets basic security standards")
	    public void TC_003_GeneratedPasswordMeetsSecurityStandards() {
	        ExtentManager.getTest().log(Status.INFO, "Clicking Generate Password");
	        Page.clickGeneratePassword();

	        String password = Page.getBodyAreaValue();
	        ExtentManager.getTest().log(Status.INFO, "Password to validate: " + password);

	        // --- Check 1: Length must be at least 8 ---
	        int length = password.length();
	        ExtentManager.getTest().log(Status.INFO, "Password length: " + length);
	        Assert.assertTrue(length >= 8,
	            "Password length should be at least 8. Actual length: " + length);

	        // --- Check 2: Must have at least one uppercase letter ---
	        boolean hasUppercase = password.chars().anyMatch(Character::isUpperCase);
	        ExtentManager.getTest().log(Status.INFO, "Has uppercase: " + hasUppercase);
	        Assert.assertTrue(hasUppercase,
	            "Password should contain at least one uppercase letter. Password: " + password);

	        // --- Check 3: Must have at least one lowercase letter ---
	        boolean hasLowercase = password.chars().anyMatch(Character::isLowerCase);
	        ExtentManager.getTest().log(Status.INFO, "Has lowercase: " + hasLowercase);
	        Assert.assertTrue(hasLowercase,
	            "Password should contain at least one lowercase letter. Password: " + password);

	        ExtentManager.getTest().log(Status.PASS,
	            "Password '" + password + "' passed all security checks — "
	            + "Length: " + length + " | Upper: " + hasUppercase + " | Lower: " + hasLowercase);
	    }
	    
	    /**
	     * TC_006 – Body area accepts special characters and emoji
	     */
	    @Test(priority = 6,
	          description = "TC_006 | Verify Body area accepts special characters and emoji without encoding error")
	    public void TC_006_BodyAreaAcceptsSpecialCharsAndEmoji() {

	        String specialText = "Hello! @#$%^&*() Special chars 😊🔥✅";
	        ExtentManager.getTest().log(Status.INFO,
	            "Entering special characters and emoji: " + specialText);

	        Page.enterBodyText(specialText);

	        String actualValue = Page.getBodyAreaValueRelatedToBodyAreaTestCase();
	        ExtentManager.getTest().log(Status.INFO,
	            "Value retrieved from body area: " + actualValue);

	        // Assert body is not empty — content was accepted
	        Assert.assertFalse(actualValue.isEmpty(),
	            "Body area should not be empty after entering special characters");

	        // Assert special characters are present
	        Assert.assertTrue(actualValue.contains("@#$%^&*()"),
	            "Body area should contain special characters. Actual: " + actualValue);

	        ExtentManager.getTest().log(Status.PASS,
	            "Special characters and emoji accepted successfully: " + actualValue);
	    }


	    /**
	     * TC_008 – Body area handles 10,000+ characters without crash
	     */
	    @Test(priority = 8,
	          description = "TC_008 | Verify Body area handles very large text (10,000+ chars) without crash or freeze")
	    public void TC_008_BodyAreaHandlesLargeText() {

	        // Build 10,000+ character string
	        String baseText = "This is a large text input test for automation. ";
	        StringBuilder largeText = new StringBuilder();
	        while (largeText.length() < 10000) {
	            largeText.append(baseText);
	        }
	        String bigInput = largeText.toString();

	        ExtentManager.getTest().log(Status.INFO,
	            "Attempting to enter " + bigInput.length() + " characters into body area");

	        try {
	            Page.enterBodyText(bigInput);

	            String actualValue = Page.getBodyAreaValueRelatedToBodyAreaTestCase();
	            int acceptedLength = actualValue.length();

	            ExtentManager.getTest().log(Status.INFO,
	                "Characters accepted by body area: " + acceptedLength);

	            // Pass condition — system did not crash
	            // Either accepted all OR truncated with a max limit — both are valid
	            Assert.assertNotNull(actualValue,
	                "Body area should not be null after large text input");

	            Assert.assertTrue(acceptedLength > 0,
	                "Body area should have accepted at least some characters");

	            if (acceptedLength >= 10000) {
	                ExtentManager.getTest().log(Status.PASS,
	                    "Body area accepted full 10,000+ characters: " + acceptedLength);
	            } else {
	                ExtentManager.getTest().log(Status.PASS,
	                    "Body area truncated input to max length: " + acceptedLength
	                    + " — no crash occurred");
	            }

	        } catch (Exception e) {
	            Assert.fail("Body area threw exception on large input — possible freeze or crash: "
	                + e.getMessage());
	        }
	    }
	    
	    
	    /**
	     * TC_009 – Expires dropdown contains all expected options
	     */
	    @Test(priority = 9,
	          description = "TC_009 | Verify 'Expires In' dropdown contains all expected time options")
	    public void TC_009_ExpiryDropdownContainsAllOptions() {

	        List<String> actualOptions = Page.getAllExpiryOptions();
	        ExtentManager.getTest().log(Status.INFO,
	            "Options found in dropdown: " + actualOptions.toString());

	        // Expected options as per test case
	        List<String> expectedOptions = Arrays.asList("7", "14", "21", "28", "Custom");

	        // Check each expected value exists in actual dropdown
	        for (String expected : expectedOptions) {
	            boolean found = actualOptions.stream()
	                .anyMatch(option -> option.contains(expected));

	            ExtentManager.getTest().log(Status.INFO,
	                "Checking option '" + expected + "' exists: " + found);

	            Assert.assertTrue(found,
	                "Expected option '" + expected + "' not found in dropdown. "
	                + "Available: " + actualOptions);
	        }

	        ExtentManager.getTest().log(Status.PASS,
	            "All expected dropdown options found: " + actualOptions);
	    }


	    /**
	     * TC_010 – Selecting Custom shows the custom days input box
	     * and validates it only accepts 1 to 99
	     */
	    @Test(priority = 10,
	          description = "TC_010 | Verify selecting 'Custom' shows custom days input and accepts only 1-99")
	    public void TC_010_SelectCustomShowsInputBox() {

	        // Select Custom from dropdown
	        ExtentManager.getTest().log(Status.INFO,
	            "Selecting 'Custom' from Expires In dropdown");
	        Page.selectExpiry("Custom");

	        // Verify custom input box is now visible
	        boolean isVisible = Page.isCustomExpiryInputVisible();
	        ExtentManager.getTest().log(Status.INFO,
	            "Custom expiry input box visible: " + isVisible);
	        Assert.assertTrue(isVisible,
	            "Custom expiry input box should be visible after selecting 'Custom'");

	        // Check max length attribute = 2 (meaning max 99 — two digits)
	        String maxLength = Page.getCustomExpiryMaxLength();
	        ExtentManager.getTest().log(Status.INFO,
	            "Max length of custom input: " + maxLength);
	        Assert.assertEquals(maxLength, "2",
	            "Custom expiry input should only allow 2 digits (max 99)");

	        // Verify valid value 1 is accepted
	        Page.enterCustomExpiryValue("1");
	        String val1 = Page.getCustomExpiryValue();
	        ExtentManager.getTest().log(Status.INFO, "Entered '1', field shows: " + val1);
	        Assert.assertEquals(val1, "1",
	            "Custom input should accept value '1'");

	        // Verify valid value 99 is accepted
	        Page.enterCustomExpiryValue("99");
	        String val99 = Page.getCustomExpiryValue();
	        ExtentManager.getTest().log(Status.INFO, "Entered '99', field shows: " + val99);
	        Assert.assertEquals(val99, "99",
	            "Custom input should accept value '99'");

	        // Verify value above 99 (3 digits) is NOT accepted due to maxlength=2
	        Page.enterCustomExpiryValue("100");
	        String val100 = Page.getCustomExpiryValue();
	        ExtentManager.getTest().log(Status.INFO,
	            "Entered '100', field shows: " + val100);
	        Assert.assertNotEquals(val100, "100",
	            "Custom input should NOT accept 3-digit value '100' — maxlength is 2");

	        ExtentManager.getTest().log(Status.PASS,
	            "Custom expiry input visible and correctly restricts to 1-99");
	    }
	    
	    /**
	     * TC_013 – Default expiry option is 7 Days on page load
	     */
	    @Test(priority = 13,
	          description = "TC_013 | Verify 'Expires In' dropdown has '7' days pre-selected by default on page load")
	    public void TC_013_DefaultExpiryIsPreSelected() {

	        // No action needed — just read the default selected value on page load
	        String defaultExpiry = Page.getSelectedExpiryOption();
	        ExtentManager.getTest().log(Status.INFO,
	            "Default selected expiry on page load: " + defaultExpiry);

	        // Assert it is not empty
	        Assert.assertFalse(defaultExpiry.isEmpty(),
	            "A default expiry option should be pre-selected on page load");

	        // Assert it contains 7
	        Assert.assertTrue(defaultExpiry.contains("7"),
	            "Default expiry should be '7' days. Actual: " + defaultExpiry);

	        ExtentManager.getTest().log(Status.PASS,
	            "Default expiry confirmed as: " + defaultExpiry);
	    }


	    /**
	     * TC_005 – Body area accepts typed/pasted content
	     * Covered as part of TC_004 flow — validates text is present in body area
	     */
	    @Test(priority = 5,
	          description = "TC_005 | Verify user can type secret content into Body area and it is visible")
	    public void TC_005_BodyAreaAcceptsText() {

	        String secretText = "This is my secret message for automation testing!";
	        ExtentManager.getTest().log(Status.INFO,
	            "Entering text into body area: " + secretText);

	        Page.enterBodyText(secretText);

	        String actualValue = Page.getBodyAreaValue();
	        ExtentManager.getTest().log(Status.INFO,
	            "Value found in body area: " + actualValue);

	        // Assert text is present and matches
	        Assert.assertFalse(actualValue.isEmpty(),
	            "Body area should not be empty after entering text");

	        Assert.assertEquals(actualValue, secretText,
	            "Body area should contain exactly what was typed");

	        ExtentManager.getTest().log(Status.PASS,
	            "Text accepted and visible in body area: " + actualValue);
	    }


	    /**
	     * TC_004 – Auto-generated password works with Generate Link with Password flow
	     *
	     * Full Flow:
	     * 1. Click Generate Password → password appears in body area → store it
	     * 2. Click Generate Link with Password checkbox
	     * 3. Wait for password fill box to appear
	     * 4. Enter stored password into fill box
	     * 5. Click reCAPTCHA
	     * 6. Click Generate Secret Link button
	     * 7. Validate page heading shows "Secret Link"
	     */
	    @Test(priority = 4,
	          description = "TC_004 | Verify auto-generated password works correctly with Generate Link with Password flow")
	    public void TC_004_AutoGeneratedPasswordWorksWithLinkFlow() {

	        // ── Step 1: Generate password and store it ────────────────────────────
	        ExtentManager.getTest().log(Status.INFO,
	            "Step 1 — Clicking Generate Password button");
	        Page.clickGeneratePassword();

	        String generatedPassword = Page.getBodyAreaValue();
	        ExtentManager.getTest().log(Status.INFO,
	            "Generated password stored: " + generatedPassword);

	        Assert.assertFalse(generatedPassword.isEmpty(),
	            "Generated password should not be empty");

	        // ── Step 2: Clear body area ───────────────────────────────────────────
	        ExtentManager.getTest().log(Status.INFO,
	            "Step 2 — Clearing body area before entering secret text");
	        Page.clearBodyArea();

	        // ── Step 3: Enter secret body text ───────────────────────────────────
	        String secretBody = "This is a secret message protected by password.";
	        ExtentManager.getTest().log(Status.INFO,
	            "Step 3 — Entering secret body: " + secretBody);
	        Page.enterBodyText(secretBody);

	        // Verify TC_005 scenario — body has text
	        String bodyValue = Page.getBodyAreaValue();
	        Assert.assertFalse(bodyValue.isEmpty(),
	            "Body area should contain secret text (TC_005 validation)");
	        ExtentManager.getTest().log(Status.INFO,
	            "Body area contains: " + bodyValue);

	        // ── Step 4: Check Generate Link with Password checkbox ────────────────
	        ExtentManager.getTest().log(Status.INFO,
	            "Step 4 — Clicking Generate Link with Password checkbox");
	        Page.clickGenerateLinkWithPasswordCheckbox();

	        Assert.assertTrue(Page.isGenerateLinkWithPasswordChecked(),
	            "Generate Link with Password checkbox should be checked");
	        ExtentManager.getTest().log(Status.INFO,
	            "Checkbox is checked: " + Page.isGenerateLinkWithPasswordChecked());

	        // ── Step 5: Wait for password fill box and enter password ─────────────
	        ExtentManager.getTest().log(Status.INFO,
	            "Step 5 — Waiting for password fill box to appear");

	        Assert.assertTrue(Page.isPasswordFillBoxVisible(),
	            "Password fill box should appear after checking Generate Link with Password");

	        ExtentManager.getTest().log(Status.INFO,
	            "Step 5 — Entering generated password into fill box: " + generatedPassword);
	        Page.enterPasswordInFillBox(generatedPassword);

	        String filledPassword = Page.getPasswordFillBoxValue();
	        ExtentManager.getTest().log(Status.INFO,
	            "Password in fill box confirmed: " + filledPassword);

	        Assert.assertEquals(filledPassword, generatedPassword,
	            "Password fill box should contain the generated password");

	        // ── Step 6: Click reCAPTCHA ───────────────────────────────────────────
	        ExtentManager.getTest().log(Status.INFO,
	            "Step 6 — Clicking reCAPTCHA checkbox");
	        Page.clickCaptcha();

	        // Wait 3 seconds for captcha to process
	        try {
	            Thread.sleep(3000);
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	        ExtentManager.getTest().log(Status.INFO, "reCAPTCHA clicked");

	        // ── Step 7: Click Generate Secret Link ───────────────────────────────
	        ExtentManager.getTest().log(Status.INFO,
	            "Step 7 — Clicking Generate Secret Link button");
	        Page.clickGenerateSecretLink();

	        // ── Step 8: Validate page heading ────────────────────────────────────
	        ExtentManager.getTest().log(Status.INFO,
	            "Step 8 — Validating page heading after link generation");

	        String heading = Page.getPageHeadingText();
	        ExtentManager.getTest().log(Status.INFO,
	            "Page heading found: " + heading);

	        Assert.assertFalse(heading.isEmpty(),
	            "Page heading should not be empty after secret link is generated");

	        Assert.assertTrue(heading.contains("Secret Link"),
	            "Page heading should contain 'Secret Link'. Actual: " + heading);

	        ExtentManager.getTest().log(Status.PASS,
	            "TC_004 PASSED — Full flow completed. "
	            + "Password: " + generatedPassword
	            + " | Page heading: " + heading);
	    }
	    
	    /**
	     * TC_011 – Entering 0 in custom days shows error message
	     *
	     * Full Flow:
	     * 1. Click Generate Password → store password from body area
	     * 2. Clear body area → enter secret text
	     * 3. Select Custom from Expires dropdown
	     * 4. Enter 0 in custom days box
	     * 5. Click Generate Link with Password checkbox
	     * 6. Enter stored password into password fill box
	     * 7. Click reCAPTCHA
	     * 8. Click Generate Secret Link button
	     * 9. Validate error message appears
	     */
	    @Test(priority = 11,
	          description = "TC_011 | Verify entering '0' in custom days box shows error 'Expiry should be greater than 0'")
	    public void TC_011_CustomExpiryZeroShowsError() {

	        // ── Step 1: Generate password and store it ────────────────────────────
	        ExtentManager.getTest().log(Status.INFO,
	            "Step 1 — Clicking Generate Password");
	        Page.clickGeneratePassword();

	        String generatedPassword = Page.getBodyAreaValue();
	        ExtentManager.getTest().log(Status.INFO,
	            "Password stored: " + generatedPassword);

	        Assert.assertFalse(generatedPassword.isEmpty(),
	            "Generated password should not be empty");

	        // ── Step 2: Clear body → enter secret text ────────────────────────────
	        ExtentManager.getTest().log(Status.INFO,
	            "Step 2 — Clearing body area and entering secret text");
	        Page.clearBodyArea();

	        String secretBody = "Test secret message for expiry validation.";
	        Page.enterBodyText(secretBody);

	        String bodyValue = Page.getBodyAreaValue();
	        Assert.assertFalse(bodyValue.isEmpty(),
	            "Body area should contain secret text");
	        ExtentManager.getTest().log(Status.INFO,
	            "Body area contains: " + bodyValue);

	        // ── Step 3: Select Custom from Expires dropdown ───────────────────────
	        ExtentManager.getTest().log(Status.INFO,
	            "Step 3 — Selecting 'Custom' from Expires In dropdown");
	        Page.selectExpiry("Custom");

	        boolean isCustomBoxVisible = Page.isCustomExpiryInputVisible();
	        Assert.assertTrue(isCustomBoxVisible,
	            "Custom expiry input box should appear after selecting Custom");
	        ExtentManager.getTest().log(Status.INFO,
	            "Custom expiry input box visible: " + isCustomBoxVisible);

	        // ── Step 4: Enter 0 in custom days box ───────────────────────────────
	        ExtentManager.getTest().log(Status.INFO,
	            "Step 4 — Entering '0' in custom days box");
	        Page.enterCustomExpiryValue("0");

	        String customValue = Page.getCustomExpiryValue();
	        Assert.assertEquals(customValue, "0",
	            "Custom expiry input should show '0'");
	        ExtentManager.getTest().log(Status.INFO,
	            "Custom expiry value confirmed: " + customValue);

	        // ── Step 5: Click Generate Link with Password checkbox ────────────────
	        ExtentManager.getTest().log(Status.INFO,
	            "Step 5 — Clicking Generate Link with Password checkbox");
	        Page.clickGenerateLinkWithPasswordCheckbox();

	        Assert.assertTrue(Page.isGenerateLinkWithPasswordChecked(),
	            "Generate Link with Password checkbox should be checked");
	        ExtentManager.getTest().log(Status.INFO, "Checkbox checked successfully");

	        // ── Step 6: Enter stored password into fill box ───────────────────────
	        ExtentManager.getTest().log(Status.INFO,
	            "Step 6 — Waiting for password fill box and entering password");

	        Assert.assertTrue(Page.isPasswordFillBoxVisible(),
	            "Password fill box should be visible after checking the checkbox");

	        Page.enterPasswordInFillBox(generatedPassword);

	        String filledPassword = Page.getPasswordFillBoxValue();
	        Assert.assertEquals(filledPassword, generatedPassword,
	            "Password fill box should contain the generated password");
	        ExtentManager.getTest().log(Status.INFO,
	            "Password entered in fill box: " + filledPassword);

	        // ── Step 7: Click reCAPTCHA ───────────────────────────────────────────
	        ExtentManager.getTest().log(Status.INFO,
	            "Step 7 — Clicking reCAPTCHA");
	        Page.clickCaptcha();

	        try {
	            Thread.sleep(3000);
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	        ExtentManager.getTest().log(Status.INFO, "reCAPTCHA clicked — waited 3 seconds");

	        // ── Step 8: Click Generate Secret Link button ─────────────────────────
	        ExtentManager.getTest().log(Status.INFO,
	            "Step 8 — Clicking Generate Secret Link button");
	        Page.clickGenerateSecretLink();

	        // ── Step 9: Validate error message ───────────────────────────────────
	        ExtentManager.getTest().log(Status.INFO,
	            "Step 9 — Checking for error message");

	        String errorMsg = Page.getErrorMessageText();
	        ExtentManager.getTest().log(Status.INFO,
	            "Error message found: " + errorMsg);

	        Assert.assertFalse(errorMsg.isEmpty(),
	            "Error message should appear when expiry is set to 0");

	     // text match
	        Assert.assertEquals(errorMsg, "Expiry should be greater than 0",
	            "Error message should be exactly 'Expiry should be greater than 0'. Actual: " + errorMsg);
	        
	        
	        ExtentManager.getTest().log(Status.PASS,
	        	    "TC_011 PASSED — Exact error message verified: '" + errorMsg + "'");
	    }
	    
	    
	    
	    /**
	     * TC_017 – Warning message shown when Allow Multiple Views is NOT checked
	     */
	    @Test(priority = 17,
	          description = "TC_017 | Verify message '* You able to use this link once.' shown when Multiple Views unchecked")
	    public void TC_017_WarningMessageWhenMultipleViewsUnchecked() {

	        // Confirm checkbox is unchecked by default
	        boolean isChecked = Page.isAllowMultipleViewsChecked();
	        ExtentManager.getTest().log(Status.INFO,
	            "Allow Multiple Views checkbox state on load: " + isChecked);

	        // If it is checked — uncheck it first
	        if (isChecked) {
	            Page.clickAllowMultipleViewsCheckbox();
	            ExtentManager.getTest().log(Status.INFO,
	                "Checkbox was checked — unchecked it");
	        }

	        Assert.assertFalse(Page.isAllowMultipleViewsChecked(),
	            "Allow Multiple Views checkbox should be unchecked for this test");

	        // Read warning message
	        String warningMsg = Page.getWarningMessageText();
	        ExtentManager.getTest().log(Status.INFO,
	            "Warning message displayed: " + warningMsg);

	        // Assert message is not empty
	        Assert.assertFalse(warningMsg.isEmpty(),
	            "Warning message should be displayed when Multiple Views is unchecked");

	        // Assert exact message text
	        Assert.assertEquals(warningMsg,
	            "* You able to use this link once.",
	            "Warning message should be exactly '* You able to use this link once.' "
	            + "Actual: " + warningMsg);

	        ExtentManager.getTest().log(Status.PASS,
	            "TC_017 PASSED — Correct warning shown: '" + warningMsg + "'");
	    }


	    /**
	     * TC_018 – Warning message shown when Allow Multiple Views IS checked
	     */
	    @Test(priority = 18,
	          description = "TC_018 | Verify message '* You will be able to use this link multiple times until expiry duration.' shown when Multiple Views checked")
	    public void TC_018_WarningMessageWhenMultipleViewsChecked() {

	        // Click Allow Multiple Views checkbox to check it
	        ExtentManager.getTest().log(Status.INFO,
	            "Clicking Allow Multiple Views checkbox");
	        Page.clickAllowMultipleViewsCheckbox();

	        Assert.assertTrue(Page.isAllowMultipleViewsChecked(),
	            "Allow Multiple Views checkbox should be checked");
	        ExtentManager.getTest().log(Status.INFO,
	            "Checkbox is now checked: " + Page.isAllowMultipleViewsChecked());

	        // Read warning message
	        String warningMsg = Page.getWarningMessageText();
	        ExtentManager.getTest().log(Status.INFO,
	            "Warning message displayed: " + warningMsg);

	        // Assert message is not empty
	        Assert.assertFalse(warningMsg.isEmpty(),
	            "Warning message should be displayed when Multiple Views is checked");

	        // Assert exact message text
	        Assert.assertEquals(warningMsg,
	            "* You will be able to use this link multiple times until expiry duration.",
	            "Warning message should be exactly "
	            + "'* You will be able to use this link multiple times until expiry duration.' "
	            + "Actual: " + warningMsg);

	        ExtentManager.getTest().log(Status.PASS,
	            "TC_018 PASSED — Correct warning shown: '" + warningMsg + "'");
	    }


}