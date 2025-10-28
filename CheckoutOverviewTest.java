import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CheckoutOverviewTest {
    @Test
    public void verifyOrderConfirmationMessage() throws InterruptedException {
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        // 🔹 Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // 🔹 Add any product to cart
        driver.findElement(By.xpath("(//button[contains(@class, 'btn_inventory')])[1]")).click();

        // 🔹 Go to cart
        driver.findElement(By.id("shopping_cart_container")).click();

        // 🔹 Click Checkout
        driver.findElement(By.id("checkout")).click();

        // 🔹 Fill out checkout information
        driver.findElement(By.id("first-name")).sendKeys("Miran");
        driver.findElement(By.id("last-name")).sendKeys("Hassan");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();

        // 🔹 Finish order
        driver.findElement(By.id("finish")).click();
        Thread.sleep(1000);

        // 🔹 Verify confirmation message
        WebElement confirmationMessage = driver.findElement(By.className("complete-header"));
        String messageText = confirmationMessage.getText();

        if (messageText.equals("Thank you for your order!")) {
            System.out.println("✅ Success message displayed correctly: " + messageText);
        } else {
            System.out.println("❌ Incorrect message! Found: " + messageText);
        }

        driver.quit();
    }  @Test
    public void verifyPonyExpressImagePresence() throws InterruptedException {
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        // 🔹 Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // 🔹 Add first product to cart
        driver.findElement(By.xpath("(//button[contains(@class,'btn_inventory')])[1]")).click();

        // 🔹 Open cart
        driver.findElement(By.id("shopping_cart_container")).click();

        // 🔹 Click Checkout
        driver.findElement(By.id("checkout")).click();

        // 🔹 Fill checkout info
        driver.findElement(By.id("first-name")).sendKeys("Miron");
        driver.findElement(By.id("last-name")).sendKeys("Hassan");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();

        // 🔹 Finish order
        driver.findElement(By.id("finish")).click();
        Thread.sleep(1000);

        // 🔹 Locate the confirmation image
        WebElement confirmationImage = driver.findElement(By.className("pony_express"));

        // ✅ Assertion to verify image is displayed
        Assert.assertTrue(confirmationImage.isDisplayed(),
                "❌ Confirmation image (Pony Express) is NOT displayed on the confirmation page!");

        System.out.println("✅ Confirmation image (Pony Express) is displayed correctly.");

        driver.quit();
    }
    @Test
    public void verifyBackHomeNavigation() throws InterruptedException {
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();

        // Step 1: Navigate to login page
        driver.get("https://www.saucedemo.com/");

        // Step 2: Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Step 3: Add a product to cart
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        // Step 4: Go to cart
        driver.findElement(By.id("shopping_cart_container")).click();

        // Step 5: Click checkout
        driver.findElement(By.id("checkout")).click();

        // Step 6: Fill checkout info
        driver.findElement(By.id("first-name")).sendKeys("Miran");
        driver.findElement(By.id("last-name")).sendKeys("Hassan");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();

        // Step 7: Finish order
        driver.findElement(By.id("finish")).click();

        // Step 8: Click "Back Home" button
        WebElement backHomeBtn = driver.findElement(By.id("back-to-products"));
        backHomeBtn.click();
        Thread.sleep(1000);

        // Step 9: Verify user is redirected to inventory page
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory.html"),
                "❌ Back Home button did not navigate to the inventory page!");

        System.out.println("✅ PASS: Back Home button successfully returned to the inventory page.");

        driver.quit();
    }
    @Test
    public void verifyCartIsClearedAfterCheckout() throws InterruptedException {
        WebDriver driver = new EdgeDriver();
        driver.manage().window().maximize();

        // Step 1: Navigate to login page
        driver.get("https://www.saucedemo.com/");

        // Step 2: Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Step 3: Add a product to the cart
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        // Step 4: Go to cart
        driver.findElement(By.id("shopping_cart_container")).click();

        // Step 5: Proceed to checkout
        driver.findElement(By.id("checkout")).click();

        // Step 6: Fill checkout info
        driver.findElement(By.id("first-name")).sendKeys("Miran");
        driver.findElement(By.id("last-name")).sendKeys("Hassan");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();

        // Step 7: Finish the order
        driver.findElement(By.id("finish")).click();
        Thread.sleep(1000);

        // Step 8: Click Back Home to return to inventory
        driver.findElement(By.id("back-to-products")).click();

        // Step 9: Open cart again
        driver.findElement(By.id("shopping_cart_container")).click();

        // Step 10: Verify the cart is empty
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));

        // ✅ Assert that cart is empty
        Assert.assertTrue(cartItems.isEmpty(), "❌ Cart is not cleared after checkout!");

        System.out.println("✅ PASS: Cart is successfully cleared after completing the order.");

        driver.quit();
    }
}


