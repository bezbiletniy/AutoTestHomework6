package ru.netology.web.page;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class VerificationPage {

    private SelenideElement verifyCode = $x("[data-test-id='code']//self::input");
    private SelenideElement verifyButton = $x("[data-test-id='action-verify']");

    public VerificationPage() {
        verifyCode.shouldBe(visible);
    }

    public DashBoardPage validVerify (DataHelper.VerificationCode verificationCode) {
        verifyCode.setValue(verificationCode.getCode());
        verifyButton.click();
        return new DashBoardPage();
    }
}
