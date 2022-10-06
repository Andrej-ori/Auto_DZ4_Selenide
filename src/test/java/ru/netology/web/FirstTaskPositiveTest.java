package ru.netology.web;

import com.codeborne.selenide.Condition.*;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;

public class FirstTaskPositiveTest {

    SelenideElement notification = $x("//div[@data-test-id='notification']");

    LocalDate today = LocalDate.now();
    LocalDate minData = today.plusDays(3);
    LocalDate newDate = today.plusDays(5);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");



    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");

    }

// шаги теста:
// 1.открыть страницу
// 2.найти поле город
//  2.1 кликнуть на поле город
//  2.2. в поле город ввести "Магадан"
// 3.найти поле дата
//  3.1 кликнуть на поле дата
//  3.2 выделить все в поле дата
//  3.3.удалить все из поле дата
//  3.4 ввести дату(не ранее 3х дней с текущей даты(дата должна быть динамической(надо придумать логику с переходом на следующий месяц(она должна работать и с високосным годом)))
// 4.найти поле Фамилия и имя
//  4.1 кликнуть на поле Фамилия и имя
//  4.2 ввести Фамилию и имя
// 5 найти поле телефон
//  5.1 кликнуть на поле телефон
//  5.2 ввести номер телефона(начинается с + и 11 цифр)
// 6 найти чекбокс с соглашением об обработке данных
//  6.1 кликнуть на чекбокс поставив галку
// 7 найти кнопку "Забронировать"
//  7.1 кликнуть на кнопку "забронировать"
// 8 подождать (не более 15 секунд)
// 9 должно появиться окно сообщающее об успехе
// 10 проверить что дата брони соответствует заполненой дате

    @Test
    public void positiveTest(){

        $("[data-test-id='city'] input").setValue("Магадан");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(newDate));
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991234567");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();
        notification.should(visible, ofSeconds(15));
        notification.$x(".//div[@class='notification__title']").should(text("Успешно"));
        notification.$x(".//div[@class='notification__content']").should(text("Встреча успешно забронирована на "
                + newDate.format(formatter)));

    }

    @Test
    public void positiveTestDefaultDate(){

        $("[data-test-id='city'] input").setValue("Магадан");

        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991234567");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();
        notification.should(visible, ofSeconds(15));
        notification.$x(".//div[@class='notification__title']").should(text("Успешно"));
        notification.$x(".//div[@class='notification__content']").should(text("Встреча успешно забронирована на "
                + minData.format(formatter)));

    }
}
