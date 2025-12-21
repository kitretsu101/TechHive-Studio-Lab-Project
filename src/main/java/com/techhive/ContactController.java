package com.techhive;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ContactController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private ComboBox<String> serviceBox;

    @FXML
    private TextArea messageArea;

    @FXML
    private Label statusLabel;

    @FXML
    private void handleSubmit() {
        String name = nameField.getText();
        String email = emailField.getText();
        String service = serviceBox.getValue();
        String message = messageArea.getText();

        if (name.isEmpty() || email.isEmpty() || service == null) {
            statusLabel.setText("Please fill in all required fields.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        DatabaseHandler.insertInquiry(name, email, service, message);
        statusLabel.setText("Inquiry submitted successfully!");
        statusLabel.setStyle("-fx-text-fill: green;");

        // Clear fields
        nameField.clear();
        emailField.clear();
        serviceBox.getSelectionModel().clearSelection();
        messageArea.clear();
    }
}
