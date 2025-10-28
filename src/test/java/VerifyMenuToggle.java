import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

    public class VerifyMenuToggle {
        @Test
        public void verifyMenuIconTogglesSideNavigation() throws InterruptedException {
            WebDriver driver = new EdgeDriver();
            driver.manage().window().maximize();

            // Step 1: Open website
            driver.get("https://www.saucedemo.com/");

            // Step 2: Login
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();
            Thread.sleep(1000);

            // Step 3: Locate menu (burger) button
            WebElement menuButton = driver.findElement(By.id("react-burger-menu-btn"));

            // Step 4: Click menu to open side navigation
            menuButton.click();
            Thread.sleep(1000);

            // Step 5: Verify side navigation is displayed
            WebElement sideMenu = driver.findElement(By.className("bm-menu-wrap"));
            Assert.assertTrue(sideMenu.isDisplayed(), "❌ Side navigation menu did not open after clicking the menu icon!");
            System.out.println("✅ PASS: Side navigation menu opened successfully.");

            // Step 6: Click menu button again to close side navigation
            driver.findElement(By.id("react-burger-cross-btn")).click();
            Thread.sleep(1000);

            // Step 7: Verify side navigation is hidden
            boolean isHidden = !sideMenu.isDisplayed();
            Assert.assertTrue(isHidden, "❌ Side navigation menu did not close after clicking the close icon!");
            System.out.println("✅ PASS: Side navigation menu closed successfully.");

            driver.quit();
        }
        @Test
        public void verifyLogoutRedirectsToLoginPage() throws InterruptedException {
            WebDriver driver = new EdgeDriver();
            driver.manage().window().maximize();

            // Step 1: Open website
            driver.get("https://www.saucedemo.com/");

            // Step 2: Login
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();
            Thread.sleep(1000);

            // Step 3: Open side navigation menu
            driver.findElement(By.id("react-burger-menu-btn")).click();
            Thread.sleep(1000);

            // Step 4: Click Logout option
            WebElement logoutLink = driver.findElement(By.id("logout_sidebar_link"));
            logoutLink.click();
            Thread.sleep(1000);

            // Step 5: Verify redirected to login page
            WebElement loginButton = driver.findElement(By.id("login-button"));
            Assert.assertTrue(loginButton.isDisplayed(), "❌ Logout failed — Login button not visible after redirect.");
            System.out.println("✅ PASS: Successfully logged out and redirected to login page.");

            driver.quit();
        }
        @Test
        public void verifyAllItemsReturnsToInventoryPage() throws InterruptedException {
            WebDriver driver = new EdgeDriver();
            driver.manage().window().maximize();

            // Step 1: Open website
            driver.get("https://www.saucedemo.com/");

            // Step 2: Login
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();
            Thread.sleep(1000);

            // Step 3: Navigate to Cart page (to leave inventory page)
            driver.findElement(By.id("shopping_cart_container")).click();
            Thread.sleep(1000);

            // Step 4: Open the side menu
            driver.findElement(By.id("react-burger-menu-btn")).click();
            Thread.sleep(1000);

            // Step 5: Click on "All Items"
            WebElement allItemsLink = driver.findElement(By.id("inventory_sidebar_link"));
            allItemsLink.click();
            Thread.sleep(1000);

            // Step 6: Verify user is redirected back to Inventory page
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("inventory.html"),
                    "❌ Navigation failed — Not redirected to inventory page.");
            System.out.println("✅ PASS: 'All Items' successfully returned to Inventory page.");

            driver.quit();
        }
        @Test
        public void verifyResetAppStateClearsCartAndUI() throws InterruptedException {
            WebDriver driver = new EdgeDriver();
            driver.manage().window().maximize();

            // Step 1: Navigate to SauceDemo login page
            driver.get("https://www.saucedemo.com/");

            // Step 2: Login
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();
            Thread.sleep(1000);

            // Step 3: Add an item to cart
            WebElement addToCart = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
            addToCart.click();
            Thread.sleep(500);

            // Step 4: Verify cart badge is visible
            WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
            Assert.assertTrue(cartBadge.isDisplayed(), "❌ Cart badge not displayed after adding item.");
            System.out.println("✅ Item added successfully — Cart badge visible.");

            // Step 5: Open side menu
            driver.findElement(By.id("react-burger-menu-btn")).click();
            Thread.sleep(1000);

            // Step 6: Click "Reset App State"
            driver.findElement(By.id("reset_sidebar_link")).click();
            Thread.sleep(1000);

            // Step 7: Close menu
            driver.findElement(By.id("react-burger-cross-btn")).click();
            Thread.sleep(500);

            // Step 8: Verify cart badge is removed (cart cleared)
            boolean isBadgeCleared = driver.findElements(By.className("shopping_cart_badge")).isEmpty();
            Assert.assertTrue(isBadgeCleared, "❌ Cart badge still visible — Reset App State failed.");
            System.out.println("✅ Cart cleared successfully — Cart badge removed.");

            // Step 9: Verify button text reset (should return to 'Add to cart')
            WebElement resetButton = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
            String buttonText = resetButton.getText();
            Assert.assertEquals(buttonText, "Add to cart", "❌ UI not reset — Button text not restored.");
            System.out.println("✅ UI reset successfully — Button text restored to 'Add to cart'.");

            driver.quit();
        }
        @Test
        public void verifyAllItemsDuringCheckout() throws InterruptedException {
            WebDriver driver = new EdgeDriver();
            driver.manage().window().maximize();

            // Step 1: Navigate to SauceDemo
            driver.get("https://www.saucedemo.com/");

            // Step 2: Login
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();
            Thread.sleep(1000);

            // Step 3: Add a product to cart
            driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
            Thread.sleep(500);

            // Step 4: Go to cart
            driver.findElement(By.id("shopping_cart_container")).click();
            Thread.sleep(1000);

            // Step 5: Proceed to checkout
            driver.findElement(By.id("checkout")).click();
            Thread.sleep(1000);

            // Step 6: Start filling checkout info (simulate mid-checkout)
            driver.findElement(By.id("first-name")).sendKeys("Miran");
            driver.findElement(By.id("last-name")).sendKeys("Hassan");
            driver.findElement(By.id("postal-code")).sendKeys("12345");
            Thread.sleep(500);

            // Step 7: Open the menu (while still in checkout)
            driver.findElement(By.id("react-burger-menu-btn")).click();
            Thread.sleep(1000);

            // Step 8: Click "All Items"
            driver.findElement(By.id("inventory_sidebar_link")).click();
            Thread.sleep(1000);

            // Step 9: Verify redirection to Products/Inventory page
            WebElement inventoryPage = driver.findElement(By.className("inventory_list"));
            Assert.assertTrue(inventoryPage.isDisplayed(), "❌ 'All Items' did not return to inventory page during checkout.");

            System.out.println("✅ PASS: 'All Items' successfully navigated to the product list during checkout.");

            driver.quit();
        }
        @Test
        public void verifyAllItemsFromCheckout() throws InterruptedException {
            WebDriver driver = new EdgeDriver();
            driver.manage().window().maximize();

            // Step 1: Navigate to SauceDemo
            driver.get("https://www.saucedemo.com/");

            // Step 2: Login
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();
            Thread.sleep(1000);

            // Step 3: Add an item to the cart
            driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
            Thread.sleep(500);

            // Step 4: Go to cart
            driver.findElement(By.id("shopping_cart_container")).click();
            Thread.sleep(1000);

            // Step 5: Start checkout
            driver.findElement(By.id("checkout")).click();
            Thread.sleep(1000);

            // Step 6: Enter some checkout info (simulate mid-checkout)
            driver.findElement(By.id("first-name")).sendKeys("Miran");
            driver.findElement(By.id("last-name")).sendKeys("Hassan");
            driver.findElement(By.id("postal-code")).sendKeys("12345");
            Thread.sleep(500);

            // Step 7: Open side menu
            driver.findElement(By.id("react-burger-menu-btn")).click();
            Thread.sleep(1000);

            // Step 8: Click on "All Items"
            driver.findElement(By.id("inventory_sidebar_link")).click();
            Thread.sleep(1000);

            // Step 9: Verify that we are back on the inventory page
            WebElement inventoryContainer = driver.findElement(By.id("inventory_container"));
            Assert.assertTrue(inventoryContainer.isDisplayed(), "❌ 'All Items' did not navigate back to the product list page.");

            System.out.println("✅ PASS: 'All Items' successfully returned to the Products page even during checkout.");

            driver.quit();
        }
        @Test
        public void verifyLogoutFromCheckout() throws InterruptedException {
            WebDriver driver = new EdgeDriver();
            driver.manage().window().maximize();

            // Step 1: Navigate to SauceDemo
            driver.get("https://www.saucedemo.com/");

            // Step 2: Login
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();
            Thread.sleep(1000);

            // Step 3: Add an item to the cart
            driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
            Thread.sleep(500);

            // Step 4: Go to cart
            driver.findElement(By.id("shopping_cart_container")).click();
            Thread.sleep(1000);

            // Step 5: Start checkout
            driver.findElement(By.id("checkout")).click();
            Thread.sleep(1000);

            // Step 6: Fill part of checkout form (simulate mid-checkout)
            driver.findElement(By.id("first-name")).sendKeys("Miran");
            driver.findElement(By.id("last-name")).sendKeys("Hassan");
            driver.findElement(By.id("postal-code")).sendKeys("12345");
            Thread.sleep(500);

            // Step 7: Open side menu
            driver.findElement(By.id("react-burger-menu-btn")).click();
            Thread.sleep(1000);

            // Step 8: Click on "Logout"
            driver.findElement(By.id("logout_sidebar_link")).click();
            Thread.sleep(1000);

            // Step 9: Verify user is redirected to login page
            WebElement loginButton = driver.findElement(By.id("login-button"));
            Assert.assertTrue(loginButton.isDisplayed(), "❌ Logout failed — user not redirected to login page.");

            System.out.println("✅ PASS: User successfully logged out from the checkout page and returned to login screen.");

            driver.quit();
        }

    }

