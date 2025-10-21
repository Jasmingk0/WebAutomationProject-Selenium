
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {

    WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Automatically sets up ChromeDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        // Implicit wait and window setup
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // Navigate to site
        driver.get("https://www.saucedemo.com/");
    }

    @Test(description = "Verify user can log in with valid credentials")
    public void testValidLogin() {
        // Locate elements
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        // Enter valid credentials
        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        // Assertion: check user is redirected to inventory page
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory.html"),
                "User was not redirected to inventory page after login.");

        // Verify presence of inventory container (sanity check)
        WebElement inventoryContainer = driver.findElement(By.id("inventory_container"));
        Assert.assertTrue(inventoryContainer.isDisplayed(),
                "Inventory container not visible after login — login may have failed.");
    }


}
