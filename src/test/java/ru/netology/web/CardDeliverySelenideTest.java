package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliverySelenideTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }
    DateService service = new DateService();

    @Test
    void shouldTestPositiveScenarioForDeliveryForm() {

        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String meetingDate = service.date(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Иван Иванов-Петров");
        $("[data-test-id='phone'] input").setValue("+79270000000");
        $("[data-test-id='agreement']").click();
        $("[class='button__content']").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Встреча успешно забронирована на " + meetingDate));

    }

    @Test
    void shouldTestAdvancePositiveScenarioForDeliveryForm() {

        $("[data-test-id='city'] input").setValue("Са");
        $$("[class='menu-item__control']").findBy(text("Санкт-Петербург")).click();
        LocalDate meetingDate = LocalDate.now().plusDays(14);
        $("[class='input__icon']").click();
        if (LocalDate.now().getMonthValue() != meetingDate.getMonthValue()) {
            $("[data-step='1'].calendar__arrow_direction_right").click();
        }
        $$("[data-day]").findBy(Condition.text(String.valueOf(meetingDate.getDayOfMonth()))).click();
        $("[data-test-id='name'] input").setValue("Иван Иванов-Петров");
        $("[data-test-id='phone'] input").setValue("+79270000000");
        $("[data-test-id='agreement']").click();
        $("[class='button__content']").click();
        $(".notification__content")
                .shouldHave(exactText("Встреча успешно забронирована на " + meetingDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))), Duration.ofSeconds(15));

    }
}
