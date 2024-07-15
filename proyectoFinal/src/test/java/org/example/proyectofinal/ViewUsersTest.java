package org.example.proyectofinal;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewUsersTest {
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
    public void testLogOutRedirect() {
        // Una vez creada la pestaña de Playwright, se dirige al Login
        page.navigate("http://localhost:8080/login");

        // Se definen los campos de Login
        page.waitForSelector("#username");
        page.waitForSelector("#password");

        // Se ingresa credenciales validas en los campos
        page.fill("#username", "playwrightLoginTest1");
        page.fill("#password", "12345");

        // Se selecciona boton de login
        page.click("#loginBtn");

        // Se define la espera de redireccion
        page.waitForURL("http://localhost:8080/home");

        // Se prueba que se haya completado el login al validar la URL de redireccion
        assertEquals("http://localhost:8080/home", page.url());

        // Se selecciona boton de productos
        page.click("#btn-users");

        // Se define la espera de redireccion
        page.waitForURL("http://localhost:8080/users");

        // Se prueba que se haya completado la redireccion al validar la URL
        assertEquals("http://localhost:8080/users", page.url());

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
}
