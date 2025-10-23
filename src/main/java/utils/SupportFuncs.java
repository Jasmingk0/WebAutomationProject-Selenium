package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

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

}
