import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.SupportFuncs;

public class FooterAndHeaderTCs {
    //locators
    private final By headerLogo = By.className("app_logo");
    private final By cartIcon = By.className("shopping_cart_link");
    private final By twitterLink = By.cssSelector("a[href='https://twitter.com/saucelabs']");
    private final By facebookLink = By.cssSelector(".social_facebook > a:nth-child(1)");
    private final By linkedInLink = By.cssSelector("a[href='https://www.linkedin.com/company/sauce-labs/']");
    private final By footerLocator = By.className("footer_copy");

    //local variables
    WebDriver driver;
    SupportFuncs actions;

    @BeforeClass
    public void setup() {
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        actions = new SupportFuncs(driver);
        driver.get("https://www.saucedemo.com");
        actions.login(utils.UserType.STANDARD);
        actions.navigateTo("https://www.saucedemo.com/Cart.html");
    }
    /*@AfterClass
    public void tearDown() {
        actions.takeScreenshot();
        driver.quit();
    }*/

    @Test(description = "Verify that the header logo navigates to the Products page when clicked from the Cart page.")
    public void verifyHeaderLogoNavigation() {
        actions.click(headerLogo);
        String currentUrl = driver.getCurrentUrl();
        assert currentUrl.equals("https://www.saucedemo.com/inventory.html") : "Header logo navigation to Products page failed.";
        actions.takeScreenshot();
    }

    @Test(description = "To ensure the cart icon is present and functional on all pages.")
    public void verifyCartIconPresence() {
        actions.navigateTo("https://www.saucedemo.com/inventory-item.html?id=4");
        actions.click(cartIcon);
        actions.assertUrlEquals("https://www.saucedemo.com/cart.html");
        actions.takeScreenshot();
    }

    @Test(description = "To ensure the Twitter link opens the correct external page.")
    public void verifyTwitterLink() {
        actions.click(twitterLink);
        actions.switchToWindow(1);
        actions.assertUrlEquals("https://x.com/saucelabs");
        actions.takeScreenshot();
    }

    @Test(description = "To ensure the Facebook link opens the correct external page.")
    public void verifyFacebookLink() {
        actions.click(facebookLink);
        actions.switchToWindow(1);
        actions.assertUrlEquals("https://www.facebook.com/saucelabs");
        actions.takeScreenshot();
    }

    @Test(description = "To ensure the LinkedIn link opens the correct external page.")
    public void verifyLinkedInLink() {
        actions.click(linkedInLink);
        actions.switchToWindow(1);
        actions.assertUrlEquals("https://www.linkedin.com/company/sauce-labs/");
        actions.takeScreenshot();
    }

    @Test(description = "To ensure the footer displays the correct and current copyright information.")
    public void verifyFooterCopyright() {
        String expectedCopyright = "© 2025 Sauce Labs. All Rights Reserved. Terms of Service | Privacy Policy";
        String actualCopyright = actions.getText(footerLocator).trim();
        assert expectedCopyright.equals(actualCopyright) : "Footer copyright information is incorrect.";
        actions.takeScreenshot();
    }
}
