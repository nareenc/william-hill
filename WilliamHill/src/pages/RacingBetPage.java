package pages;

import functions.CommonFunctions;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.By.xpath;

public class RacingBetPage extends CommonFunctions {

    public RacingBetPage(CommonFunctions testClass) {
        setDriver(testClass.getDriver());
        setWait(testClass.getWait());
    }

    public String desktopMenuItem(String bettingType) {
        return "//li[@class='racing-link-desktop']/a[text()[contains(.,'" + bettingType + "')]]";
    }

    public String raceId(String raceType, String raceId) {
        return "//a[contains(@href, '/" + raceType + "/" + raceId + "')]";
    }

    public String racePage(String raceId) {
        return "//section[@data-event-id='" + raceId + "']";
    }

    public String competitorFixedWin(String competitorName) {
        return "//strong[@class='competitorName'][text()[contains(.,'" + competitorName + "')]]/ancestor-or-self::div[@data-competitor-id]//li[contains(@class,'fixed-win')]/a";
    }

    public void clickMenuItem(String raceType) throws InterruptedException {
        WebElement menuItem = clickableElement(xpath(desktopMenuItem(raceType)));
        menuItem.click();
    }

    public void clickRaceItem(String raceType, String raceId) throws InterruptedException {
        WebElement raceIdElement = clickableElement(xpath(raceId(raceType, raceId)));
        raceIdElement.click();
    }

    public String betSlipCompetitorName(String competitorName) {
        return "//div[@id='single-bets']//small/b[text()[contains(.,'" + competitorName + "')]]";
    }

    public String betSlipBetPrice(String value) {
        return "//div[@class='bet single']//b[text()[contains(.,'" + value + "')]]";
    }

}
