package com.example.application.views.login;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

@PageTitle("Login")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class LoginView extends Composite<VerticalLayout> {

    public LoginView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        LoginForm loginForm = new LoginForm();
        loginForm.setForgotPasswordButtonVisible(false);

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
//        loginForm.setWidth("100%");
        getContent().add(layoutRow);
        layoutRow.add(loginForm);

        loginForm.addLoginListener(e -> {
            String username = e.getUsername();
            String password = e.getPassword();

            if (isValidCredentials(username, password)){
                Notification.show("Inicio de sesión exitoso", 3000, Notification.Position.MIDDLE);
            }else{
                loginForm.setError(true);
                Notification.show("Usuario o contraseña incorrectos", 3000, Notification.Position.MIDDLE);
            }
        });
    }

    private boolean isValidCredentials(String username, String password){
        return "admin".equals(username) && "admin".equals(password);
    }
}
