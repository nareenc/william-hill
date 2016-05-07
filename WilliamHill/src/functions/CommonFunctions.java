package functions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

public class CommonFunctions {
    private String baseUrl;
    protected WebDriver driver;
    ReadXMLData data = new ReadXMLData();
    protected Wait<WebDriver> wait;
    
 //   private Wait

    public Wait<WebDriver> getWait() {
        return wait;
    }

    public void setWait(Wait<WebDriver> wait) {
        this.wait = wait;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    @SuppressWarnings("unchecked")
    @BeforeTest
    public void setUp() {
        driver = new FirefoxDriver();
        baseUrl = data.getHorseRaceData("williamhill_url");
        driver.get(baseUrl);
        wait = new FluentWait<WebDriver>(driver)
                .withTimeout(60, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);
    }

    public WebElement clickableElement(final By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public WebElement webElement(final By by) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void pass(String value) {
        System.out.println(value);
    }

    public void fail(String value) {
        System.err.println(value);
    }

    @AfterTest
    public void tearDown() throws Exception {
        driver.quit();
    }
}