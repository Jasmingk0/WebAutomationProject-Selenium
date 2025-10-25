import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.SupportFuncs;

public class CheckOutInfoTCs {
    //locators
    private final By productNames = By.className("inventory_item_name");
    private final By PageTitle = By.className("title");
    private final By addToCartButton = By.className("btn_inventory");
    private final By cartBtb = By.className("shopping_cart_link");
    private final By checkoutButton = By.id("checkout");
    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By errorMessageContainer = By.className("error-message-container");
    private final By cancelButton = By.id("cancel");


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
        actions.click(addToCartButton);
    }
    /*@AfterClass
    public void tearDown() {
        actions.takeScreenshot();
        driver.quit();
    }*/

    @Test(description = "To ensure the user is correctly routed from the Cart Page to the Checkout Info Page.")
    public void verifyNavigationToCheckoutInfo() {
        actions.click(cartBtb);
        actions.click(checkoutButton);

        String pageTitle = actions.getText(PageTitle).trim();

        assert pageTitle.equals("Checkout: Your Information") : "Navigation to Checkout Info Page failed.";

        actions.takeScreenshot();
    }

    @Test(description = "To ensure proceeding fails when the First Name field is empty.")
    public void verifyCheckoutFailsOnEmptyFirstName() {
        actions.click(cartBtb);
        actions.click(checkoutButton);
        actions.type(lastNameField, "Doe");
        actions.type(postalCodeField, "12345");
        actions.click(continueButton);

        String errorMessage = actions.getText(errorMessageContainer).trim();

        assert errorMessage.equals("Error: First Name is required") : "Error message for empty First Name is incorrect.";

        actions.takeScreenshot();
    }

    @Test(description = "To ensure proceeding fails when the Last Name field is empty.")
    public void verifyCheckoutFailsOnEmptyLastName() {
        actions.click(cartBtb);
        actions.click(checkoutButton);
        actions.type(firstNameField, "John");
        actions.type(postalCodeField, "12345");
        actions.click(continueButton);

        String errorMessage = actions.getText(errorMessageContainer).trim();

        assert errorMessage.equals("Error: Last Name is required") : "Error message for empty Last Name is incorrect.";

        actions.takeScreenshot();
    }


    @Test(description = "To ensure proceeding fails when the Postal Code field is empty.")
    public void verifyCheckoutFailsOnEmptyPostalCode() {
        actions.click(cartBtb);
        actions.click(checkoutButton);
        actions.type(firstNameField, "John");
        actions.type(lastNameField, "Doe");
        actions.click(continueButton);

        String errorMessage = actions.getText(errorMessageContainer).trim();

        assert errorMessage.equals("Error: Postal Code is required") : "Error message for empty Postal Code is incorrect.";

        actions.takeScreenshot();
    }

    @Test(description = "To ensure the error messages for missing fields are textually correct.")
    public void verifyErrorMessagesText() {
        actions.click(cartBtb);
        actions.click(checkoutButton);
        actions.click(continueButton);

        String errorMessage = actions.getText(errorMessageContainer).trim();

        assert errorMessage.equals("Error: First Name is required") : "Error message text is incorrect.";

        actions.takeScreenshot();
    }

    @Test(description = "To ensure proceeding to the next step is successful when all fields are correctly filled.")
    public void verifySuccessfulCheckoutInfoSubmission() {
        actions.click(cartBtb);
        actions.click(checkoutButton);
        actions.type(firstNameField, "John");
        actions.type(lastNameField, "Doe");
        actions.type(postalCodeField, "12345");
        actions.click(continueButton);

        String pageTitle = actions.getText(PageTitle).trim();

        assert pageTitle.equals("Checkout: Overview") : "Checkout Info submission failed.";

        actions.takeScreenshot();
    }

    @Test(description = "To verify that the 'Cancel' button on the Checkout Info Page redirects back to the Cart Page.")
    public void verifyCancelButtonRedirectsToCart() {
        actions.click(cartBtb);
        actions.click(checkoutButton);
        actions.click(cancelButton);

        String pageTitle = actions.getText(PageTitle).trim();

        assert pageTitle.equals("Your Cart") : "Cancel button did not redirect to Cart Page.";

        actions.takeScreenshot();
    }

    @Test(description = "To ensure 'Cancel' clears any entered data when returning to the Cart.")
    public void verifyCancelClearsData() {
        actions.click(cartBtb);
        actions.click(checkoutButton);
        actions.type(firstNameField, "John");
        actions.type(lastNameField, "Doe");
        actions.type(postalCodeField, "12345");
        actions.click(cancelButton);
        actions.click(checkoutButton);

        actions.assertFieldIsEmpty(firstNameField);
        actions.assertFieldIsEmpty(lastNameField);
        actions.assertFieldIsEmpty(postalCodeField);

        actions.takeScreenshot();
    }


}
