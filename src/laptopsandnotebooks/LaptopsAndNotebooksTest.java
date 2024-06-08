package laptopsandnotebooks;

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
public class LaptopsAndNotebooksTest extends Utility {

    String baseUrl = "http://tutorialsninja.com/demo/index.php?";

    @Before
    public void setUp() {
        openBrowser(baseUrl);
    }

    @Test
    public void verifyProductsPriceDisplayHighToLowSuccessfully() {
        //1.1 Mouse hover on Laptops & Notebooks Tab.and click
        mouseHoverToElementAndClick(By.linkText("Laptops & Notebooks"));
        //1.2 Click on “Show AllLaptops & Notebooks”
        clickOnElement(By.linkText("Show AllLaptops & Notebooks"));
        // Get all the products price and stored into array list
        List<WebElement> products = getListOfElements(By.xpath("//p[@class ='price']"));
        List<Double> originalProductsPrice = new ArrayList<>();
        for (WebElement e : products) {
            System.out.println(e.getText());
            String[] arr = e.getText().split("Ex Tax:");
            originalProductsPrice.add(Double.valueOf(arr[0].substring(1).replaceAll(",","")));
        }
        System.out.println(originalProductsPrice);
        // Sort By Reverse order
        Collections.sort(originalProductsPrice, Collections.reverseOrder());
        System.out.println(originalProductsPrice);
        //1.3 Select Sort By "Price (High > Low)"
        selectByVisibleTextFromDropDown(By.id("input-sort"), "Price (High > Low)");
        // After filter Price (High > Low) Get all the products price and stored into array list
        products = getListOfElements(By.xpath("//p[@class ='price']"));
        ArrayList<Double> afterSortByPrice = new ArrayList<>();
        for (WebElement e : products) {
            String[] arr = e.getText().split("Ex Tax:");
            afterSortByPrice.add(Double.valueOf(arr[0].substring(1).replaceAll(",","")));
        }
        System.out.println(afterSortByPrice);
        //1.4 Verify the Product price will arrange in High to Low order.
        Assert.assertEquals("Product not sorted by price High to Low",
                originalProductsPrice, afterSortByPrice);
    }

    @Test
    public void verifyThatUserPlaceOrderSuccessfully() {
        // Select currency as £ Pound Sterling
        clickOnElement(By.xpath("//span[contains(text(),'Currency')]"));
        selectByContainsTextFromListOfElements(By.xpath("//ul[@class = 'dropdown-menu']/li"), "£Pound Sterling");
        //2.1 Mouse hover on Laptops & Notebooks Tab and click
        mouseHoverToElementAndClick(By.linkText("Laptops & Notebooks"));
        //2.2 Click on “Show All Laptops & Notebooks”
        clickOnElement(By.linkText("Show AllLaptops & Notebooks"));
        //2.3 Select Sort By "Price (High > Low)"
        selectByVisibleTextFromDropDown(By.id("input-sort"), "Price (High > Low)");
        //2.4 Select Product “MacBook”
        clickOnElement(By.linkText("MacBook"));
        //2.5 Verify the text “MacBook”
        Assert.assertEquals("MacBook Product not display", "MacBook",
                getTextFromElement(By.xpath("//h1[contains(text(),'MacBook')]")));
        //2.6 Click on ‘Add To Cart’ button
        clickOnElement(By.xpath("//button[@id='button-cart']"));
        //2.7 Verify the message “Success: You have added MacBook to your shopping cart!”
        Assert.assertTrue("Product not added to cart",
                getTextFromElement(By.cssSelector("body:nth-child(2) div.container:nth-child(4) > div.alert.alert-success.alert-dismissible"))
                        .contains("Success: You have added MacBook to your shopping cart!"));
        //2.8 Click on link “shopping cart” display into success message
        clickOnElement(By.xpath("//a[contains(text(),'shopping cart')]"));
        //2.9 Verify the text "Shopping Cart"
        Assert.assertTrue(getTextFromElement(By.xpath("//div[@id='content']//h1")).contains("Shopping Cart"));
        //2.10 Verify the Product name "MacBook"
        Assert.assertEquals("Product name not matched", "MacBook", getTextFromElement(By.xpath("//div[@class = 'table-responsive']/table/tbody/tr/td[2]/a")));
        //2.11 Change Quantity "2"
        sendTextToElement(By.xpath("//input[contains(@name, 'quantity')]"), "2");
        //2.12 Click on “Update” Tab
        clickOnElement(By.xpath("//button[contains(@data-original-title, 'Update')]"));
        //2.13 Verify the message “Success: You have modified your shopping cart!”
        Assert.assertTrue("Cart not modified", getTextFromElement(By.xpath("//div[@id='checkout-cart']/div[1]")).contains("Success: You have modified your shopping cart!"));
        //2.14 Verify the Total £737.45
        Assert.assertEquals("Total not matched", "£737.45", getTextFromElement(By.xpath("//div[@class = 'table-responsive']/table/tbody/tr/td[6]")));


    }

    @After
    public void tearDown() {
        closeBrowser();
    }

}
