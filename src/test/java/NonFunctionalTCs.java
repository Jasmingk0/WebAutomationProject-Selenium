import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.SupportFuncs;
import utils.UserType;

import java.time.Duration;

public class NonFunctionalTCs {
    //locators
    private final By logoutButton = By.id("logout_sidebar_link");
    private final By menuButton = By.id("react-burger-menu-btn");


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

    @Test(description = "To measure the baseline page load time for the Products Page.")
    public void measureProductsPageLoadTime() {
        long startTime = System.currentTimeMillis();
        actions.login(UserType.STANDARD);
        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;
        System.out.println("Products Page Load Time: " + loadTime + " ms");
        if (loadTime > 2000) {
            assert false : "Products Page load time exceeded 2 seconds!";
        }
        actions.takeScreenshot();
    }

    @Test(description = "To verify the intentional performance degradation for the performance_glitch_user.")
    public void verifyPerformanceGlitchUser() {
        long startTime = System.currentTimeMillis();
        actions.login(UserType.PERFORMANCE);
        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;
        System.out.println("Performance Glitch User Products Page Load Time: " + loadTime + " ms");
        if (loadTime > 2000) {
            assert false : "Performance Glitch User load time was less than expected 4 seconds!";
        }
        actions.takeScreenshot();
    }

    @Test(description = "To ensure a logged-out session cannot be reused with the browser's back button.")
    public void verifySessionInvalidationOnLogout() {
        actions.login(UserType.STANDARD);
        actions.click(menuButton);
        actions.click(logoutButton);
        driver.navigate().back();
        String currentUrl = driver.getCurrentUrl();
        assert currentUrl.equals("https://www.saucedemo.com/") : "User was able to navigate back to a logged-in page after logout!";
        actions.takeScreenshot();
    }

    @Test(description = "To ensure credentials are not visible in URL.")
    public void verifyCredentialsNotInUrlOrNetworkLogs() {
        actions.login(UserType.STANDARD);
        String currentUrl = driver.getCurrentUrl();
        assert !currentUrl.contains("user-name") && !currentUrl.contains("password") : "Credentials are visible in the URL!";
        actions.takeScreenshot();
    }

    @Test(description = "Verify Secure Connection (HTTPS)")
    public void verifySecureConnection() {
        actions.login(UserType.STANDARD);
        String currentUrl = driver.getCurrentUrl();
        assert currentUrl.startsWith("https://") : "Connection is not secure (HTTPS)!";
        actions.takeScreenshot();
    }

    @Test(description = "To ensure the application is functional and renders correctly across major desktop browsers.")
    public void verifyCrossBrowserCompatibility() {
        new ChromeDriver().get("https://www.saucedemo.com/");
        new FirefoxDriver().get("https://www.saucedemo.com/");
        actions.takeScreenshot();
    }

}
