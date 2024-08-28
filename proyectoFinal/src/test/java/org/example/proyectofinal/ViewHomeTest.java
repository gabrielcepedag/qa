package org.example.proyectofinal;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ViewHomeTest {
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
    }

    @AfterEach
    public void tearDown() {
        context.close();
        browser.close();
        playwright.close();
    }

    @Test
    public void testManageRedirectProducts() {
        // Se prueba que se haya completado el login al validar la URL de redireccion
        assertEquals("http://localhost:8080/home", page.url());

        // Se selecciona boton de productos
        page.click("#btn-products");

        // Se define la espera de redireccion
        page.waitForURL("http://localhost:8080/products");

        // Se prueba que se haya completado la redireccion al validar la URL
        assertEquals("http://localhost:8080/products", page.url());

        // Se cierra la pestaña de Playwright
        page.close();
    }

    @Test
    public void testManageRedirectStock() {
        // Se prueba que se haya completado el login al validar la URL de redireccion
        assertEquals("http://localhost:8080/home", page.url());

        // Se selecciona boton de productos
        page.click("#btn-stock");

        // Se define la espera de redireccion
        page.waitForURL("http://localhost:8080/stock");

        // Se prueba que se haya completado la redireccion al validar la URL
        assertEquals("http://localhost:8080/stock", page.url());

        // Se cierra la pestaña de Playwright
        page.close();
    }

    @Test
    public void testManageRedirectUsers() {
        // Se prueba que se haya completado el login al validar la URL de redireccion
        assertEquals("http://localhost:8080/home", page.url());

        // Se selecciona boton de productos
        page.click("#btn-users");

        // Se define la espera de redireccion
        page.waitForURL("http://localhost:8080/users");

        // Se prueba que se haya completado la redireccion al validar la URL
        assertEquals("http://localhost:8080/users", page.url());

        // Se cierra la pestaña de Playwright
        page.close();
    }

    @Test
    public void testLogOutRedirect() {
        // Se prueba que se haya completado el login al validar la URL de redireccion
        assertEquals("http://localhost:8080/home", page.url());

        // Se selecciona boton de Dropdown de Usuario
        page.click("#dropdownUser1");

        // Se selecciona boton de logout
        page.click("#logoutBtn");

        // Se identifica la alerta esperada
        Locator alertMessage = page.locator("#swal2-title");
        // Se prueba que la alerta se haya desplegado y contenga el mensaje adecuando
        assertEquals("Log Out Confirmation", alertMessage.innerText());

        // Se identifica el boton esperado dentro de la alerta
        page.waitForSelector(".swal2-confirm", new Page.WaitForSelectorOptions().setTimeout(5000));
        Locator alertButton = page.locator(".swal2-confirm");
        alertButton.click();

        // Se define la espera de redireccion
        page.waitForURL("http://localhost:8080/");

        // Se prueba que se haya completado el redireccionamiento al validar la URL
        assertEquals("http://localhost:8080/", page.url());

        // Se cierra la pestaña de Playwright
        page.close();
    }

//    @Test
//    public void testSubmitSuccessful() {
//        // Una vez creada la pestaña de Playwright, se dirige a la vista de gestion de usuarios
//        page.navigate("http://localhost:8080/contact");
//
//        // Obtener el número de entradas mostrado textualmente
//        Locator hintTextDiv = page.locator(".text-light span");
//        String formCountBefore = hintTextDiv.innerText();
//
//        // Se definen los campos de Login
//        page.waitForSelector("#name");
//        page.waitForSelector("#email");
//        page.waitForSelector("#message");
//
//        // Se ingresa credenciales validas en los campos
//        page.fill("#name", "Eduardo Martinez");
//        page.fill("#email", "eemr0001@ce.pucmm.edu.do");
//        page.fill("#message", "Hola Mundo!");
//
//        // Se selecciona boton de login
//        page.click("#submitBtn");
//
//        // Se identifica la alerta esperada
//        Locator alertMessage = page.locator("#swal2-title");
//        // Se prueba que la alerta se haya desplegado y contenga el mensaje adecuando
//        assertEquals("Submission Registered!", alertMessage.innerText());
//        // Se cierra la alerta
//        page.locator(".swal2-confirm").click();
//
//        // Se define la espera de redireccion
//        page.waitForURL("http://localhost:8080/contact");
//
//        // Obteniendo nuevo valor de cantidad de registros
//        String formCountAfter = hintTextDiv.innerText();
//
//        // Se prueba que se haya completado el redireccionamiento al validar la URL
//        assertEquals(Integer.parseInt(formCountBefore)+1, Integer.parseInt(formCountAfter));
//
//        // Se cierra la pestaña de Playwright
//        page.close();
//    }

//    @Test
//    public void testSubmitEmptyFields() {
//        // Una vez creada la pestaña de Playwright, se dirige a la vista de gestion de usuarios
//        page.navigate("http://localhost:8080/contact");
//
//        // Se definen los campos de Login
//        page.waitForSelector("#name");
//        page.waitForSelector("#email");
//        page.waitForSelector("#message");
//
//        // Se ingresa credenciales validas en los campos
//        page.fill("#name", "Eduardo Martinez");
//        page.fill("#email", "eemr0001@");
//        page.fill("#message", "Hola Mundo!");
//
//        // Se selecciona boton de login
//        page.click("#submitBtn");
//
//        // Se definen los identificadores de los mensajes de error
//        Locator nameValidFeedback = page.locator("#name ~ .valid-feedback");
//        Locator emailInvalidFeedback = page.locator("#email ~ .invalid-feedback");
//        Locator messageValidFeedback = page.locator("#message ~ .valid-feedback");
//
//        // Se valida que los mensajes de entrada invalida son desplegados
//        assertTrue(nameValidFeedback.isVisible());
//        assertTrue(emailInvalidFeedback.isVisible());
//        assertTrue(messageValidFeedback.isVisible());
//
//
//        // Se valida su contenido the content of the invalid feedback messages
//        assertEquals("Username field is valid!", nameValidFeedback.innerText());
//        assertEquals("Email is blank or invalid!", emailInvalidFeedback.innerText());
//        assertEquals("Message field is valid!", messageValidFeedback.innerText());
//
//        // Se cierra la pestaña de Playwright
//        page.close();
//    }
//
//    @Test
//    public void testSubmitBadEmail() {
//        // Una vez creada la pestaña de Playwright, se dirige a la vista de gestion de usuarios
//        page.navigate("http://localhost:8080/contact");
//
//        // Se selecciona boton de login
//        page.click("#submitBtn");
//
//        // Se definen los identificadores de los mensajes de error
//        Locator nameInvalidFeedback = page.locator("#name ~ .invalid-feedback");
//        Locator emailInvalidFeedback = page.locator("#email ~ .invalid-feedback");
//        Locator messageInvalidFeedback = page.locator("#message ~ .invalid-feedback");
//
//        // Se valida que los mensajes de entrada invalida son desplegados
//        assertTrue(nameInvalidFeedback.isVisible());
//        assertTrue(emailInvalidFeedback.isVisible());
//        assertTrue(messageInvalidFeedback.isVisible());
//
//
//        // Se valida su contenido the content of the invalid feedback messages
//        assertEquals("Username field cannot be blank!", nameInvalidFeedback.innerText());
//        assertEquals("Email is blank or invalid!", emailInvalidFeedback.innerText());
//        assertEquals("Message field cannot be empty!", messageInvalidFeedback.innerText());
//
//        // Se cierra la pestaña de Playwright
//        page.close();
//    }

}
