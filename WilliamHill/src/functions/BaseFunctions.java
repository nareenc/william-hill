
package functions;

import static functions.BrowserConstants.BROWSER_CHROME;
import static functions.BrowserConstants.BROWSER_FIREFOX;
import static functions.BrowserConstants.BROWSER_IE;
import static functions.BrowserConstants.BROWSER_REMOTE;
import static functions.BrowserConstants.BROWSER_SAFARI;
import static data.ElementValues.*;
import functions.WebDriverUtils;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;
import functions.ReadXMLData;
import org.apache.commons.lang3.Validate;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;


import com.google.common.base.Function;

public class BaseFunctions {

    private ReadXMLData rxml;
    private String browser;
    private String environment;
    private String remoteUrl;
    private String remoteWebDriverUrl;
    private String environmentUrl;
    private WebDriver webDriver;
    private String brand;
    private String currencysymbol;
    private Boolean record;
    private Boolean previousSearch;
    private String client_user_id;
    protected Wait<WebDriver> wait;

    public BaseFunctions() {
        try {
            rxml = new ReadXMLData();
            browser = getData().commonData("browser");
            remoteUrl = getData().commonData("remoteUrl");
            environment = getData().commonData("environment");
            environmentUrl = determineUrl(environment);            
            record = Boolean.valueOf(getData().commonData("record"));
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    protected String getBrowser() {
        return browser;
    }

    protected String getRemoteWebDriverUrl() {
        return remoteWebDriverUrl;
    }

    protected String getEnvironment() {
        return environment;
    }

    public String getBrand() {
        return brand;
    }

    public String getCurrencySymbol() {
        return currencysymbol;
    }

    public String getUserName() {
        return client_user_id;
    }

    public boolean getRecord() {
        return record;
    }

    public WebDriver getWebDriver() {
        Validate.notNull(webDriver, "Failed to retrieve valid web driver");
        return webDriver;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public Wait<WebDriver> getWait() {
        return wait;
    }

    public void setWait(Wait<WebDriver> wait) {
        this.wait = wait;
    }

    protected ReadXMLData getData() {
        return rxml;
    }

    private String determineUrl(String environment) {
        return "williamhill".equalsIgnoreCase(environment)
                ? williamhill_url
                : "devtest".equalsIgnoreCase(environment)
                ? devtest_url
                : "devtestlocal".equalsIgnoreCase(environment)
                ? dtlocal_url
                : "devapps".equalsIgnoreCase(environment)
                ? devapps_url
                : "sandbox".equalsIgnoreCase(environment)
                ? sandbox_url
                : "sandboxtest".equalsIgnoreCase(environment)
                ? sandboxtest_url
                : "amazon".equalsIgnoreCase(environment)
                ? amazon_url
                : "local".equalsIgnoreCase(environment)
                ? local_url
                : "mylocal".equalsIgnoreCase(environment)
                ? mylocal_url
                : "recorder".equalsIgnoreCase(environment)
                ? recorder_url
                : "qa".equalsIgnoreCase(environment)
                ? qa_url
                : "devtestHUB".equalsIgnoreCase(environment)
                ? devtestHUB_url
                : "Test1UK".equalsIgnoreCase(environment)
                ? test1uk_url
                : "Test2UK".equalsIgnoreCase(environment)
                ? test2uk_url
                : "Test3UK".equalsIgnoreCase(environment)
                ? test3uk_url
                : "UKTest1".equalsIgnoreCase(environment)
                ? uktest1_url
                : "UKStaging".equalsIgnoreCase(environment)
                ? ukstaging_url
                : "StagingUK".equalsIgnoreCase(environment)
                ? staginguk_url
                : "UKAngular".equalsIgnoreCase(environment)
                ? uk_angular_url
                : "SAIAngular".equalsIgnoreCase(environment)
                ? sai_angular_url
                : "";
    }

    protected String getEnvironmentUrl() {
        return environmentUrl;
    }

    protected Boolean getPS() {
        return previousSearch;
    }

    protected void start(String remoteWebDriverUrl) throws MalformedURLException {
        if (webDriver == null) {
            this.remoteWebDriverUrl = remoteWebDriverUrl;
            webDriver =
                    WebDriverUtils
                            .getInstance()
                            .createWebDriver(
                                    browser,
                                    isEmpty(remoteWebDriverUrl) ? remoteUrl : remoteWebDriverUrl);
            webDriver.get(getEnvironmentUrl());
            if (webDriver instanceof RemoteWebDriver) {
                String remoteBrowserName = ((RemoteWebDriver) webDriver).getCapabilities().getBrowserName();
                if ("internet explorer".equalsIgnoreCase(remoteBrowserName)) {
                    remoteBrowserName = BROWSER_IE;
                    browser = remoteBrowserName;
                } else if ("firefox".equalsIgnoreCase(remoteBrowserName)) {
                    remoteBrowserName = BROWSER_FIREFOX;
                    browser = remoteBrowserName;
                } else if ("chrome".equalsIgnoreCase(remoteBrowserName)) {
                    remoteBrowserName = BROWSER_CHROME;
                    browser = remoteBrowserName;
                } else if ("safari".equalsIgnoreCase(remoteBrowserName)) {
                    remoteBrowserName = BROWSER_SAFARI;
                    browser = remoteBrowserName;
                } else {
                    browser = BROWSER_REMOTE + " - " + remoteBrowserName;
                }
            }
        }
        wait = new FluentWait<WebDriver>(getWebDriver())
                .withTimeout(360, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);
    }

    protected void stop() throws Exception {
        getWebDriver().quit();
        WebDriverUtils.getInstance().removeDriver(getBrowser());
    }

    public void pass(String value) {
        System.out.println(value);
    }

    public void fail(String value) {
        System.err.println(value);
    }

    public WebElement webElement(final By by) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement webElementVisible(final By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebElement clickableElement(final By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public WebElement fluentWait(final By locator) {
        WebElement foo = wait.until(
                new Function<WebDriver, WebElement>() {
                    public WebElement apply(WebDriver driver) {
                        return driver.findElement(locator);
                    }
                }
        );
        return foo;
    }

    ;
}
