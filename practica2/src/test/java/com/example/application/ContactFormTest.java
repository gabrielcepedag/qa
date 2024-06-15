package com.example.application;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ContactFormTest extends PlaywrightIT{
    @LocalServerPort
    private int port;
    private Locator name;
    private Locator email;
    private Locator message;
    private Locator button;

    @BeforeEach
    void setUp() {
        String baseUrl = "http://localhost:" + port;
        page.navigate(baseUrl + "/contact-form");

        name = page.locator("input[id^='input-vaadin-text-field']");
        email = page.locator("input[id^='input-vaadin-email-field']");
        message = page.locator("textarea[id^='textarea-vaadin-text-area']");
        button = page.locator("vaadin-button:has-text('Enviar')");
    }

    @Test
    public void series_are_displayed() {
        assertThat(page).hasTitle("Contact Form");
        Locator seriesLayouts = page.locator("vaadin-vertical-layout");
        assertThat(seriesLayouts).hasCount(2);
    }

    @Test
    public void inputs_are_visible(){
        assertThat(name).isVisible();
        assertThat(email).isVisible();
        assertThat(button).isVisible();
        assertThat(message).isVisible();
    }

    @Test
    public void form_is_empty(){
        button.click();

        assertThat(page.locator("div[slot='error-message']:has-text('Por favor, ingrese su nombre')")).isVisible();
        assertThat(page.locator("div[slot='error-message']:has-text('Por favor, ingrese un correo electrónico válido')")).isVisible();
        assertThat(page.locator("div[slot='error-message']:has-text('Por favor, ingrese un mensaje')")).isVisible();
    }

    @Test
    public void invalid_email(){
        name.fill("El mejor");
        message.fill("Freddy la para");
        email.fill("gabrielcepeda");
        button.click();

        assertThat(page.locator("vaadin-notification-card:has-text('Por favor, complete todos los campos correctamente')")).isVisible();
    }

    @Test
    public void limit_characters_message(){
        name.fill("Gabriel la para");
        email.fill("hola@mail.com");
        message.fill("a".repeat(60));

        Assertions.assertEquals(message.inputValue().length(), 50);
    }

    @Test
    public void valid_data_form(){
        name.fill("Gabriel Cepeda");
        email.fill("hola@mail.com");
        message.fill("Hola, me llamo Gabriel");

        button.click();
        assertThat(page.locator("vaadin-notification-card:has-text('Formulario enviado con éxito')")).isVisible();
    }
}


