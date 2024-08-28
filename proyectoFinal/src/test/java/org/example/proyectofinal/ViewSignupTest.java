package org.example.proyectofinal;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewSignupTest {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @BeforeEach
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
        context = browser.newContext();
        page = browser.newPage();

    }

    @AfterEach
    public void tearDown() {
        context.close();
        browser.close();
        playwright.close();
    }

    @Test
    public void testSignupSuccessful() {
        // Una vez creada la pestaña de Playwright, se dirige al Signup
        page.navigate("http://localhost:8080/signup");

        // Se definen los campos de Signup
        page.waitForSelector("#username");
        page.waitForSelector("#name");
        page.waitForSelector("#password");
        page.waitForSelector("#passwordConfirm");

        // Se ingresan credenciales válidas en los campos
        page.fill("#username", "playwrightLoginTest1");
        page.fill("#name", "Playwright Spring");
        page.fill("#password", "12345");
        page.fill("#passwordConfirm", "12345");

        // Se selecciona boton de login
        page.click("#signupBtn");

        // Se define la espera de redireccion
        page.waitForURL("http://localhost:8080/home");

        // Se prueba que se haya completado el login al validar la URL de redireccion
        assertEquals("http://localhost:8080/home", page.url());

        // Se cierra la pestaña de Playwright
        page.close();
    }

    @Test
    public void testSignupPasswordMismatch() {
        // Una vez creada la pestaña de Playwright, se dirige al Signup
        page.navigate("http://localhost:8080/signup");

        // Se definen los campos de Signup
        page.waitForSelector("#username");
        page.waitForSelector("#name");
        page.waitForSelector("#password");
        page.waitForSelector("#passwordConfirm");

        // Se ingresan credenciales inválidas en los campos
        page.fill("#username", "playwrightLoginTest2");
        page.fill("#name", "Playwright Spring");
        page.fill("#password", "12345");
        page.fill("#passwordConfirm", "98765");

        // Se selecciona boton de signup
        page.click("#signupBtn");

        // Se identifica la alerta esperada
        Locator alertMessage = page.locator("#swal2-title");

        // Se prueba que la alerta se haya desplegado y contenga el mensaje adecuando
        assertEquals("Password Mismatch", alertMessage.innerText());

        // Se cierra la alerta
        page.locator(".swal2-confirm").click();
        // Se cierra la pestaña de Playwright
        page.close();
    }

    @Test
    public void testSignupEmptyFields() {
        // Una vez creada la pestaña de Playwright, se dirige al Signup
        page.navigate("http://localhost:8080/signup");

        // Se selecciona boton de login
        page.click("#signupBtn");

        // Se identifica la alerta esperada
        Locator alertMessage = page.locator("#swal2-title");

        // Se prueba que la alerta se haya desplegado y contenga el mensaje adecuando
        assertEquals("Validation Error", alertMessage.innerText());

        // Se cierra la alerta
        page.locator(".swal2-confirm").click();
        // Se cierra la pestaña de Playwright
        page.close();
    }

    @Test
    public void testLoginRedirect() {
        // Una vez creada la pestaña de Playwright, se dirige al Login
        page.navigate("http://localhost:8080/signup");

        // Se selecciona boton de login
        page.click("text=Log in");

        // Se define la espera de redireccion
        page.waitForURL("http://localhost:8080/login");

        // Se prueba que se haya completado el redireccionamiento al validar la URL
        assertEquals("http://localhost:8080/login", page.url());

        // Se cierra la pestaña de Playwright
        page.close();
    }
}