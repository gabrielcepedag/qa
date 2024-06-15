package com.example.application.views.product;

import com.example.application.views.model.Product;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Product List")
@Route(value = "products")
public class ProductListView extends Composite<VerticalLayout> {

    public ProductListView() {
        Grid<Product> grid = new Grid<>(Product.class, false);
        grid.addColumn(Product::getId).setHeader("ID");
        grid.addColumn(Product::getName).setHeader("Name");
        grid.addColumn(Product::getPrice).setHeader("Price");
        grid.addComponentColumn(product -> createAvailableIcon(product.getQuantity()))
        .setHeader("Available");

        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Arroz", 200, 10));
        products.add(new Product(2, "Carne", 100, 2));
        products.add(new Product(3, "Maiz", 50, 30));
        products.add(new Product(4, "Queso", 500, 0));
        products.add(new Product(5, "Jamon", 400, 1));
        products.add(new Product(6, "Pollo", 2000, 0));
        products.add(new Product(7, "Azucar", 10, 0));

        Button button = new Button();
        button.setText("Solicitar");
        button.setEnabled(false);
        button.setWidth("min-content");
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.getStyle().set("margin", "auto");

        grid.setItems(products);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE).addSelectionListener(event -> {
            if (grid.getSelectedItems().isEmpty()){
                button.setEnabled(false);
            }else{
                button.setEnabled(true);
            }
        });

        button.addClickListener(event -> {
            if (grid.getSelectedItems().isEmpty()){
                Notification.show("No hay seleccionado ningun producto", 3000, Notification.Position.BOTTOM_END);
            }else{
                Notification.show("Producto solicitado exitosamente", 3000, Notification.Position.BOTTOM_END);
            }
        });

        getContent().add(grid, button);
    }

    private Icon createAvailableIcon(int quantity) {
        boolean isAvailable = quantity > 0;
        Icon icon;
        if (isAvailable) {
            icon = VaadinIcon.CHECK.create();
            icon.getElement().getThemeList().add("badge success");
        } else {
            icon = VaadinIcon.CLOSE_SMALL.create();
            icon.getElement().getThemeList().add("badge error");
        }
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        return icon;
    }
}
