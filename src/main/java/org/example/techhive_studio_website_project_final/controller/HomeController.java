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
        // Load the hero dashboard image
        try {
            java.net.URL imageUrl = getClass()
                    .getResource("/org/example/techhive_studio_website_project_final/images/hero_dashboard.jpg");
            if (imageUrl != null) {
                heroImage.setImage(new Image(imageUrl.toExternalForm()));
            } else {
                System.out.println("Hero image resource not found.");
            }
        } catch (Exception e) {
            System.out.println("Could not load hero image: " + e.getMessage());
        }
    }
}
