package com.controller;

import com.model.User;
import com.service.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;



public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;


    @FXML
    public void handleLogin(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();


        boolean isValid = com.service.DataStore.validateLogin(username, password);

        if (isValid) {

            com.model.User user = new com.model.User(username);

            com.service.DataStore.loadUserProfile(user);

            com.service.UserSession.getInstance().setUser(user);

            Parent root = FXMLLoader.load(getClass().getResource("/FXML/homepage.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } else {
            System.out.println("Invalid credentials!");

        }
    }

    @FXML
    public void goToSignup(ActionEvent event) throws IOException {
        System.out.println("Switching to Signup screen...");


        Parent root = FXMLLoader.load(getClass().getResource("/FXML/signup.fxml"));


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}