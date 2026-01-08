package org.example.techhive_studio_website_project_final.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.example.techhive_studio_website_project_final.model.Engineer;
import org.example.techhive_studio_website_project_final.core.SceneManager;

public class EngineerProfileController {

    @FXML
    private ImageView profileImage;
    @FXML
    private Label nameLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private Label bioLabel;
    @FXML
    private HBox skillsContainer;

    private Engineer engineer;

    public void setEngineer(Engineer engineer) {
        this.engineer = engineer;
        updateUI();
    }

    private void updateUI() {
        if (engineer == null)
            return;

        nameLabel.setText(engineer.getName());
        roleLabel.setText(engineer.getRole());
        bioLabel.setText(engineer.getBio());

        // Setup image (using placeholder logic for now since URL is dummy)
        // profileImage.setImage(new Image(engineer.getImageUrl()));

        skillsContainer.getChildren().clear();
        for (String skill : engineer.getSkills()) {
            Label tag = new Label(skill);
            tag.setStyle(
                    "-fx-background-color: rgba(0, 240, 255, 0.1); -fx-text-fill: -color-primary; -fx-padding: 5 10; -fx-background-radius: 15;");
            skillsContainer.getChildren().add(tag);
        }
    }

    @FXML
    private void onBackClick() {
        SceneManager.getInstance().switchView("fxml/EngineersView.fxml");
    }
}
