import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.SupportFuncs;

import java.time.Duration;

public class ProductPageTCs {
    //locators
    private final By productNames = By.className("inventory_item_name");
    private final By productImages = By.className("inventory_item_img");
    private final By productDescriptions = By.className("inventory_item_desc");
    private final By productPrices = By.className("inventory_item_price");
    private final By productContainer = By.className("inventory_list");
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
        actions.type(By.id("user-name"), "standard_user");
        actions.type(By.id("password"), "secret_sauce");
        actions.click(By.id("login-button"));
    }

   /* @AfterClass
    public void tearDown() {
        driver.quit();
    }*/

    @Test(description = "To ensure all required information (name, image, description, price) is present on every product card correctly.")
    public void verifyProductInfo() {
        int productsCount = actions.getElementsSize(productNames);
        for (int i = 0; i < productsCount; i++) {
            Assert.assertTrue(actions.ElementsIsDisplayed(productNames, i), "Product name is missing for product index: " + i);
            Assert.assertTrue(actions.ElementsIsDisplayed(productImages, i), "Product image is missing for product index: " + i);
            Assert.assertTrue(actions.ElementsIsDisplayed(productDescriptions, i), "Product description is missing for product index: " + i);
            Assert.assertTrue(actions.ElementsIsDisplayed(productPrices, i), "Product price is missing for product index: " + i);
            String nameText = actions.getElementsText(productNames, i).trim();
            Assert.assertFalse(nameText.isEmpty(), "Product name text is empty for product index: " + i);
            Assert.assertTrue(nameText.matches("^[A-Za-z0-9\\s'-]+$"),
                    "Product name contains invalid characters or is corrupted: '" + nameText + "' at index " + i);
        }
    }

    @Test(description = "Check Layout on Small Screens (Responsiveness)")
    public void verifyLayoutOnSmallScreens() {
        driver.manage().window().setSize(new Dimension(375, 667)); // iPhone 6/7/8 dimensions
        Assert.assertTrue(actions.isDisplayed(productContainer), "Product container is not displayed correctly on small screens.");
    }

    @Test(description = "To verify all product prices adhere to the required currency formatting.")
    public void verifyProductPriceFormat() {
        int productsCount = actions.getElementsSize(productPrices);
        String pricePattern = "^\\$\\d+\\.\\d{2}$"; // Example pattern for USD currency format
        for (int i = 0; i < productsCount; i++) {
            String priceText = actions.getElementsText(productPrices, i);
            Assert.assertTrue(priceText.matches(pricePattern), "Product price format is incorrect for product index: " + i);
        }
    }


}
