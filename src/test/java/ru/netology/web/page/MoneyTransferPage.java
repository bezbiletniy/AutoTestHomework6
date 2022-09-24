package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;
import static com.codeborne.selenide.Selenide.$x;

public class MoneyTransferPage {
    private SelenideElement transferAmount = $x("//*[@data-test-id=\"amount\"]//self::input");
    private SelenideElement fromField = $x("//*[@data-test-id=\"from\"]//self::input");
    private SelenideElement transferButton = $x("//*[@data-test-id=\"action-transfer\"]");
    private SelenideElement errorMessage = $x("//*[@data-test-id=\"error-notification\"]");

    public DashBoardPage moneyTransfer(DataHelper.CardInfo from, String amountToTransfer) {
        transferAmount.setValue(amountToTransfer);
        fromField.setValue(from.getNumber());
        transferButton.click();
        return new DashBoardPage();
    }

    public void getError() {
        errorMessage.shouldBe(Condition.visible);
    }



}
