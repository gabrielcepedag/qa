package org.example.proyectofinal;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ViewStockTest {
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
    public void testLogOutRedirect() {
        // Se prueba que se haya completado el login al validar la URL de redireccion
        assertEquals("http://localhost:8080/home", page.url());

        // Se selecciona boton de productos
        page.click("#btn-stock");

        // Se define la espera de redireccion
        page.waitForURL("http://localhost:8080/stock");

        // Se prueba que se haya completado la redireccion al validar la URL
        assertEquals("http://localhost:8080/stock", page.url());

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

    @Test
    public void testAddStockSuccessful(){
        // Se prueba que se haya completado el login al validar la URL de redireccion
        assertEquals("http://localhost:8080/home", page.url());

        // Se selecciona boton de productos
        page.click("#btn-stock");

        // Se define la espera de redireccion
        page.waitForURL("http://localhost:8080/stock");

        // Se prueba que se haya completado la redireccion al validar la URL
        assertEquals("http://localhost:8080/stock", page.url());

        // Selecciona el boton de Editar en el primer producto de la tabla
        page.click("#tableProductStock tbody tr:first-child #btnStockPlus");

        // Se identifica el modal para agregar el producto
        page.waitForSelector("#manageStockModal");

        // Completamos los campos con un producto valido
        page.fill("#stockChange", String.valueOf(20));

        // Se selecciona el botón de confirmar registro
        page.click("#btnSubmit");

        // Se identifica la alerta de registro exitoso
        page.waitForSelector(".swal2-popup .swal2-title");
        Locator successAlert = page.locator(".swal2-popup .swal2-title");

        // Se valida el mensaje de registro exitoso
        assertEquals("Stock Modification Completed!", successAlert.innerText());

        // Se cierra la alerta
        page.click(".swal2-confirm");

        page.close();
    }

    @Test
    public void testRemoveStockSuccessful(){
        // Se prueba que se haya completado el login al validar la URL de redireccion
        assertEquals("http://localhost:8080/home", page.url());

        // Se selecciona boton de productos
        page.click("#btn-stock");

        // Se define la espera de redireccion
        page.waitForURL("http://localhost:8080/stock");

        // Se prueba que se haya completado la redireccion al validar la URL
        assertEquals("http://localhost:8080/stock", page.url());

        // Selecciona el boton de Editar en el primer producto de la tabla
        page.click("#tableProductStock tbody tr:first-child #btnStockMinus");

        // Se identifica el modal para agregar el producto
        page.waitForSelector("#manageStockModal");

        // Completamos los campos con un producto valido
        page.fill("#stockChange", String.valueOf(15));

        // Se selecciona el botón de confirmar registro
        page.click("#btnSubmit");

        // Se identifica la alerta de registro exitoso
        page.waitForSelector(".swal2-popup .swal2-title");
        Locator successAlert = page.locator(".swal2-popup .swal2-title");

        // Se valida el mensaje de registro exitoso
        assertEquals("Stock Modification Completed!", successAlert.innerText());

        // Se cierra la alerta
        page.click(".swal2-confirm");
        page.close();
    }

    @Test
    public void testAddStockEmptyFields(){
        // Se prueba que se haya completado el login al validar la URL de redireccion
        assertEquals("http://localhost:8080/home", page.url());

        // Se selecciona boton de productos
        page.click("#btn-stock");

        // Se define la espera de redireccion
        page.waitForURL("http://localhost:8080/stock");

        // Se prueba que se haya completado la redireccion al validar la URL
        assertEquals("http://localhost:8080/stock", page.url());

        page.click("#tableProductStock tbody tr:first-child #btnStockPlus");

        // Se identifica el modal para agregar el producto
        page.waitForSelector("#manageStockModal");

        // Se selecciona el botón de confirmar registro
        page.click("#btnSubmit");

        // Se definen los identificadores de los mensajes de error
        Locator stockInvalidFeedback = page.locator("#stockChange ~ .invalid-feedback");

        // Se valida que los mensajes de entrada invalida son desplegados
        assertTrue(stockInvalidFeedback.isVisible());

        // Se valida el contenido de los mensajes invalidos
        assertEquals("This amount is invalid!", stockInvalidFeedback.innerText());

        // Se selecciona el botón de cancelar
        page.click("#btnCancel");

        page.close();
    }

    @Test
    public void testRemoveStockEmptyFields(){
        // Se prueba que se haya completado el login al validar la URL de redireccion
        assertEquals("http://localhost:8080/home", page.url());

        // Se selecciona boton de productos
        page.click("#btn-stock");

        // Se define la espera de redireccion
        page.waitForURL("http://localhost:8080/stock");

        // Se prueba que se haya completado la redireccion al validar la URL
        assertEquals("http://localhost:8080/stock", page.url());

        page.click("#tableProductStock tbody tr:first-child #btnStockMinus");

        // Se identifica el modal para agregar el producto
        page.waitForSelector("#manageStockModal");

        // Se selecciona el botón de confirmar registro
        page.click("#btnSubmit");

        // Se definen los identificadores de los mensajes de error
        Locator stockInvalidFeedback = page.locator("#stockChange ~ .invalid-feedback");

        // Se valida que los mensajes de entrada invalida son desplegados
        assertTrue(stockInvalidFeedback.isVisible());

        // Se valida el contenido de los mensajes invalidos
        assertEquals("This amount is invalid!", stockInvalidFeedback.innerText());

        // Se selecciona el botón de cancelar
        page.click("#btnCancel");

        page.close();
    }
}
