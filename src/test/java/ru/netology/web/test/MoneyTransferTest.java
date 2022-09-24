package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashBoardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.MoneyTransferPage;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        verificationPage.validVerify(verificationCode);
        Configuration.holdBrowserOpen = true;
    }

    @Test
    public void shouldTransferMoneyHappyPath() {
        var dashboardPage = new DashBoardPage();
        int expectedFirstCardBalance = dashboardPage.getCardBalance("1")+2000;
        int expectedSecondCardBalance = dashboardPage.getCardBalance("2")-2000;
        dashboardPage.getMoneyTransferFromSecondToFirstCard();
        var moneyTransferPage = new MoneyTransferPage();
        moneyTransferPage.moneyTransfer(DataHelper.getCardInfo("2"), "2000");
        int actualFirstCardBalance = dashboardPage.getCardBalance("1");
        int actualSecondCardBalance = dashboardPage.getCardBalance("2");

        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    public void shouldTransferMoneyAnotherHappyPath() {
        var dashboardPage = new DashBoardPage();
        int expectedFirstCardBalance = dashboardPage.getCardBalance("1")-3000;
        int expectedSecondCardBalance = dashboardPage.getCardBalance("2")+3000;
        dashboardPage.getMoneyTransferFromFirstToSecondCard();
        var moneyTransferPage = new MoneyTransferPage();
        moneyTransferPage.moneyTransfer(DataHelper.getCardInfo("1"), "3000");
        int actualFirstCardBalance = dashboardPage.getCardBalance("1");
        int actualSecondCardBalance = dashboardPage.getCardBalance("2");

        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    public void shouldReloadBalance() {
        var dashboardPage = new DashBoardPage();
        int expectedFirstCardBalance = dashboardPage.getCardBalance("1");
        int expectedSecondCardBalance = dashboardPage.getCardBalance("2");
        dashboardPage.reloadBalance();
        int actualFirstCardBalance = dashboardPage.getCardBalance("1");
        int actualSecondCardBalance = dashboardPage.getCardBalance("2");

        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    public void shouldCancelMoneyTransfer() {
        var dashboardPage = new DashBoardPage();
        int expectedFirstCardBalance = dashboardPage.getCardBalance("1");
        int expectedSecondCardBalance = dashboardPage.getCardBalance("2");
        dashboardPage.getMoneyTransferFromSecondToFirstCard();
        dashboardPage.cancelMoneyTransfer();
        int actualFirstCardBalance = dashboardPage.getCardBalance("1");
        int actualSecondCardBalance = dashboardPage.getCardBalance("2");

        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    public void shouldNotTransferMoneyIfCardNotSelected() {
        var dashboardPage = new DashBoardPage();
        dashboardPage.getMoneyTransferFromSecondToFirstCard();
        var moneyTransferPage = new MoneyTransferPage();
        moneyTransferPage.moneyTransfer(DataHelper.getCardInfo(""), "2000");
        moneyTransferPage.getError();
    }

    @Test
    public void shouldNotTransferMoneyAboveExistingValue() {
        var dashboardPage = new DashBoardPage();
        String aboveBalance = String.valueOf(dashboardPage.getCardBalance("1") + 1000);
        int expectedFirstCardBalance = dashboardPage.getCardBalance("1");
        int expectedSecondCardBalance = dashboardPage.getCardBalance("2");
        dashboardPage.getMoneyTransferFromFirstToSecondCard();
        var moneyTransferPage = new MoneyTransferPage();
        moneyTransferPage.moneyTransfer(DataHelper.getCardInfo("1"), aboveBalance);
        moneyTransferPage.getError();
    }
}
