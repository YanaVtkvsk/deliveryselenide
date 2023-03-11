package ru.netology.web;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class AppCardDeliveryTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));

    }

    @BeforeEach
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        Configuration.headless = true;
        open("http://localhost:9999");
    }



    @Test
    void shouldBeSuccessful() {
        $("[data-test-id= 'city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id= 'date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id= 'date'] input").sendKeys(currentDate);
        $("[data-test-id= 'name'] input").setValue("Иванов Иванович Иван");
        $("[data-test-id= 'phone'] input").setValue("+79001000000");
        $("[data-test-id= 'agreement'] ").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + currentDate));
    }
}

