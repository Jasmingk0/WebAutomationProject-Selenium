import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.SupportFuncs;
import java.time.Duration;

public class ProductPageTCs {
    WebDriver driver;
    SupportFuncs actions;

    @BeforeClass
    public void setup() {
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        actions = new SupportFuncs(driver);
        driver.get("https://www.saucedemo.com/");
        actions.type( By.id("user-name"), "standard_user");
        actions.type( By.id("password"), "secret_sauce");
        actions.click( By.id("login-button"));
        // initialize actions AFTER driver is ready

    }

    @Test (description = "To ensure all required information (name, image, description, price) is present on every product card.")
    public void verifyProductInfo() {
        By productNames = By.className("inventory_item_name");
        By productImages = By.className("inventory_item_img");
        By productDescriptions = By.className("inventory_item_desc");
        By productPrices = By.className("inventory_item_price");
        int productsCount = driver.findElements(productNames).size();
        for (int i = 0; i < productsCount; i++) {
            Assert.assertTrue(driver.findElements(productNames).get(i).isDisplayed(), "Product name is missing for product index: " + i);
            Assert.assertTrue(driver.findElements(productImages).get(i).isDisplayed(), "Product image is missing for product index: " + i);
            Assert.assertTrue(driver.findElements(productDescriptions).get(i).isDisplayed(), "Product description is missing for product index: " + i);
            Assert.assertTrue(driver.findElements(productPrices).get(i).isDisplayed(), "Product price is missing for product index: " + i);
        }
    }



}
