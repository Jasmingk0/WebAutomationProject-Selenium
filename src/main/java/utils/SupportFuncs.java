package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;

/**
 * Utility class that provides common reusable Selenium actions.
 * Instead of duplicating methods in every test,
 * you can just create one instance of this class and call its methods.
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

    public void navigateTo(String url) {
        driver.navigate().to(url);
    }

    public void maximize() {
        driver.manage().window().maximize();
    }

    public String getCssValue(By locator, String property) {
        return driver.findElement(locator).getCssValue(property);
    }
}
