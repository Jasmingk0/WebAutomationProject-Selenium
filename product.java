

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class product {
    @Test
    public void verifyProductListSortsByName() throws InterruptedException {
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();

        // Step 1: Open SauceDemo and login
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(1000);

        // Step 2: Get all product names before sorting
        List<WebElement> productElements = driver.findElements(By.className("inventory_item_name"));
        List<String> originalNames = new ArrayList<>();
        for (WebElement product : productElements) {
            originalNames.add(product.getText());
        }

        // Step 3: Apply sorting (A to Z)
        WebElement sortDropdown = driver.findElement(By.className("product_sort_container"));
        sortDropdown.click();
        driver.findElement(By.cssSelector("option[value='az']")).click();
        Thread.sleep(1000);

        // Step 4: Get sorted product names after selecting A-Z
        List<WebElement> sortedElements = driver.findElements(By.className("inventory_item_name"));
        List<String> actualSortedNames = new ArrayList<>();
        for (WebElement product : sortedElements) {
            actualSortedNames.add(product.getText());
        }

        // Step 5: Sort the original list manually (for expected order)
        List<String> expectedSortedNames = new ArrayList<>(originalNames);
        Collections.sort(expectedSortedNames);

        // Step 6: Compare both lists
        Assert.assertEquals(actualSortedNames, expectedSortedNames,
                "❌ Product list not sorted correctly by name (A to Z).");
        System.out.println("✅ PASS: Product list sorted correctly by name (A to Z).");

        driver.quit();
    }
    @Test
    public void verifyProductListSortsByPriceLowToHigh() throws InterruptedException {
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();

        // Step 1: Open SauceDemo and login
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(1000);

        // Step 2: Get all product prices before sorting
        List<WebElement> priceElements = driver.findElements(By.className("inventory_item_price"));
        List<Double> originalPrices = new ArrayList<>();
        for (WebElement price : priceElements) {
            originalPrices.add(Double.parseDouble(price.getText().replace("$", "")));
        }

        // Step 3: Apply sorting (Low to High)
        WebElement sortDropdown = driver.findElement(By.className("product_sort_container"));
        sortDropdown.click();
        driver.findElement(By.cssSelector("option[value='lohi']")).click();
        Thread.sleep(1000);

        // Step 4: Get product prices after sorting
        List<WebElement> sortedPriceElements = driver.findElements(By.className("inventory_item_price"));
        List<Double> actualSortedPrices = new ArrayList<>();
        for (WebElement price : sortedPriceElements) {
            actualSortedPrices.add(Double.parseDouble(price.getText().replace("$", "")));
        }

        // Step 5: Sort the original prices manually (expected order)
        List<Double> expectedSortedPrices = new ArrayList<>(originalPrices);
        Collections.sort(expectedSortedPrices);

        // Step 6: Compare actual vs expected sorted prices
        Assert.assertEquals(actualSortedPrices, expectedSortedPrices,
                "❌ Product list not sorted correctly by price (Low → High).");

        System.out.println("✅ PASS: Product list sorted correctly by price (Low → High).");

        driver.quit();
    }
    @Test
    public void verifyAddToCartAddsItemToCart() throws InterruptedException {
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();

        // Step 1: Open SauceDemo and login
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(1000);

        // Step 2: Click the "Add to cart" button for the first product
        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addToCartButton.click();
        Thread.sleep(1000);

        // Step 3: Verify the cart badge appears and shows "1"
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        String itemCount = cartBadge.getText();
        Assert.assertEquals(itemCount, "1", "❌ Cart count is incorrect after adding an item.");

        System.out.println("✅ PASS: 'Add to cart' button successfully added the item to the cart. Count = " + itemCount);

        driver.quit();
    }
    @Test
    public void verifyAddToCartAddsItem() throws InterruptedException {
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();

        // Step 1: Navigate to SauceDemo login page
        driver.get("https://www.saucedemo.com/");

        // Step 2: Login with valid credentials
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(1000);

        // Step 3: Click on "Add to cart" for a product
        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addToCartButton.click();
        Thread.sleep(1000);

        // Step 4: Verify the cart badge shows "1" after adding item
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        String badgeText = cartBadge.getText();

        Assert.assertEquals(badgeText, "1", "❌ Item was not added to the cart correctly.");

        System.out.println("✅ Test Passed: 'Add to cart' button successfully added the item to the cart.");

        // Step 5: Close browser
        driver.quit();
    }
    @Test
    public void verifyButtonTextChangeAfterAddToCart() throws InterruptedException {
        // Step 1: Initialize WebDriver
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();

        // Step 2: Navigate to the website
        driver.get("https://www.saucedemo.com/");

        // Step 3: Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(1000);

        // Step 4: Identify the Add to Cart button for a product
        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));

        // Step 5: Click on the Add to Cart button
        addToCartButton.click();
        Thread.sleep(1000);

        // Step 6: Verify the button text changed to "Remove"
        String updatedText = addToCartButton.getText();
        Assert.assertEquals(updatedText, "Remove", "❌ Button text did not change to 'Remove' after adding to cart.");

        System.out.println("✅ Test Passed: Button text changed to 'Remove' successfully.");

        // Step 7: Close browser
        driver.quit();
    }
    @Test
    public void verifyCartBadgeCount() throws InterruptedException {
        // Step 1: Initialize WebDriver
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();

        // Step 2: Navigate to SauceDemo
        driver.get("https://www.saucedemo.com/");

        // Step 3: Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(1000);

        // Step 4: Add multiple items to the cart
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        Thread.sleep(1000);

        // Step 5: Locate the cart badge and get its count
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        String badgeCount = cartBadge.getText();

        // Step 6: Verify that badge count equals number of added items
        Assert.assertEquals(badgeCount, "2", "❌ Cart badge count is incorrect!");

        System.out.println("✅ Test Passed: Cart badge count correctly shows '2' after adding two items.");

        // Step 7: Close browser
        driver.quit();
    }
    @Test
    public void verifyProductLinkNavigation() throws InterruptedException {
        // Step 1: Initialize WebDriver
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();

        // Step 2: Navigate to SauceDemo and login
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(1000);

        // Step 3: Click on the first product name
        WebElement firstProductName = driver.findElement(By.className("inventory_item_name"));
        String expectedName = firstProductName.getText(); // Save name for validation
        firstProductName.click();
        Thread.sleep(1000);

        // Step 4: Verify that user is redirected to product details page
        WebElement productDetail = driver.findElement(By.className("inventory_details_name"));
        String actualName = productDetail.getText();

        Assert.assertEquals(actualName, expectedName, "❌ Product detail page name doesn't match!");

        System.out.println("✅ Clicking on product name navigates to the correct details page.");

        // Step 5: Navigate back to product list
        driver.navigate().back();
        Thread.sleep(1000);

        // Step 6: Click on the first product image this time
        WebElement firstProductImage = driver.findElement(By.cssSelector(".inventory_item_img a img"));
        firstProductImage.click();
        Thread.sleep(1000);

        // Step 7: Verify image navigation also leads to details page
        WebElement productDetail2 = driver.findElement(By.className("inventory_details_name"));
        Assert.assertTrue(productDetail2.isDisplayed(), "❌ Product details page not displayed after clicking image!");

        System.out.println("✅ Clicking on product image navigates correctly to the details page.");

        // Step 8: Close browser
        driver.quit();
    }
    @Test
    public void verifyDefaultProductsLoaded() throws InterruptedException {
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();

        // Step 1: Open SauceDemo site
        driver.get("https://www.saucedemo.com/");

        // Step 2: Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(1000);

        // Step 3: Locate all product items on inventory page
        List<WebElement> products = driver.findElements(By.className("inventory_item"));

        // Step 4: Verify the number of products displayed (should be 6)
        int actualCount = products.size();
        int expectedCount = 6;

        Assert.assertEquals(actualCount, expectedCount,
                "❌ Incorrect number of products loaded. Expected: " + expectedCount + ", Found: " + actualCount);
        System.out.println("✅ PASS: Correct number of default products (" + expectedCount + ") loaded successfully.");

        driver.quit();
    }
}
