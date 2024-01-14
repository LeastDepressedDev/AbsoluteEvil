package me.qigan.abse.ant;

import javax.swing.*;

public class LoginScreen extends JFrame {
    public LoginScreen() {
        super();
        setTitle("Enter login data");
        setSize(700, 400);
        setResizable(false);

        add(new InputField());
    }
}
