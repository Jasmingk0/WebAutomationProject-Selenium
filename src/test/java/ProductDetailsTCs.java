import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.SupportFuncs;
import utils.UserType;

import java.time.Duration;

public class ProductDetailsTCs {
    //locators
    private final By productNames = By.className("inventory_item_name");
    private final By productImages = By.className("inventory_item_img");
    private final By productDescriptions = By.className("inventory_item_desc");
    private final By productPrices = By.className("inventory_item_price");
    private final By productContainer = By.className("inventory_list");
    private final By backToProductsButton = By.id("back-to-products");
    private final By nameInDetails = By.className("inventory_details_name");
    private final By addToCartButton = By.className("btn_inventory");

    //local variables
    WebDriver driver;
    SupportFuncs actions;

    @BeforeClass
    public void setup() {
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        actions = new SupportFuncs(driver);
        driver.get("https://www.saucedemo.com/");

    }

    @AfterClass
    public void tearDown() {
        actions.takeScreenshot();
        driver.quit();
    }

    @Test(description = "confirm that clicking the product image or name navigates to the detail page.")
    public void verifyNavigationToProductDetails() {
        actions.login(UserType.STANDARD);
        int productsCount = actions.getElementsSize(productNames);
        for (int i = 0; i < productsCount; i++) {
            String expectedName = actions.getElementsText(productNames, i).trim();
            actions.clickOnElementFromList(productNames, i);
            String actualName = actions.getText(nameInDetails).trim();
            assert expectedName.equals(actualName) : "Product name mismatch on details page for product index: " + i;
            actions.click(backToProductsButton);
        }
        actions.takeScreenshot();
    }

    @Test(description = " ensure the Detail Page displays the accurate information for the selected product.")
    public void verifyProductDetailsInfo() {
        actions.login(UserType.STANDARD);
        int productsCount = actions.getElementsSize(productNames);
        for (int i = 0; i < productsCount; i++) {
            String expectedName = actions.getElementsText(productNames, i).trim();
            String expectedDescription = actions.getElementsText(productDescriptions, i).trim();
            String expectedPrice = actions.getElementsText(productPrices, i).trim();

            actions.clickOnElementFromList(productNames, i);

            String actualName = actions.getText(nameInDetails).trim();
            String actualDescription = actions.getText(By.className("inventory_details_desc")).trim();
            String actualPrice = actions.getText(By.className("inventory_details_price")).trim();

            assert expectedName.equals(actualName) : "Product name mismatch on details page for product index: " + i;
            assert expectedDescription.equals(actualDescription) : "Product description mismatch on details page for product index: " + i;
            assert expectedPrice.equals(actualPrice) : "Product price mismatch on details page for product index: " + i;

            actions.click(backToProductsButton);
        }
        actions.takeScreenshot();
    }

    @Test(description = "To ensure the 'Add to Cart' button on the detail page works.")
    public void verifyAddToCartFromDetailsPage() {
        actions.login(UserType.ERROR);
        int productsCount = actions.getElementsSize(productNames);
        for (int i = 0; i < productsCount; i++) {
            actions.clickOnElementFromList(productNames, i);
            By addToCartButton = By.className("btn_inventory");
            actions.click(addToCartButton);
            String buttonText = actions.getText(addToCartButton).trim();
            assert buttonText.equals("Remove") : "Add to Cart button did not change to Remove on details page for product index: " + i;
            actions.click(backToProductsButton);
        }
        actions.takeScreenshot();

    }

    @Test(description = "ensure the 'Remove' button on the detail page works.")
    public void verifyRemoveFromDetailsPage() {
        actions.login(UserType.ERROR);
        int productsCount = actions.getElementsSize(productNames);
        for (int i = 0; i < productsCount; i++) {
            actions.clickOnElementFromList(productNames, i);
            By addToCartButton = By.className("btn_inventory");
            actions.click(addToCartButton); // First add to cart
            actions.click(addToCartButton); // Then remove from cart
            String buttonText = actions.getText(addToCartButton).trim();
            assert buttonText.equals("Add to cart") : "Remove button did not change to Add to cart on details page for product index: " + i;
            actions.click(backToProductsButton);
        }

    }

    @Test(description = "verify that the 'Back to Products' button navigates back to the main product listing page.")
    public void verifyBackToProductsNavigation() {
        actions.login(UserType.STANDARD);
        int productsCount = actions.getElementsSize(productNames);
        for (int i = 0; i < productsCount; i++) {
            actions.clickOnElementFromList(productNames, i);
            actions.click(backToProductsButton);
            assert actions.isDisplayed(productContainer) : "Did not navigate back to product listing page from details page for product index: " + i;
        }
        actions.takeScreenshot();
    }

    @Test(description = "To confirm the cart badge updates correctly when adding an item from the details page.")
    public void verifyCartBadgeUpdateFromDetailsPage() {
        actions.login(UserType.PROBLEM);
        int productsCount = actions.getElementsSize(productNames);
        for (int i = 0; i < productsCount; i++) {
            actions.clickOnElementFromList(productNames, i);
            actions.click(addToCartButton);
            String cartBadgeText = actions.getText(By.className("shopping_cart_badge")).trim();
            assert cartBadgeText.equals(String.valueOf(i + 1)) : "Cart badge did not update correctly after adding product index: " + i;
            actions.click(backToProductsButton);
        }
        actions.takeScreenshot();
    }

    @Test(description = "To confirm the cart badge updates correctly when removing an item from the details page.")
    public void verifyCartBadgeUpdateOnRemoveFromDetailsPage() {
        actions.login(UserType.STANDARD);
        int productsCount = actions.getElementsSize(productNames);
        actions.addAllProductsToCart(productNames, addToCartButton, backToProductsButton);
        // Then remove them one by one and check cart badge
        for (int i = productsCount - 1; i >= 0; i--) {
            actions.clickOnElementFromList(productNames, i);
            actions.click(addToCartButton); // This will remove the item
            String expectedBadge = (i == 0) ? "" : String.valueOf(i);
            String actualBadge = "";
            if (actions.getElementsSize(By.className("shopping_cart_badge")) > 0) {
                actualBadge = actions.getText(By.className("shopping_cart_badge")).trim();
            }
            assert actualBadge.equals(expectedBadge) : "Cart badge did not update correctly after removing product index: " + i;
            actions.click(backToProductsButton);
        }
        actions.takeScreenshot();
    }


}