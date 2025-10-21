import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.SupportFuncs;

import java.time.Duration;

public class LoginTCs {

    WebDriver driver;
    SupportFuncs actions;

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMsg = By.cssSelector("[data-test='error']");
    private final By inventoryContainer = By.id("inventory_container");

    @BeforeClass
    public void setUp() {
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        // initialize actions AFTER driver is ready
        actions = new SupportFuncs(driver);
    }

    @Test(description = "Verify user can log in with valid credentials")
    public void testValidLogin() {
        actions.type(usernameField, "standard_user");
        actions.type(passwordField, "secret_sauce");
        actions.click(loginButton);

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory.html"),
                "User was not redirected to inventory page after login.");

        Assert.assertTrue(actions.isDisplayed(inventoryContainer),
                "Inventory container not visible after login — login may have failed.");
    }
}
