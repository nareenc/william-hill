/*
 * This source file is proprietary property of Encompass Corporation.
 */
package functions;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static functions.BrowserConstants.BROWSER_IE;
import static org.openqa.selenium.By.*;
import static org.testng.Assert.*;

@SuppressWarnings("unused")
public class CommonFunctions extends WebFunctions {

    private boolean hub;
    private boolean isStartSearchOrisIE;
    private boolean previousSearch;
    private String client_user_id;
    public int resultcount;
    public String ie_version = getData().commonData("ie_version");
    private String testresults;
    private String retrieve_selected_certificates;
    private Boolean share_workspace;
    private String end_point;

    public CommonFunctions() {
       
    }

    @Parameters(value = "IPAddress")
    @BeforeClass()
    protected void setup(@Optional String remoteWebDriverUrl) throws MalformedURLException {
        super.start(remoteWebDriverUrl);
    }

    @AfterClass
    public void teardown() throws Exception {
        if (getRecord()) {
            stopRecording();
        }
        super.stop();
    }


    public void clickAt(By by) throws InterruptedException, IOException {
        Thread.sleep(1000);
        if (getBrowser().contains("Firefox")) {
            click(by);
        } else {
            Actions builder = new Actions(getWebDriver());
            try {
                WebElement tagElement = getWebDriver().findElement(by);
                builder.moveToElement(tagElement).click().perform();
                Thread.sleep(1000);
            } catch (Exception e) {
                takeScreenshot();
                fail("Can not find " + by);
            }
        }
    }


    public void clickAt(WebElement tagElement) throws InterruptedException, IOException {
        Thread.sleep(1000);
        Actions builder = new Actions(getWebDriver());
        try {
            builder.moveToElement(tagElement).click().perform();
            Thread.sleep(1000);
        } catch (Exception e) {
            takeScreenshot();
            fail("Can not find " + tagElement);
        }
    }


    public void doubleClick(By by) throws InterruptedException, IOException {
        Thread.sleep(1000);
        Actions builder = new Actions(getWebDriver());
        try {
            WebElement tagElement = getWebDriver().findElement(by);
            builder.doubleClick(tagElement).perform();
            Thread.sleep(1000);
        } catch (Exception e) {
//            takeScreenshot();
        }
    }

   

    public void type(By by, String value) throws InterruptedException {
        try {
            getWebDriver().findElement(by).clear();
            getWebDriver().findElement(by).sendKeys(value);
        } catch (Exception e) {
            getWebDriver().findElement(by).clear();
            getWebDriver().findElement(by).sendKeys(value);
        }
    }

    public void type(WebElement element, String value) throws InterruptedException {
        element.clear();
        element.sendKeys(value);
    }

    public void typeAll(String[] path, String[] value) throws InterruptedException {
        int len = path.length;
        for (int i = 0; i <= (len - 1); i++) {
            if (isElementPresent(xpath(path[i]))) {
                type(xpath(path[i]), value[i]);
            }
        }
    }

    public void actionType(By by, String value) throws InterruptedException {
        waitForElementPresent((by));
        new Actions(getWebDriver()).moveToElement(getWebDriver().findElement(by)).click()
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), value).perform();
    }   
}
