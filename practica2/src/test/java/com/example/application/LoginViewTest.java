package com.example.application;

import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class LoginViewTest extends PlaywrightIT{
    @LocalServerPort
    private int port;
    private Locator username;
    private Locator password;
    private Locator sendButton;
    private Locator showPasswordButton;

    @BeforeEach
    void setUp() {
        String baseUrl = "http://localhost:" + port;
        page.navigate(baseUrl + "/");

        username = page.locator("input[id^='input-vaadin-text-field']");
        password = page.locator("input[id^='input-vaadin-password-field']");
        sendButton = page.locator("vaadin-button:has-text('Log in')");
        showPasswordButton = page.locator("vaadin-password-field-button");
    }

    @Test
    public void series_are_displayed() {
        assertThat(page).hasTitle("Login");
        Locator seriesLayouts = page.locator("vaadin-vertical-layout");
        assertThat(seriesLayouts).hasCount(1);
    }

    @Test
    public void inputs_are_visible(){
        assertThat(username).isVisible();
        assertThat(password).isVisible();
        assertThat(sendButton).isVisible();
        assertThat(showPasswordButton).isVisible();
    }

    @Test
    public void username_is_required(){
        password.click();
        assertThat(page.locator("div[slot='error-message']:has-text('Username is required')")).isVisible();
    }

    @Test
    public void password_is_required(){
        username.fill("gabriel");
        sendButton.click();
        assertThat(page.locator("div[slot='error-message']:has-text('Password is required')")).isVisible();
    }

    @Test
    public void show_password_button(){
        password.fill("gabriel");
        assertThat(password).hasAttribute("type", "password");
        showPasswordButton.click();
        assertThat(password).hasAttribute("type", "text");
        showPasswordButton.click();
        assertThat(password).hasAttribute("type", "password");
    }

    @Test
    public void invalid_username(){
        username.fill("gabriel");
        password.fill("admin");
        sendButton.click();
        assertThat(page.locator("vaadin-notification-card:has-text('Usuario o contraseña incorrectos')")).isVisible();
    }

    @Test
    public void invalid_password(){
        username.fill("admin");
        password.fill("gabriel");
        sendButton.click();
        assertThat(page.locator("vaadin-notification-card:has-text('Usuario o contraseña incorrectos')")).isVisible();
    }

    @Test
    public void invalid_credentials(){
        username.fill("gabriel");
        password.fill("loquesea");
        sendButton.click();
        assertThat(page.locator("vaadin-notification-card:has-text('Usuario o contraseña incorrectos')")).isVisible();
    }

    @Test
    public void valid_credentials(){
        username.fill("admin");
        password.fill("admin");
        sendButton.click();
        assertThat(page.locator("vaadin-notification-card:has-text('Inicio de sesión exitoso')")).isVisible();
    }

}
