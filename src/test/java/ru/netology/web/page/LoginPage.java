package ru.netology.web.page;
import ru.netology.web.data.DataHelper;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        $x("//*[@data-test-id=\"login\"]//self::input").setValue(info.getLogin());
        $x("//*[@data-test-id=\"password\"]//self::input").setValue(info.getPassword());
        $x("//*[@data-test-id=\"action-login\"]").click();
        return new VerificationPage();
    }
}
