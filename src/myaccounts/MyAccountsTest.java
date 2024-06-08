package myaccounts;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import utilities.Utility;

import java.util.List;

/**
 * Created by Jay Vaghani
 */
public class MyAccountsTest extends Utility {
    String baseUrl = "http://tutorialsninja.com/demo/index.php?";

    @Before
    public void setUp() {
        openBrowser(baseUrl);
    }

    public void selectMyAccountOptions(String option) {
        List<WebElement> myAccountList = getListOfElements(By.xpath("//div[@id='top-links']//li[contains(@class,'open')]/ul/li"));
        try {
            for (WebElement options : myAccountList) {
                if (options.getText().equalsIgnoreCase(option)) {
                    options.click();
                }
            }
        } catch (StaleElementReferenceException e) {
            myAccountList = getListOfElements(By.xpath("//div[@id='top-links']//li[contains(@class,'open')]/ul/li"));
        }
    }

    @Test
    public void verifyUserShouldNavigateToRegisterPageSuccessfully() {
        //1.1 Click on My Account Link.
        clickOnElement(By.xpath("//span[contains(text(),'My Account')]"));
        //1.2 Call the method “selectMyAccountOptions” method and pass the parameter "Register”
        selectMyAccountOptions("Register");
        //1.3 Verify the text “Register Account”.
        String expectedMessage = "Register Account";
        String actualMessage = getTextFromElement(By.xpath("//h1[contains(text(),'Register Account')]"));
        Assert.assertEquals("Register page not displayed", expectedMessage, actualMessage);
    }

    @Test
    public void verifyUserShouldNavigateToLoginPageSuccessfully() {
        //2.1 Click on My Account Link.
        clickOnElement(By.xpath("//span[contains(text(),'My Account')]"));
        //2.2 Call the method “selectMyAccountOptions” method and pass the parameter “Login”
        selectMyAccountOptions("Login");
        //2.3 Verify the text “Returning Customer”.
        String expectedMessage = "Returning Customer";
        String actualMessage = getTextFromElement(By.xpath("//h2[contains(text(),'Returning Customer')]"));
        Assert.assertEquals("Login page not displayed", expectedMessage, actualMessage);
    }

    @Test
    public void verifyThatUserRegisterAccountSuccessfully() {
        //3.1 Click on My Account Link.
        clickOnElement(By.xpath("//span[contains(text(),'My Account')]"));
        //3.2 Call the method “selectMyAccountOptions” method and pass the parameter “Register”
        selectMyAccountOptions("Register");
        //3.3 Enter First Name
        sendTextToElement(By.id("input-firstname"), "prime" + getAlphaNumericString(4));
        //3.4 Enter Last Name
        sendTextToElement(By.id("input-lastname"), "test" + getAlphaNumericString(4));
        //3.5 Enter Email
        sendTextToElement(By.id("input-email"), "prime" + getAlphaNumericString(4) + "@gmail.com");
        //3.6 Enter Telephone
        sendTextToElement(By.id("input-telephone"), "07988112233");
        //3.7 Enter Password
        sendTextToElement(By.id("input-password"), "test123");
        //3.8 Enter Password Confirm
        sendTextToElement(By.id("input-confirm"), "test123");
        //3.9 Select Subscribe Yes radio button
        selectByContainsTextFromListOfElements(By.xpath("//fieldset[3]//input"), "Yes");
        //3.10 Click on Privacy Policy check box
        clickOnElement(By.xpath("//div[@class = 'buttons']//input[@name='agree']"));
        //3.11 Click on Continue button
        clickOnElement(By.xpath("//div[@class = 'buttons']//input[@value='Continue']"));
        //3.12 Verify the message “Your Account Has Been Created!”
        Assert.assertEquals("", "Your Account Has Been Created!",
                getTextFromElement(By.xpath("//h1[contains(text(),'Your Account Has Been Created!')]")));
        //3.13 Click on Continue button
        clickOnElement(By.xpath("//a[contains(text(),'Continue')]"));
        //3.14 Click on My Account Link.
        clickOnElement(By.xpath("//span[contains(text(),'My Account')]"));
        //3.15 Call the method “selectMyAccountOptions” method and pass the parameter “Logout”
        selectMyAccountOptions("Logout");
        //3.16 Verify the text “Account Logout”
        String expectedMessage = "Account Logout";
        String actualMessage = getTextFromElement(By.xpath("//h1[contains(text(),'Account Logout')]"));
        Assert.assertEquals("Not logged out", expectedMessage, actualMessage);
        //3.17 Click on Continue button
        clickOnElement(By.xpath("//a[contains(text(),'Continue')]"));
    }

    @Test
    public void verifyThatUserShouldLoginAndLogoutSuccessfully() {
        //4.1 Click on My Account Link.
        clickOnElement(By.xpath("//span[contains(text(),'My Account')]"));
        //4.2 Call the method “selectMyAccountOptions” method and pass the parameter "Login”
        selectMyAccountOptions("Login");
        //4.3 Enter Email address
        sendTextToElement(By.id("input-email"), "prime1233@gmail.com");
        //4.4 Enter Password
        sendTextToElement(By.id("input-password"), "test1234");
        //4.5 Click on Login button
        clickOnElement(By.xpath("//form[contains(@action,'login')]//input[@type='submit']"));
        //4.8 Click on My Account Link.
        clickOnElement(By.xpath("//span[contains(text(),'My Account')]"));
        //4.9 Call the method “selectMyAccountOptions” method and pass the parameter “Logout”
        selectMyAccountOptions("Logout");
        //4.10 Verify the text “Account Logout”
        String expectedMessage = "Account Logout";
        String actualMessage = getTextFromElement(By.xpath("//h1[contains(text(),'Account Logout')]"));
        Assert.assertEquals("Not logged out", expectedMessage, actualMessage);
        //4.11 Click on Continue button
        clickOnElement(By.xpath("//a[contains(text(),'Continue')]"));
    }

    @After
    public void tearDown() {
        closeBrowser();
    }

}
