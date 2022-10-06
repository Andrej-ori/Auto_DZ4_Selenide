package ru.netology.web;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.*;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SecondTaskPositiveTest {

    public static String generateDate(int days, String formatPattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(formatPattern));
    }


    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");

    }

    @Test
    public void PositiveTest(){
        String myCity = "Магадан";
        $("[data-test-id='city'] input").setValue("Ма");
        $$(".menu-item__control").filter(exactText(myCity)).forEach(SelenideElement::click);
        $(".icon").click();
        if (generateDate(7, "M").equals(generateDate(0, "M"))) {
            ElementsCollection dates = $$(".calendar__day");
            for (SelenideElement element : dates) {
                if (element.getText().equals(generateDate(7, "d"))) {
                    element.click();
                }
            }
        } else {
            $("[data-step='1']").click();
            ElementsCollection dates = $$(".calendar__day");
            for (SelenideElement element : dates) {
                if (element.getText().equals(generateDate(7, "d"))) {
                    element.click();
                }
            }
        }
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991234567");
        $("[data-test-id='agreement']").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();
        $x(".//div[@class='notification__content']").should(text("Встреча успешно забронирована на "
                + generateDate(7, "dd.MM.yyyy")), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}

