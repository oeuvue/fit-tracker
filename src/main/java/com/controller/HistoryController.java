package com.controller;

import com.model.User;
import com.service.DataStore;
import com.service.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class HistoryController {

    @FXML private TextArea historyArea; // Must match fx:id in FXML

    @FXML
    public void initialize() {
        User user = UserSession.getInstance().getUser();
        if (user != null) {
            // Load the data
            String history = DataStore.loadHistoryFile(user.getUsername());
            historyArea.setText(history);

            // Auto-scroll to bottom to see latest workout
            historyArea.setScrollTop(Double.MAX_VALUE);
        } else {
            historyArea.setText("Error: No user logged in.");
        }
    }

    @FXML
    public void handleClearHistory() {
        User user = UserSession.getInstance().getUser();
        if (user != null) {
            File file = new File(user.getUsername() + "_history.txt");
            if(file.exists()) {
                if(file.delete()) {
                    historyArea.setText("History Cleared.");
                } else {
                    historyArea.setText("Could not delete file.");
                }
            }
        }
    }

    @FXML
    public void handleBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/homepage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}