package com.example.application.views.login;

import com.example.application.views.about.AboutView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Log in")
@Route(value = "")
public class LogInView extends HorizontalLayout {

    public LogInView() {
        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(loginEvent -> {
            UI.getCurrent().navigate(AboutView.class);
        });

        add(loginForm);
    }

}
