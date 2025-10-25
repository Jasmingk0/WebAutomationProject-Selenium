package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

/**
 * Utility class that provides common reusable Selenium actions.
 * Instead of duplicating methods in every test.
 */

public class SupportFuncs {

    private WebDriver driver;

    // Constructor — connects this class to the WebDriver you're using in your tests
    public SupportFuncs(WebDriver driver) {
        this.driver = driver;
    }

    public void login(UserType user) {
        By usernameLocator = By.id("user-name");
        By passwordLocator = By.id("password");
        By loginButtonLocator = By.id("login-button");
        String username = UserFactory.getUsername(user);
        type(usernameLocator, username);
        type(passwordLocator, "secret_sauce");
        click(loginButtonLocator);
    }

    public String getDomProperty(By by, String property) {
        return findElement(by).getDomProperty(property);
    }

    public String getText(By by) {
        return findElement(by).getText();
    }

    public boolean isEnabled(By by) {
        return findElement(by).isEnabled();
    }

    public boolean isDisplayed(By by) {
        return findElement(by).isDisplayed();
    }

    public boolean ElementsIsDisplayed(By by, int index) {
        return driver.findElements(by).get(index).isDisplayed();
    }

    public int getElementsSize(By by) {
        return driver.findElements(by).size();
    }

    public String getElementsText(By by, int index) {
        return driver.findElements(by).get(index).getText();
    }

    public void upload(By by, String path) {
        clear(by);
        String userDir = System.getProperty("user.dir");
        findElement(by).sendKeys(userDir + File.separator + path);
    }

    public void type(By by, String text) {
        clear(by);
        findElement(by).sendKeys(text);
    }

    public void clear(By by) {
        findElement(by).clear();
    }

    public void click(By by) {
        findElement(by).click();
    }

    public void clickOnElementFromList(By by, int index) {
        driver.findElements(by).get(index).click();
    }

    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    public WebElement findElements(By by, int index) {
        return driver.findElements(by).get(index);
    }

    public void navigateTo(String url) {
        driver.navigate().to(url);
    }

    public void maximize() {
        driver.manage().window().maximize();
    }

    public void addAllProductsToCart(By products, By addButton, By backToProductsButton) {
        int productsCount = getElementsSize(products);
        // First add all products to cart
        for (int i = 0; i < productsCount; i++) {
            clickOnElementFromList(products, i);
            click(addButton);
            click(backToProductsButton);
        }
    }

    public void removeAllProductsFromCart(By products, By removeButton, By backToProductsButton) {
        int productsCount = getElementsSize(products);
        // Then remove all products from cart
        for (int i = 0; i < productsCount; i++) {
            clickOnElementFromList(products, i);
            click(removeButton);
            click(backToProductsButton);
        }
    }

    public String getCssValue(By locator, String property) {
        return driver.findElement(locator).getCssValue(property);
    }

    public void quit() {
        driver.quit();
    }

    public String activeElementId() {
        return driver.switchTo().activeElement().getAttribute("id");
    }

    public void refresh() {
        driver.navigate().refresh();
    }

    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void getUrl(String url) {
        driver.get(url);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public void waitUntilVisable(By by, java.time.Duration duration) {
        WebDriverWait wait = new WebDriverWait(driver, duration);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void selectByValue(By by, String value) {
        Select select = new Select(findElement(by));
        select.selectByValue(value);
    }

    public void select(By by, int index) {
        Select select = new Select(findElement(by));
        select.selectByIndex(index);
    }

    public void selectByVisibleText(By by, String text) {
        Select select = new Select(findElement(by));
        select.selectByVisibleText(text);
    }

    public void rightClick(By by) {
        Actions action = new Actions(driver);
        action.contextClick(findElement(by)).perform();
    }

    public void doubleclick(By by) {
        Actions action = new Actions(driver);
        action.doubleClick(findElement(by)).perform();
    }

    public void hoverOver(By by) {
        Actions action = new Actions(driver);
        action.moveToElement(findElement(by)).perform();
    }

    public void dragAndDrop(By source, By target) {
        Actions actions = new Actions(driver);
        actions.dragAndDrop(findElement(source), findElement(target)).perform();
    }

    public void takeScreenshot() {

        String folderPath = "src/test/resources/screenshots/";

        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();

        String baseName = methodName + ".png";
        File screenshotFile = new File(folderPath + baseName);

        int counter = 1;
        while (screenshotFile.exists()) {
            screenshotFile = new File(folderPath + methodName + "(" + counter + ").png");
            counter++;
        }

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, screenshotFile);
            System.out.println("Screenshot saved: " + screenshotFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scrollToElement(By by) {
        Actions actions = new Actions(driver);
        actions.moveToElement(findElement(by)).perform();
    }

    public void resetActions() {
        ((RemoteWebDriver) driver).resetInputState();
    }

    public void switchToWindow(int tabIndex) {
        driver.switchTo().window(driver.getWindowHandles().toArray()[tabIndex].toString());
    }

    public void closeCurrentTab() {
        driver.close();
    }

    public void switchToFrame(By by) {
        driver.switchTo().frame(findElement(by));
    }

    public void alertAccept() {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public void alertDismiss() {
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
    }

    public void alertGetText() {
        Alert alert = driver.switchTo().alert();
        System.out.println("Alert text: " + alert.getText());
    }

    public void alertEnterText(String text) {
        Alert alert = driver.switchTo().alert();
        alert.sendKeys(text);
    }

    public void assertFieldIsEmpty(By locator) {
        String value = driver.findElement(locator).getAttribute("value").trim();
        assert value.isEmpty() : " field is not cleared .";
    }

    public void assertFieldIsNotEmpty(By locator) {
        String value = driver.findElement(locator).getAttribute("value").trim();
        assert !value.isEmpty() : " field is cleared .";
    }

    public void assertIsDisplayed(By locator) {
        boolean isDisplayed = driver.findElement(locator).isDisplayed();
        assert isDisplayed : " element is not displayed .";
    }

    public void assertIsNotDisplayed(By locator) {
        boolean isDisplayed = driver.findElement(locator).isDisplayed();
        assert !isDisplayed : " element is displayed .";
    }

    public void assertUrlEquals(String expectedUrl) {
        String currentUrl = driver.getCurrentUrl();
        assert currentUrl.equals(expectedUrl) : "URL does not match the expected URL.";
    }

    public void switchToNewTab() {
        String originalHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }


}
