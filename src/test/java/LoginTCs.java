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
    private final By logoutButton = By.id("logout_sidebar_link");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By errorMsgColor = By.cssSelector("h3[data-test='error']");
    private final By errMsgContainerCcolor = By.cssSelector("div.error-message-container");

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

    @Test(description = "verify the system prevents login with an incorrect username or password.")
    public void testInvalidLogin() {
        actions.type(usernameField, "invalid_user");
        actions.type(passwordField, "wrong_password");
        actions.click(loginButton);

        String errorText = actions.getText(errorMsg);
        Assert.assertTrue(errorText.contains("Username and password do not match any user in this service"),
                "Expected error message not displayed for invalid login.");
    }

    @Test(description = "verify the system prevents login when the username field are left empty.")
    public void testEmptyFieldsLogin() {
        actions.clear(usernameField);
        actions.type(passwordField, "secret_sauce");
        actions.click(loginButton);

        String errorText = actions.getText(errorMsg);
        Assert.assertTrue(errorText.contains("Username is required"),
                "Expected error message not displayed for empty username field.");
    }

    @Test(description = "verify the system prevents login when the password field are left empty.")
    public void testEmptyPasswordLogin() {
        actions.type(usernameField, "standard_user");
        actions.clear(passwordField);
        actions.click(loginButton);

        String errorText = actions.getText(errorMsg);
        Assert.assertTrue(errorText.contains("Password is required"),
                "Expected error message not displayed for empty password field.");
    }

    @Test(description = "verify the system prevents login when both fields are left empty.")
    public void testBothFieldsEmptyLogin() {
        actions.clear(usernameField);
        actions.clear(passwordField);
        actions.click(loginButton);

        String errorText = actions.getText(errorMsg);
        Assert.assertTrue(errorText.contains("Username is required"),
                "Expected error message not displayed for both fields empty.");
    }

    @Test(description = "verify the system prevents login with locked out user.")
    public void testLockedOutUserLogin() {
        actions.type(usernameField, "locked_out_user");
        actions.type(passwordField, "secret_sauce");
        actions.click(loginButton);

        String errorText = actions.getText(errorMsg);
        Assert.assertTrue(errorText.contains("Sorry, this user has been locked out."),
                "Expected error message not displayed for locked out user.");
    }

    @Test(description = "verify the problem_user can log in successfully.")
    public void testProblemUserLogin() {
        actions.type(usernameField, "problem_user");
        actions.type(passwordField, "secret_sauce");
        actions.click(loginButton);

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory.html"),
                "Problem user was not redirected to inventory page after login.");
    }

    @Test(description = "verify the performance_glitch_user can log in successfully.")
    public void testPerformanceGlitchUserLogin() {
        actions.type(usernameField, "performance_glitch_user");
        actions.type(passwordField,"secret_sauce");
        actions.click(loginButton);
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory.html"),
                "Performance glitch user was not redirected to inventory page after login.");
    }

    @Test(description = " verify a logged-in user can successfully log out and log back in.")
    public void testLogoutAndRelogin() {
        // Log in first
        actions.type(usernameField, "standard_user");
        actions.type(passwordField, "secret_sauce");
        actions.click(loginButton);

        // Log out
        actions.click(menuButton);
        actions.click(logoutButton);

        // Verify redirected to login page
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("saucedemo.com"),
                "User was not redirected to login page after logout.");
        // Log back in
        actions.type(usernameField, "standard_user");
        actions.type(passwordField, "secret_sauce");
        actions.click(loginButton);
        currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory.html"),
                "User was not redirected to inventory page after re-login.");
    }

    @Test(description = "To verify the error message text and styling for invalid login.")
    public void testErrorMessageStyling() {
        actions.type(usernameField, "invalid_user");
        actions.type(passwordField, "wrong_password");
        actions.click(loginButton);

        String errorText = actions.getText(errorMsgColor);
        Assert.assertEquals(errorText, "Epic sadface: Username and password do not match any user in this service",
                "Error message text does not match expected.");

        String msgColor = actions.getCssValue(errorMsgColor, "color");
        Assert.assertEquals(msgColor, "rgba(255, 255, 255, 1)",
                "Error message text color does not match expected white color.");

        String color = actions.getCssValue(errMsgContainerCcolor, "background-color");
        Assert.assertEquals(color, "rgba(226, 35, 26, 1)",
                "Error message color does not match expected red color.");
    }

    @Test (description = "To verify all required login page elements are displayed correctly.")
    public void testLoginPageElementsVisibility() {
        Assert.assertTrue(actions.isDisplayed(usernameField), "Username field is not displayed.");
        Assert.assertTrue(actions.isDisplayed(passwordField), "Password field is not displayed.");
        Assert.assertTrue(actions.isDisplayed(loginButton), "Login button is not displayed.");
    }

    @Test (description = "To verify that the password field masks input characters.")
    public void testPasswordFieldMasking() {
        actions.findElement(passwordField).sendKeys("secret_sauce");
        String typeAttribute = actions.findElement(passwordField).getAttribute("type");
        Assert.assertEquals(typeAttribute, "password", "Password field does not mask input characters.");
    }

    @Test (description = "To verify the login page displays correctly on smaller screen sizes.")
    public void testResponsiveDesign() {
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(375, 667)); // iPhone 6/7/8 size
        Assert.assertTrue(actions.isDisplayed(usernameField), "Username field is not displayed on small screen.");
        Assert.assertTrue(actions.isDisplayed(passwordField), "Password field is not displayed on small screen.");
        Assert.assertTrue(actions.isDisplayed(loginButton), "Login button is not displayed on small screen.");
    }

    @Test (description = "To verify the keyboard navigation (tab order) follows the specified sequence.")
    public void testKeyboardNavigation() {
        actions.findElement(usernameField).sendKeys("standard_user");
        actions.findElement(usernameField).sendKeys(org.openqa.selenium.Keys.TAB);
        String activeElementId = actions.activeElementId();
        Assert.assertEquals(activeElementId, "password", "Tab order did not move to password field.");

        actions.findElement(passwordField).sendKeys("secret_sauce");
        actions.findElement(passwordField).sendKeys(org.openqa.selenium.Keys.TAB);
        activeElementId = actions.activeElementId();
        Assert.assertEquals(activeElementId, "login-button", "Tab order did not move to login button.");
    }

    @Test (description = "To verify the error message is cleared after a page refresh.")
    public void testErrorMessageClearedOnRefresh() {
        actions.type(usernameField, "invalid_user");
        actions.type(passwordField, "wrong_password");
        actions.click(loginButton);

        Assert.assertTrue(actions.isDisplayed(errorMsg), "Error message is not displayed after invalid login.");

        actions.refresh();

        Assert.assertFalse(actions.isElementPresent(errorMsg), "Error message is still displayed after page refresh.");
    }

}
