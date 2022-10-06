package ru.netology.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;

public class FirstTasNegativeTest {

    LocalDate today = LocalDate.now();
    LocalDate newDate = today.plusDays(5);
    LocalDate invalidDate = today.plusDays(-5);
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
//  2.2. в поле город ввести "Баку"
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
// 9 под полем город должно появиться сообщение об ошибке
// 10 проверить что сообщение об ошибке  соответствует "Доставка в выбранный город недоступна"

    @Test
    public void negativeTestСityFalse(){

        $("[data-test-id='city'] input").setValue("Баку");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(newDate));
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991234567");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

       $x(".//span[@data-test-id='city']").should(cssClass("input_invalid"));
       $x(".//span[@data-test-id='city']//child::span[@class='input__sub']").should(visible, text("Доставка в выбранный город недоступна"));

    }

    @Test
    public void negativeTestСityTypo(){

        $("[data-test-id='city'] input").setValue("Магодан");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(newDate));
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991234567");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

        $x(".//span[@data-test-id='city']").should(cssClass("input_invalid"));
        $x(".//span[@data-test-id='city']//child::span[@class='input__sub']").should(visible, text("Доставка в выбранный город недоступна"));

    }

    @Test
    public void negativeTestNoCity() {

        $("[data-test-id='city'] input").setValue("");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(newDate));
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991234567");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

//        $x(".//span[@data-test-id='city']").should(cssClass("input_invalid"));
        $x(".//span[@data-test-id='city']//child::span[@class='input__sub']").should(visible, text("Поле обязательно для заполнения"));
    }

    @Test
    public void negativeTestInvalidDate() {

        $("[data-test-id='city'] input").setValue("Магадан");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(invalidDate));
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991234567");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

        $x(".//span[@data-test-id='date']//child::span[@class='input__sub']").should(visible, text("Заказ на выбранную дату невозможен"));
    }

    @Test
    // ввод несуществующей даты (например 32 число)
    public void negativeTestInvalidDate2() {

        $("[data-test-id='city'] input").setValue("Магадан");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys("32.01.2023");
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991234567");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

        $x(".//span[@data-test-id='date']//child::span[@class='input__sub']").should(visible, text("Неверно введена дата"));
    }

    @Test
    public void negativeTestTodayData() {

        $("[data-test-id='city'] input").setValue("Магадан");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(today));
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991234567");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

        $x(".//span[@data-test-id='date']//child::span[@class='input__sub']").should(visible, text("Заказ на выбранную дату невозможен"));
    }

    @Test
    public void negativeTestNoDate() {

        $("[data-test-id='city'] input").setValue("Магадан");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);

        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991234567");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

        $x(".//span[@data-test-id='date']//child::span[@class='input__sub']").should(visible, text("Неверно введена дата"));
    }

    @Test
    public void negativeTestInvalidName() {

        $("[data-test-id='city'] input").setValue("Магадан");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(newDate));
        $("[data-test-id='name'] input").setValue("Ivanov Ivan");
        $("[data-test-id='phone'] input").setValue("+79991234567");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

        $x(".//span[@data-test-id='name']//child::span[@class='input__sub']").should(visible, text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void negativeTestInvalidNameЁ() {

        $("[data-test-id='city'] input").setValue("Магадан");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(newDate));
        $("[data-test-id='name'] input").setValue("Иванова Алёна");
        $("[data-test-id='phone'] input").setValue("+79991234567");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

        $x(".//span[@data-test-id='name']//child::span[@class='input__sub']").should(visible, text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void negativeTestInvalidTel() {

        $("[data-test-id='city'] input").setValue("Магадан");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(newDate));
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+7999");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

        $x(".//span[@data-test-id='phone']//child::span[@class='input__sub']").should(visible, text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void negativeTestInvalidTelNotPlus() {

        $("[data-test-id='city'] input").setValue("Магадан");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(newDate));
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("89991234567");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

        $x(".//span[@data-test-id='phone']//child::span[@class='input__sub']").should(visible, text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void negativeTestEmptyCheckbox() {

        $("[data-test-id='city'] input").setValue("Магадан");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(newDate));
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991234567");

        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();

        $x(".//label[@data-test-id='agreement']").should(cssClass("input_invalid"));
    }

}
