package org.example.proyectofinal;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void testAddUserSuccessful(){
        // Se prueba que se haya completado el login al validar la URL de redireccion
        assertEquals("http://localhost:8080/home", page.url());

        // Se selecciona boton de productos
        page.click("#btn-users");

        // Se define la espera de redireccion
        page.waitForURL("http://localhost:8080/users");

        // Se prueba que se haya completado la redireccion al validar la URL
        assertEquals("http://localhost:8080/users", page.url());

        // Se selecciona el botón de logout
        page.click("#btn-add-user");

        // Se identifica el modal para agregar el producto
        page.waitForSelector("#addUserModal");

        // Completamos los campos con un producto valido
        page.fill("#username", "PlayWrightUser");
        page.fill("#name", "Playwright User");
        page.selectOption("#role", "USER");
        page.fill("#password", "12345");

        // Se selecciona el botón de confirmar registro
        page.click("#btnSubmit");

        // Se identifica la alerta de registro exitoso
        page.waitForSelector(".swal2-popup .swal2-title");
        Locator successAlert = page.locator(".swal2-popup .swal2-title");

        // Se valida el mensaje de registro exitoso
        assertEquals("New User Registered!", successAlert.innerText());

        // Se cierra la alerta
        page.click(".swal2-confirm");
        page.close();
    }

    @Test
    public void testAddUserEmptyFields(){
        // Se prueba que se haya completado el login al validar la URL de redireccion
        assertEquals("http://localhost:8080/home", page.url());

        // Se selecciona boton de productos
        page.click("#btn-users");

        // Se define la espera de redireccion
        page.waitForURL("http://localhost:8080/users");

        // Se prueba que se haya completado la redireccion al validar la URL
        assertEquals("http://localhost:8080/users", page.url());

        // Se selecciona el botón de logout
        page.click("#btn-add-user");

        // Se identifica el modal para agregar el producto
        page.waitForSelector("#addUserModal");

        // Se selecciona el botón de confirmar registro
        page.click("#btnSubmit");

        // Se definen los identificadores de los mensajes de error
        Locator usernameInvalidFeedback = page.locator("#username ~ .invalid-feedback");
        Locator nameInvalidFeedback = page.locator("#name ~ .invalid-feedback");
        Locator roleInvalidFeedback = page.locator("#role ~ .invalid-feedback");
        Locator passwordInvalidFeedback = page.locator("#password ~ .invalid-feedback");

        // Se valida que los mensajes de entrada invalida son desplegados
        assertTrue(usernameInvalidFeedback.isVisible());
        assertTrue(nameInvalidFeedback.isVisible());
        assertTrue(roleInvalidFeedback.isVisible());
        assertTrue(passwordInvalidFeedback.isVisible());

        // Se valida el contenido de los mensajes invalidos
        assertEquals("This field cannot be empty!", usernameInvalidFeedback.innerText());
        assertEquals("This field is empty or invalid!", nameInvalidFeedback.innerText());
        assertEquals("Please select a role!", roleInvalidFeedback.innerText());
        assertEquals("This field is empty or invalid!", passwordInvalidFeedback.innerText());

        page.close();
    }

    @Test
    public void testEditUserSuccessful(){
        // Se prueba que se haya completado el login al validar la URL de redireccion
        assertEquals("http://localhost:8080/home", page.url());

        // Se selecciona boton de productos
        page.click("#btn-users");

        // Se define la espera de redireccion
        page.waitForURL("http://localhost:8080/users");

        // Se prueba que se haya completado la redireccion al validar la URL
        assertEquals("http://localhost:8080/users", page.url());

        // Selecciona el boton de Editar en el primer producto de la tabla
        page.click("#tableUsers tbody tr:first-child .edit-btn");

        // Se identifica el modal para agregar el producto
        page.waitForSelector("#editUserModal");

        // Completamos los campos con un producto valido
        page.fill("#editName", "Updated Name User");

        // Se selecciona el botón de confirmar registro
        page.click("#btnSaveChanges");

        // Se identifica la alerta de registro exitoso
        page.waitForSelector(".swal2-popup .swal2-title");
        Locator successAlert = page.locator(".swal2-popup .swal2-title");

        // Se valida el mensaje de registro exitoso
        assertEquals("User Modification Completed!", successAlert.innerText());

        // Se cierra la alerta
        page.click(".swal2-confirm");
        page.close();
    }
}
