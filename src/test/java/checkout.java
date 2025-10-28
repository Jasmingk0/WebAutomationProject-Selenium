import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

    public class checkout{

        @Test
        public void verifyOverviewListsAllCartProducts() throws InterruptedException {
            WebDriver driver = new EdgeDriver();
            driver.manage().window().maximize();

            // Step 1: Open the SauceDemo website
            driver.get("https://www.saucedemo.com/");

            // Step 2: Login
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();
            Thread.sleep(1000);

            // Step 3: Add multiple products to the cart
            driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
            driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
            Thread.sleep(1000);

            // Step 4: Go to the cart page
            driver.findElement(By.id("shopping_cart_container")).click();
            Thread.sleep(1000);

            // Step 5: Capture product names in the cart
            List<WebElement> cartItems = driver.findElements(By.className("inventory_item_name"));
            int cartItemCount = cartItems.size();
            System.out.println("🛒 Products in cart: " + cartItemCount);

            // Step 6: Proceed to checkout
            driver.findElement(By.id("checkout")).click();
            Thread.sleep(1000);

            // Step 7: Enter checkout information
            driver.findElement(By.id("first-name")).sendKeys("Miran");
            driver.findElement(By.id("last-name")).sendKeys("Hassan");
            driver.findElement(By.id("postal-code")).sendKeys("12345");
            driver.findElement(By.id("continue")).click();
            Thread.sleep(1000);

            // Step 8: Capture products on the overview page
            List<WebElement> overviewItems = driver.findElements(By.className("inventory_item_name"));
            int overviewItemCount = overviewItems.size();
            System.out.println("📦 Products on overview page: " + overviewItemCount);

            // Step 9: Verify both lists match
            Assert.assertEquals(overviewItemCount, cartItemCount, "❌ Mismatch between cart and overview items!");
            System.out.println("✅ PASS: Overview page lists all products from the cart.");

            // Step 10: Close the browser
            driver.quit();
        }
        @Test
        public void verifyItemSubtotalAccuracy() throws InterruptedException {
            WebDriver driver = new EdgeDriver();
            driver.manage().window().maximize();

            // Step 1: Navigate to SauceDemo
            driver.get("https://www.saucedemo.com/");

            // Step 2: Login
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();
            Thread.sleep(1000);

            // Step 3: Add multiple items to the cart
            driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
            driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
            Thread.sleep(1000);

            // Step 4: Navigate to the cart page
            driver.findElement(By.id("shopping_cart_container")).click();
            Thread.sleep(1000);

            // Step 5: Proceed to checkout
            driver.findElement(By.id("checkout")).click();
            Thread.sleep(1000);

            // Step 6: Fill checkout info
            driver.findElement(By.id("first-name")).sendKeys("Miran");
            driver.findElement(By.id("last-name")).sendKeys("Hassan");
            driver.findElement(By.id("postal-code")).sendKeys("12345");
            driver.findElement(By.id("continue")).click();
            Thread.sleep(1000);

            // Step 7: Get all product prices on the overview page
            List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
            double calculatedTotal = 0.0;

            for (WebElement priceElement : prices) {
                String priceText = priceElement.getText().replace("$", "");
                double priceValue = Double.parseDouble(priceText);
                calculatedTotal += priceValue;
            }

            System.out.println("🧮 Calculated item subtotal: $" + calculatedTotal);

            // Step 8: Get the displayed item subtotal
            WebElement subtotalElement = driver.findElement(By.className("summary_subtotal_label"));
            String subtotalText = subtotalElement.getText().replace("Item total: $", "").trim();
            double displayedSubtotal = Double.parseDouble(subtotalText);

            System.out.println("💰 Displayed item subtotal: $" + displayedSubtotal);

            // Step 9: Compare calculated and displayed subtotal
            Assert.assertEquals(displayedSubtotal, calculatedTotal, 0.01,
                    "❌ Item subtotal does not match the sum of product prices!");

            System.out.println("✅ PASS: Item subtotal matches the sum of product prices accurately.");

            // Step 10: Close browser
            driver.quit();
        }
        @Test
        public void verifyTaxAndTotalCalculation() throws InterruptedException {
            WebDriver driver = new EdgeDriver();
            driver.manage().window().maximize();

            // Step 1: Navigate to SauceDemo
            driver.get("https://www.saucedemo.com/");

            // Step 2: Login
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();
            Thread.sleep(1000);

            // Step 3: Add items to cart
            driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
            driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
            Thread.sleep(1000);

            // Step 4: Go to cart
            driver.findElement(By.id("shopping_cart_container")).click();
            Thread.sleep(1000);

            // Step 5: Proceed to checkout
            driver.findElement(By.id("checkout")).click();
            Thread.sleep(1000);

            // Step 6: Fill checkout info
            driver.findElement(By.id("first-name")).sendKeys("Miran");
            driver.findElement(By.id("last-name")).sendKeys("Hassan");
            driver.findElement(By.id("postal-code")).sendKeys("12345");
            driver.findElement(By.id("continue")).click();
            Thread.sleep(1000);

            // Step 7: Get all item prices and calculate subtotal
            List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
            double calculatedSubtotal = 0.0;

            for (WebElement price : prices) {
                String priceText = price.getText().replace("$", "");
                calculatedSubtotal += Double.parseDouble(priceText);
            }

            System.out.println("🧮 Calculated Subtotal: $" + calculatedSubtotal);

            // Step 8: Get displayed subtotal
            String subtotalText = driver.findElement(By.className("summary_subtotal_label"))
                    .getText().replace("Item total: $", "").trim();
            double displayedSubtotal = Double.parseDouble(subtotalText);

            // Step 9: Get displayed tax
            String taxText = driver.findElement(By.className("summary_tax_label"))
                    .getText().replace("Tax: $", "").trim();
            double displayedTax = Double.parseDouble(taxText);

            // Step 10: Get displayed total
            String totalText = driver.findElement(By.className("summary_total_label"))
                    .getText().replace("Total: $", "").trim();
            double displayedTotal = Double.parseDouble(totalText);

            // Step 11: Calculate expected total (subtotal + tax)
            double expectedTotal = displayedSubtotal + displayedTax;

            System.out.println("💰 Displayed Subtotal: $" + displayedSubtotal);
            System.out.println("💸 Displayed Tax: $" + displayedTax);
            System.out.println("🧾 Displayed Total: $" + displayedTotal);
            System.out.println("✅ Expected Total (Subtotal + Tax): $" + expectedTotal);

            // Step 12: Assertions
            Assert.assertEquals(displayedSubtotal, calculatedSubtotal, 0.01,
                    "❌ Subtotal mismatch between calculation and display!");

            Assert.assertEquals(displayedTotal, expectedTotal, 0.01,
                    "❌ Total price not matching subtotal + tax!");

            System.out.println("✅ PASS: Tax and total price are calculated correctly.");

            // Step 13: Close browser
            driver.quit();
        }
        @Test
        public void verifyPaymentAndShippingDetailsArePresent() throws InterruptedException {
            // Step 1: Initialize WebDriver
            WebDriver driver = new EdgeDriver();
            driver.manage().window().maximize();

            // Step 2: Navigate to the website
            driver.get("https://www.saucedemo.com/");

            // Step 3: Login with valid credentials
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();
            Thread.sleep(1000);

            // Step 4: Add product to cart
            driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

            // Step 5: Go to the cart
            driver.findElement(By.className("shopping_cart_link")).click();
            Thread.sleep(500);

            // Step 6: Proceed to checkout
            driver.findElement(By.id("checkout")).click();

            // Step 7: Enter checkout information
            driver.findElement(By.id("first-name")).sendKeys("John");
            driver.findElement(By.id("last-name")).sendKeys("Doe");
            driver.findElement(By.id("postal-code")).sendKeys("12345");
            driver.findElement(By.id("continue")).click();
            Thread.sleep(1000);

            // Step 8: Verify Payment and Shipping details are displayed
            WebElement paymentInfo = driver.findElement(By.className("summary_value_label"));
            WebElement shippingInfo = driver.findElements(By.className("summary_value_label")).get(1);

            Assert.assertTrue(paymentInfo.isDisplayed(), "❌ Payment Information is missing on the Overview page.");
            Assert.assertTrue(shippingInfo.isDisplayed(), "❌ Shipping Information is missing on the Overview page.");

            System.out.println("✅ Test Passed: Payment and Shipping details are displayed correctly on the Overview page.");

            // Step 9: Close the browser
            driver.quit();
        }
        @Test
        public void verifyCancelButtonReturnsToProductList() throws InterruptedException {
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

            // Step 4: Add an item to cart
            driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

            // Step 5: Go to cart
            driver.findElement(By.className("shopping_cart_link")).click();
            Thread.sleep(500);

            // Step 6: Click on Checkout
            driver.findElement(By.id("checkout")).click();

            // Step 7: Fill checkout information
            driver.findElement(By.id("first-name")).sendKeys("John");
            driver.findElement(By.id("last-name")).sendKeys("Doe");
            driver.findElement(By.id("postal-code")).sendKeys("12345");
            driver.findElement(By.id("continue")).click();
            Thread.sleep(1000);

            // Step 8: Click on Cancel
            driver.findElement(By.id("cancel")).click();
            Thread.sleep(1000);

            // Step 9: Verify user is redirected back to the product list page
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("inventory.html"),
                    "❌ Cancel button did not return to the product list page.");

            System.out.println("✅ Test Passed: Cancel button returned to the product list successfully.");

            // Step 10: Close browser
            driver.quit();
        }
        @Test
        public void verifyFinishButtonNavigatesToConfirmationPage() throws InterruptedException {
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

            // Step 4: Add an item to the cart
            driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

            // Step 5: Go to the cart page
            driver.findElement(By.className("shopping_cart_link")).click();
            Thread.sleep(500);

            // Step 6: Click on Checkout
            driver.findElement(By.id("checkout")).click();

            // Step 7: Fill out checkout information
            driver.findElement(By.id("first-name")).sendKeys("John");
            driver.findElement(By.id("last-name")).sendKeys("Doe");
            driver.findElement(By.id("postal-code")).sendKeys("12345");
            driver.findElement(By.id("continue")).click();
            Thread.sleep(1000);

            // Step 8: Click the Finish button
            driver.findElement(By.id("finish")).click();
            Thread.sleep(1000);

            // Step 9: Verify user is on the confirmation page
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("checkout-complete.html"),
                    "❌ Finish button did not navigate to the confirmation page.");

            System.out.println("✅ Test Passed: Finish button navigated to the confirmation page successfully.");

            // Step 10: Close the browser
            driver.quit();
        }
    }


