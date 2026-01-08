package org.example.techhive_studio_website_project_final.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HomeController {

    @FXML
    private ImageView heroImage;

    @FXML
    public void initialize() {
        // Load a placeholder image for the dashboard preview
        try {
            heroImage.setImage(new Image("https://via.placeholder.com/800x400"));
        } catch (Exception e) {
            System.out.println("Could not load hero image: " + e.getMessage());
        }
    }
}
