package tests;

import functions.CommonFunctions;
import functions.ReadXMLData;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RacingBetPage;

import static org.openqa.selenium.By.xpath;

public class HorseRaceBet extends CommonFunctions {
    ReadXMLData data = new ReadXMLData();

    @Test
    public void HorseRaceBettest() throws InterruptedException {
        RacingBetPage racingBet = new RacingBetPage(this);
        racingBet.clickMenuItem(data.getHorseRaceData("menuItem"));
        try {
            //Assert for the page title after clicking the Horse Racing menu item
            Assert.assertTrue(getWebDriver().getTitle().contains(data.getHorseRaceData("menuItem")));
            pass(data.getHorseRaceData("menuItem") + " is displayed in the page title");
        } catch (AssertionError e) {
            fail(data.getHorseRaceData("menuItem") + " is not displayed in the page title");
        }
        racingBet.clickRaceItem(data.getHorseRaceData("raceType"), data.getHorseRaceData("raceId"));
        WebElement racePage = webElement(xpath(racingBet.racePage(data.getHorseRaceData("raceId"))));
        try {
            //Assert whether the race page is displayed or not
            Assert.assertTrue(racePage.isDisplayed());
            pass(data.getHorseRaceData("raceId") + " section is displayed");
        } catch (AssertionError e) {
            fail(data.getHorseRaceData("raceId") + "section is not displayed");
        }
        try {
            //Assert for the raceId on the page title
            Assert.assertTrue(getWebDriver().getTitle().contains(data.getHorseRaceData("raceId")));
            pass(data.getHorseRaceData("raceId") + " is displayed in the page title");
        } catch (AssertionError e) {
            fail(data.getHorseRaceData("raceId") + " is not displayed in the page title");
        }
        WebElement competitorName = clickableElement(xpath(racingBet.competitorFixedWin(data.getHorseRaceData("competitorName"))));
        String betValue = competitorName.getText();
        competitorName.click();
        WebElement betSlip = clickableElement(xpath("//a[@id='betslipBadge' and @data-count='1']"));
        try {
            //Assert whether the Bet Slip page is displayed or not
            Assert.assertTrue(betSlip.isDisplayed());
            pass("The bet is added to the Bet Slip");
        } catch (AssertionError e) {
            fail("The bet is not added to the Bet Slip");
        }
        betSlip.click();
        WebElement betSlipCompetitorName = webElement(xpath(racingBet.betSlipCompetitorName(data.getHorseRaceData("competitorName"))));
        try {
            //Assert whether the competitor name is displayed in the Bet Slip or not
            Assert.assertTrue(betSlipCompetitorName.isDisplayed());
            pass(data.getHorseRaceData("competitorName") + " is displayed in the Bet Slip");
        } catch (AssertionError e) {
            fail(data.getHorseRaceData("competitorName") + " is not displayed in the Bet Slip");
        }

        WebElement betSlipBetPrice = webElement(xpath(racingBet.betSlipBetPrice(betValue)));
        try {
            //Assert whether the bet value is displayed on the Bet Slip page or not
            Assert.assertTrue(betSlipBetPrice.isDisplayed());
            pass(betValue + " is displayed in the Bet Slip");
        } catch (AssertionError e) {
            fail(betValue + " is not displayed in the Bet Slip");
        }
    }
}