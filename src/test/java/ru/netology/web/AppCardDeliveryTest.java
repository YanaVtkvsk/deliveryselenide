package ru.netology.web;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AppCardDeliveryTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldBeSuccessful() {
        open("http://localhost:9999");
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
