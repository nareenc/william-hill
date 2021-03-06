/*
 * This source file is proprietary property of Encompass Corporation.
 */
package functions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.log4testng.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

import static  functions.BrowserConstants.*;
import static org.openqa.selenium.remote.DesiredCapabilities.*;

public class WebDriverUtils {

    private static final Logger LOGGER = Logger.getLogger(WebDriverUtils.class);

    private static WebDriverUtils webDriverUtils;

    public static WebDriverUtils getInstance() {
        if (webDriverUtils == null) {
            webDriverUtils = new WebDriverUtils();
        }
        return webDriverUtils;
    }

    private ConcurrentHashMap<String, WebDriver> webDriverCache;

    private WebDriverUtils() {
        webDriverCache = new ConcurrentHashMap<String, WebDriver>();
    }

    public void removeDriver(String type) {
        LOGGER.debug("Removing cached WebDriver of type [" + type + "]");
        webDriverCache.remove(type);
    }

    public WebDriver createWebDriver(String browserName, String remoteWebDriverUrl) throws MalformedURLException {
        LOGGER.debug("Creating [" + browserName + "] WebDriver");
        WebDriver webDriver;
        if (remoteWebDriverUrl == null) {
            webDriver = null;
            if (browserName.contains("Firefox")) {
                webDriver = new FirefoxDriver();
            }
            if (browserName.contains("Chrome")) {
                System.setProperty("webdriver.chrome.driver", "C:\\Softwares\\chromedriver.exe");
                webDriver = new ChromeDriver();
            }
        } else {
            if (webDriverCache.containsKey(browserName)) {
                webDriver = webDriverCache.get(browserName);
            } else {
                if (BROWSER_FIREFOX.contains(browserName) || BROWSER_HTML_UNIT.contains(browserName) || BROWSER_IE.contains
                        (browserName) || BROWSER_CHROME.contains(browserName)) {
                    webDriver = getWebDriver(remoteWebDriverUrl, browserName);
                    browserName = BROWSER_REMOTE.contains(browserName) ? BROWSER_REMOTE + " - " + remoteBrowserName(
                            (RemoteWebDriver) webDriver) : remoteBrowserName((RemoteWebDriver) webDriver);
                } else if (BROWSER_SAUCELABS.contains(browserName)) {
                    DesiredCapabilities caps = internetExplorer();
                    caps.setCapability("platform", "Windows 7");
                    caps.setCapability("version", "11");
                    webDriver = new RemoteWebDriver(new URL(remoteWebDriverUrl), caps);
                    browserName = BROWSER_REMOTE + " - " + remoteBrowserName((RemoteWebDriver) webDriver);
                } else {
                    throw new IllegalArgumentException("Unable to determine the driver for [" + browserName + "]");
                }
                LOGGER.debug("Caching [" + browserName + "] WebDriver");
                webDriverCache.put(browserName, webDriver);
            }
        }
        return webDriver;
    }


    protected WebDriver getWebDriver(String remoteWebDriverUrl, String browserName) throws MalformedURLException {
        return BROWSER_FIREFOX.contains(browserName)
                ? new RemoteWebDriver(new URL(remoteWebDriverUrl), firefox())
                : BROWSER_IE.contains(browserName)
                ? new RemoteWebDriver(new URL(remoteWebDriverUrl), internetExplorer())
                : BROWSER_CHROME.contains(browserName)
                ? new RemoteWebDriver(new URL(remoteWebDriverUrl), chrome())
                : BROWSER_REMOTE.contains(browserName)
                ? new RemoteWebDriver(new URL(remoteWebDriverUrl), chrome())
                : new RemoteWebDriver(new URL(remoteWebDriverUrl), htmlUnit());
    }

    protected String remoteBrowserName(RemoteWebDriver webDriver) {
        return webDriver.getCapabilities().getBrowserName();
    }
}
