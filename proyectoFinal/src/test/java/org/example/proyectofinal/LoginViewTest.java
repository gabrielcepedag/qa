package org.example.proyectofinal;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginViewTest {
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
    public void testLoginSuccessful() {
        // Una vez creada la pestaña de Playwright, se dirige al Login
        page.navigate("http://localhost:8080/login");

        // Se definen los campos de Login
        page.waitForSelector("#username");
        page.waitForSelector("#password");

        // Se ingresa credenciales validas en los campos
        page.fill("#username", "admin");
        page.fill("#password", "admin");

        // Se selecciona boton de login
        page.click("#loginBtn");

        // Se define la espera de redireccion
        page.waitForURL("http://localhost:8080/home");

        // Se prueba que se haya completado el login al validar la URL de redireccion
        assertEquals("http://localhost:8080/home", page.url());

        // Se cierra la pestaña de Playwright
        page.close();
    }

    @Test
    public void testLoginUnsuccessful() {
        // Una vez creada la pestaña de Playwright, se dirige al Login
        page.navigate("http://localhost:8080/login");

        // Se definen los campos de Login
        page.waitForSelector("#username");
        page.waitForSelector("#password");

        // Se ingresan credenciales inválidas en los campos
        page.fill("#username", "admin");
        page.fill("#password", "12345");

        // Se selecciona boton de login
        page.click("#loginBtn");

        // Se identifica la alerta esperada
        Locator alertMessage = page.locator("#swal2-title");
        System.out.println(alertMessage.innerText());

        // Se prueba que la alerta se haya desplegado y contenga el mensaje adecuando
        assertEquals("Wrong Credentials", alertMessage.innerText());

        // Se cierra la alerta
        page.locator(".swal2-confirm").click();
        // Se cierra la pestaña de Playwright
        page.close();
    }

    @Test
    public void testSignUpRedirect() {
        // Una vez creada la pestaña de Playwright, se dirige al Login
        page.navigate("http://localhost:8080/login");

        // Se selecciona boton de login
        page.click("text=Sign up");

        // Se define la espera de redireccion
        page.waitForURL("http://localhost:8080/signup");

        // Se prueba que se haya completado el redireccionamiento al validar la URL
        assertEquals("http://localhost:8080/signup", page.url());

        // Se cierra la pestaña de Playwright
        page.close();
    }
}
