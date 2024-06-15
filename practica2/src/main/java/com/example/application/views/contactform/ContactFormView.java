package com.example.application.views.contactform;

import com.example.application.views.MainLayout;
import com.example.application.views.model.ContactFormModel;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

@PageTitle("Contact Form")
@Route(value = "contact-form", layout = MainLayout.class)
public class ContactFormView extends Composite<VerticalLayout> {

    private Binder<ContactFormModel> binder = new Binder<>(ContactFormModel.class);

    public ContactFormView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();
        TextField textField = new TextField();
        EmailField emailField = new EmailField();
        HorizontalLayout layoutRow = new HorizontalLayout();
        TextArea textArea = new TextArea();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        Button buttonPrimary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("500px");
        h3.setText("Contact Form");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        textField.setLabel("Nombre");
        textField.setId("nombre");
        emailField.setLabel("Email");
        emailField.setId("email");
        layoutRow.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        textArea.setLabel("Mensaje");
        textArea.setId("mensaje");
        layoutRow.setAlignSelf(FlexComponent.Alignment.CENTER, textArea);
        textArea.getStyle().set("flex-grow", "1");
        textArea.setMaxLength(50);
        textArea.setHelperText("Máximo 50 caracteres");
        textArea.setHeightFull();
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        buttonPrimary.setText("Enviar");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(textField);
        formLayout2Col.add(emailField);
        layoutColumn2.add(layoutRow);
        layoutRow.add(textArea);
        layoutColumn2.add(layoutRow2);
        layoutRow2.add(buttonPrimary);

//        Paragraph resultMessage = new Paragraph();
//        resultMessage.setId("msg");
//        resultMessage.setClassName("result-message");
//        resultMessage.setVisible(false);
//        getContent().add(resultMessage);

        binder.forField(emailField)
                .withValidator(new EmailValidator("Por favor, ingrese un correo electrónico válido"))
                .asRequired("Por favor, ingresa un correo electronico")
                .bind(ContactFormModel::getEmail, ContactFormModel::setEmail);

        binder.forField(textField)
                .asRequired("Por favor, ingrese su nombre")
                .bind(ContactFormModel::getName, ContactFormModel::setName);

        binder.forField(textArea)
                .asRequired("Por favor, ingrese un mensaje")
                .withValidator(message -> message.length() <= 50, "El mensaje debe tener 50 caracteres o menos")
                .bind(ContactFormModel::getMessage, ContactFormModel::setMessage);

        buttonPrimary.addClickListener(e -> {
            ContactFormModel contactFormModel = new ContactFormModel();

            try {
                binder.writeBean(contactFormModel);
//                resultMessage.setText("Nombre: " + contactFormModel.getName() + " Email: " + contactFormModel.getEmail() + " Mensaje: " + contactFormModel.getMessage());
                Notification.show("Formulario enviado con éxito", 3000, Notification.Position.MIDDLE);
            } catch (ValidationException exp) {
                Notification.show("Por favor, complete todos los campos correctamente", 3000, Notification.Position.MIDDLE);
            }
        });
    }
}
