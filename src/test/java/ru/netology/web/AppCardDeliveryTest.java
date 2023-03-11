package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.util.concurrent.TimeUnit.SECONDS;

public class AppCardDeliveryTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));

    }

    private WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        options.addArguments("start-maximized");
        options.addArguments("disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(15, SECONDS);
        driver.manage().timeouts().pageLoadTimeout(15, SECONDS);
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

