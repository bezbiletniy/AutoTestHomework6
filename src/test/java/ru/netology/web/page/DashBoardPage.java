package ru.netology.web.page;
import com.codeborne.selenide.ElementsCollection;
import lombok.val;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;

public class DashBoardPage {
    private ElementsCollection cards = $$x("//*[@class='list__item']//self::div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashBoardPage() {
    }

    public int getFirstCardBalance() {
        val text = cards.first().text();
        return extractBalance(text);
    }
    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

}
