import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.SupportFuncs;

import java.time.Duration;


public class CartPageTCs {
    //locators
    private final By cartIcon = By.className("shopping_cart_link");
    private final By cartTitle = By.className("title");
    private final By cartItems = By.className("cart_item");
    private final By addProduct1Btn = By.id("add-to-cart-sauce-labs-backpack");
    private final By addProduct2Btn = By.id("add-to-cart-sauce-labs-bike-light");
    private final By removeProductBtn1 = By.id("remove-sauce-labs-backpack");
    private final By removeProductBtn2 = By.id("remove-sauce-labs-bike-light");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By continueShoppingBtn = By.id("continue-shopping");
    private final By productsPageTitle = By.className("title");
    private final By checkoutBtn = By.id("checkout");
    private final By checkoutPageTitle = By.className("title");

    // Local variables
    WebDriver driver;
    SupportFuncs actions;

    @BeforeClass
    public void setup() {
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        actions = new SupportFuncs(driver);
        driver.get("https://www.saucedemo.com/");
        actions.type(By.id("user-name"), "standard_user");
        actions.type(By.id("password"), "secret_sauce");
        actions.click(By.id("login-button"));

    }

   /* @AfterClass
    public void tearDown() {
        driver.quit();
    }*/

    @Test(description = "Verify that the cart page loads correctly and displays the correct title.")
    public void verifyCartPageTitle() {
        actions.click(cartIcon);
        String expectedTitle = "Your Cart";
        String actualTitle = actions.getText(cartTitle);
        assert actualTitle.equals(expectedTitle) : "Cart page title is incorrect. Expected: " + expectedTitle + ", but got: " + actualTitle;
    }

    @Test(description = "Verify that items can be added to the cart and are displayed correctly.")
    public void verifyAddItemsToCart() {
        actions.click(addProduct1Btn);
        actions.click(addProduct2Btn);
        actions.click(cartIcon);
        String expectedItemName1 = "Sauce Labs Backpack";
        String expectedItemName2 = "Sauce Labs Bike Light";
        String itemCountText = actions.getText(cartBadge);

        assert itemCountText.equals("2") : "Cart icon item count is incorrect. Expected: 1, but got: " + itemCountText;
        String actualItemName = actions.getElementsText(cartItems, 0).split("\n")[1]; // Assuming the item name is the second line in the cart item text
        assert actualItemName.equals(expectedItemName1) : "Item in cart is incorrect. Expected: " + expectedItemName1 + ", but got: " + actualItemName;
        actualItemName = actions.getElementsText(cartItems, 1).split("\n")[1];
        assert actualItemName.equals(expectedItemName2) : "Item in cart is incorrect. Expected: " + expectedItemName2 + ", but got: " + actualItemName;
    }

    @Test(description = "Verify that the cart updates correctly when items are removed.")
    public void verifyRemoveItemsFromCart() {
        actions.click(addProduct1Btn);
        actions.click(addProduct2Btn);
        actions.click(removeProductBtn2);
        actions.click(cartIcon);
        String itemCountText = actions.getText(cartBadge);

        assert itemCountText.equals("1") : "Cart icon item count is incorrect. Expected: 1, but got: " + itemCountText;
        int itemCountAfterRemoval = actions.getElementsSize(cartItems);
        assert itemCountAfterRemoval == 1 : "Item was not removed from cart correctly.";
    }

    @Test(description = "Verify that the cart is empty when no items have been added.")
    public void verifyEmptyCart() {
        actions.click(addProduct2Btn);
        actions.click(removeProductBtn2);
        actions.click(cartIcon);
        boolean isBadgeDisplayed = actions.getElementsSize(cartBadge) > 0;

        Assert.assertFalse(isBadgeDisplayed, "Cart icon badge is displayed when cart should be empty.");
        int itemCount = actions.getElementsSize(cartItems);
        assert itemCount == 0 : "Cart is not empty when it should be.";
    }

    @Test(description = "Verify that the cart icon displays the correct item count.")
    public void verifyCartIconItemCount() {
        actions.click(addProduct1Btn);
        String itemCountText = actions.getText(By.className("shopping_cart_badge"));

        assert itemCountText.equals("1") : "Cart icon item count is incorrect. Expected: 1, but got: " + itemCountText;
    }

    @Test(description = "To ensure the 'Continue Shopping' button redirects the user to the Products page.")
    public void verifyContinueShoppingButton() {
        actions.click(addProduct1Btn);
        actions.click(cartIcon);
        actions.click(continueShoppingBtn);
        String expectedTitle = "Products";
        String actualTitle = actions.getText(productsPageTitle);

        assert actualTitle.equals(expectedTitle) : "Continue Shopping button did not redirect to Products page. Expected title: " + expectedTitle + ", but got: " + actualTitle;
    }

    @Test(description = "To ensure the 'Checkout' button redirects the user to the initial Checkout page.")
    public void verifyCheckoutButton() {
        actions.click(addProduct1Btn);
        actions.click(cartIcon);
        actions.click(checkoutBtn);
        String expectedTitle = "Checkout: Your Information";
        String actualTitle = actions.getText(checkoutPageTitle);

        assert actualTitle.equals(expectedTitle) : "Checkout button did not redirect to Checkout page. Expected title: " + expectedTitle + ", but got: " + actualTitle;
    }
}


