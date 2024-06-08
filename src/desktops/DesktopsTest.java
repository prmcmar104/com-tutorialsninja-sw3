package desktops;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utilities.Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jay Vaghani
 */
public class DesktopsTest extends Utility {

    String baseUrl = "http://tutorialsninja.com/demo/index.php?";

    @Before
    public void setUp() {
        openBrowser(baseUrl);
    }

    @Test
    public void verifyProductArrangeInAlphabeticalOrder() {
        //1.1 Mouse hover on Desktops Tab.and click
        mouseHoverToElementAndClick(By.linkText("Desktops"));
        //1.2 Click on “Show All Desktops”
        clickOnElement(By.linkText("Show AllDesktops"));
        // Get all the products name and stored into array list
        List<WebElement> products = getListOfElements(By.xpath("//h4/a"));
        ArrayList<String> originalProductsName = new ArrayList<>();
        for (WebElement e : products) {
            originalProductsName.add(e.getText());
        }
        System.out.println(originalProductsName);
        // Sort By Reverse order
        Collections.reverse(originalProductsName);
        System.out.println(originalProductsName);
        //1.3 Select Sort By position "Name: Z to A"
        selectByVisibleTextFromDropDown(By.id("input-sort"), "Name (Z - A)");
        // After filter Z -A Get all the products name and stored into array list
        products = getListOfElements(By.xpath("//h4/a"));
        ArrayList<String> afterSortByZToAProductsName = new ArrayList<>();
        for (WebElement e : products) {
            afterSortByZToAProductsName.add(e.getText());
        }
        System.out.println(afterSortByZToAProductsName);
        //1.4 Verify the Product will arrange in Descending order.
        Assert.assertEquals("Product not sorted into Z to A order",
                originalProductsName, afterSortByZToAProductsName);
    }

    @Test
    public void verifyProductAddedToShoppingCartSuccessFully() {

        // Select currency as £ Pound Sterling
        clickOnElement(By.xpath("//span[contains(text(),'Currency')]"));
        selectByContainsTextFromListOfElements(By.xpath("//ul[@class = 'dropdown-menu']/li"), "£Pound Sterling");
        //2.1 Mouse hover on Desktops Tab. and click
        mouseHoverToElementAndClick(By.linkText("Desktops"));
        //2.2 Click on “Show All Desktops”
        clickOnElement(By.linkText("Show AllDesktops"));
        //2.3 Select Sort By position "Name: A to Z"
        selectByVisibleTextFromDropDown(By.id("input-sort"), "Name (A - Z)");
        //2.4 Select product “HP LP3065”
        clickOnElement(By.xpath("//a[contains(text(),'HP LP3065')]"));
        //2.5 Verify the Text "HP LP3065"
        Assert.assertEquals("HP LP3065 Product not display", "HP LP3065",
                getTextFromElement(By.xpath("//h1[contains(text(),'HP LP3065')]")));
        //2.6 Select Delivery Date "2023-11-30"
        String year = "2025";
        String month = "November";
        String date = "30";
        clickOnElement(By.xpath("//div[@class = 'input-group date']//button"));
        while (true) {
            String monthAndYear = driver.findElement(By.xpath("//div[@class = 'datepicker']/div[1]//th[@class='picker-switch']")).getText();
            String[] arr = monthAndYear.split(" ");
            String mon = arr[0];
            String yer = arr[1];
            if (mon.equalsIgnoreCase(month) && yer.equalsIgnoreCase(year)) {
                break;
            } else {
                clickOnElement(By.xpath("//div[@class = 'datepicker']/div[1]//th[@class='next']"));
            }
        }
        List<WebElement> allDates = getListOfElements(By.xpath("//div[@class = 'datepicker']/div[1]//tbody/tr/td[@class = 'day']"));
        for (WebElement e : allDates) {
            if (e.getText().equalsIgnoreCase(date)) {
                e.click();
                break;
            }
        }
        //2.7.Enter Qty "1” using Select class.
//        sendTextToElement(By.name("quantity"), "1");
        //2.8 Click on “Add to Cart” button
        clickOnElement(By.xpath("//button[@id='button-cart']"));
        //2.9 Verify the Message “Success: You have added HP LP3065 to your shopping cart!”
        Assert.assertTrue("Product not added to cart",
                getTextFromElement(By.cssSelector("body:nth-child(2) div.container:nth-child(4) > div.alert.alert-success.alert-dismissible"))
                        .contains("Success: You have added HP LP3065 to your shopping cart!"));
        //2.10 Click on link “shopping cart” display into success message
        clickOnElement(By.xpath("//a[contains(text(),'shopping cart')]"));
        //2.11 Verify the text "Shopping Cart"
        Assert.assertTrue(getTextFromElement(By.xpath("//div[@id='content']//h1")).contains("Shopping Cart"));
        //2.12 Verify the Product name "HP LP3065"
        Assert.assertEquals("Product name not matched", "HP LP3065", getTextFromElement(By.xpath("//div[@class = 'table-responsive']/table/tbody/tr/td[2]/a")));
        //2.13 Verify the Delivery Date "2025-11-30"
        Assert.assertTrue("Delivery date not matched", getTextFromElement(By.xpath("//div[@class = 'table-responsive']/table/tbody/tr/td[2]/small[1]")).contains("2025-11-30"));
        //2.14 Verify the Model "Product21"
        Assert.assertEquals("Model not matched", "Product 21", getTextFromElement(By.xpath("//div[@class = 'table-responsive']/table/tbody/tr/td[3]")));
        //2.15 Verify the Todat "£74.73"
        Assert.assertEquals("Total not matched", "£74.73", getTextFromElement(By.xpath("//div[@class = 'table-responsive']/table/tbody/tr/td[6]")));

    }

    @After
    public void tearDown() {
        closeBrowser();
    }

}
