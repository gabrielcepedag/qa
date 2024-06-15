package com.example.application;

import com.example.application.views.model.Product;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.ArrayList;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class ProductListTest extends PlaywrightIT{
    @LocalServerPort
    private int port;
    private Locator grid;
    private Locator content;
    private Locator button;

    @BeforeEach
    void setUp() {
        String baseUrl = "http://localhost:" + port;
        page.navigate(baseUrl + "/products");

        grid = page.locator("vaadin-grid");
        content = grid.locator("vaadin-grid-cell-content");
        button = page.locator("vaadin-button:has-text('Solicitar')");
    }

    @Test
    public void grid_is_visible(){
        assertThat(grid).isVisible();
        assertThat(button).isVisible();
    }

    @Test
    public void button_is_disable(){
        assertThat(button).isDisabled();
    }

    @Test
    public void count_products_in_grid(){
        assertThat(content).hasCount(50);
    }

    @Test
    public void enable_disable_button_on_select(){
        content.nth(15).click();
        assertThat(button).isEnabled();
        content.nth(20).click();
        assertThat(button).isEnabled();
        content.nth(20).click();
        assertThat(button).isDisabled();
    }

    @Test
    public void message_on_send_when_product_isSelected(){
        content.nth(15).click();
        assertThat(button).isEnabled();
        button.click();
        assertThat(page.locator("vaadin-notification-card:has-text('Producto solicitado exitosamente')")).isVisible();
    }

    @Test
    public void icons_are_correctly_selected(){
        Locator arroz = grid.locator("vaadin-grid-cell-content:has-text('10') + vaadin-grid-cell-content vaadin-icon").first();
        assertThat(arroz).hasAttribute("theme", "badge success");

        Locator queso = grid.locator("vaadin-grid-cell-content:has-text('0') + vaadin-grid-cell-content vaadin-icon").last();
        assertThat(queso).hasAttribute("theme", "badge error");
    }
}
