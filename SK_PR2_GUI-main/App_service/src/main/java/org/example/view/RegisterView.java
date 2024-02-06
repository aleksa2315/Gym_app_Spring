package org.example.view;

import org.example.MyApp;
import org.example.restClient.UserServiceClient;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dateOfBirthField;
    private JComboBox<String> userTypeBox;
    private UserServiceClient userServiceClient;

    public RegisterView() {
        userServiceClient = new UserServiceClient();
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        // Add username field
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(5, 5, 5, 5);
        add(new JLabel("Username:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        usernameField = new JTextField(20);
        add(usernameField, constraints);

        // Add password field
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.EAST;
        add(new JLabel("Password:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        passwordField = new JPasswordField(20);
        add(passwordField, constraints);

        // Add email field
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST;
        add(new JLabel("Email:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        emailField = new JTextField(20);
        add(emailField, constraints);

        // Add first name field
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.EAST;
        add(new JLabel("First name:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        firstNameField = new JTextField(20);
        add(firstNameField, constraints);

        // Add last name field
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.EAST;
        add(new JLabel("Last name:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        lastNameField = new JTextField(20);
        add(lastNameField, constraints);

        // Add date of birth field
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.EAST;
        add(new JLabel("Date of birth:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        dateOfBirthField = new JTextField(20);
        add(dateOfBirthField, constraints);

        // Add user type box
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.anchor = GridBagConstraints.EAST;
        String[] userTypes = {"KLIJENT", "MENADZER"};
        userTypeBox = new JComboBox<>(userTypes);
        add(new JLabel("User type:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(userTypeBox, constraints);

        // Add submit button
        constraints.gridx = 1;
        constraints.gridy = 7;
        constraints.anchor = GridBagConstraints.CENTER;
        JButton submitButton = new JButton("Submit");
        add(submitButton, constraints);
        submitButton.addActionListener(e -> {
            boolean created = userServiceClient.registrujKorisnika(
                    usernameField.getText(),
                    String.valueOf(passwordField.getPassword()),
                    emailField.getText(),
                    firstNameField.getText(),
                    lastNameField.getText(),
                    dateOfBirthField.getText(),
                    userTypeBox.getSelectedItem().toString());

            System.out.println(created);

            if (created){
                MyApp.getInstance().getjPanel().remove(MyApp.getInstance().getRegisterView());
                MyApp.getInstance().getjPanel().add(MyApp.getInstance().getLoginView());
                this.setVisible(false);
                MyApp.getInstance().getLoginView().setVisible(true);
                MyApp.getInstance().getjPanel().updateUI();
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.anchor = GridBagConstraints.CENTER;
        JButton back = new JButton("Back");
        add(back, constraints);
        back.addActionListener(e -> {
            MyApp.getInstance().getjPanel().remove(MyApp.getInstance().getRegisterView());
            MyApp.getInstance().getjPanel().add(MyApp.getInstance().getLoginView());
            this.setVisible(false);
            MyApp.getInstance().getLoginView().setVisible(true);
            MyApp.getInstance().getjPanel().updateUI();
        });
    }


    public JTextField getUsernameField() {
        return usernameField;
    }

    public void setUsernameField(JTextField usernameField) {
        this.usernameField = usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public void setEmailField(JTextField emailField) {
        this.emailField = emailField;
    }

    public JTextField getFirstNameField() {
        return firstNameField;
    }

    public void setFirstNameField(JTextField firstNameField) {
        this.firstNameField = firstNameField;
    }

    public JTextField getLastNameField() {
        return lastNameField;
    }

    public void setLastNameField(JTextField lastNameField) {
        this.lastNameField = lastNameField;
    }

    public JTextField getDateOfBirthField() {
        return dateOfBirthField;
    }

    public void setDateOfBirthField(JTextField dateOfBirthField) {
        this.dateOfBirthField = dateOfBirthField;
    }

    public JComboBox<String> getUserTypeBox() {
        return userTypeBox;
    }

    public void setUserTypeBox(JComboBox<String> userTypeBox) {
        this.userTypeBox = userTypeBox;
    }


}
