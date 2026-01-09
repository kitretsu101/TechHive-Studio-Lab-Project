package org.example.techhive_studio_website_project_final.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.example.techhive_studio_website_project_final.data.DataStore;
import org.example.techhive_studio_website_project_final.model.Engineer;

import java.util.List;
import java.util.stream.Collectors;

public class EngineersController {

    @FXML
    private FlowPane engineersGrid;

    @FXML
    public void initialize() {
        loadEngineers("All");
    }

    @FXML
    private void onFilterAll() {
        loadEngineers("All");
    }

    @FXML
    private void onFilterFrontend() {
        loadEngineers("Frontend Engineer");
    }

    @FXML
    private void onFilterBackend() {
        loadEngineers("Backend Engineer");
    }

    @FXML
    private void onFilterFullstack() {
        loadEngineers("Fullstack Engineer");
    }

    @FXML
    private void onFilterDevOps() {
        loadEngineers("DevOps Engineer");
    }

    @FXML
    private void onFilterDesigner() {
        loadEngineers("UI/UX Designer");
    }

    private void loadEngineers(String filterRole) {
        engineersGrid.getChildren().clear();
        List<Engineer> allEngineers = DataStore.getInstance().getEngineers();

        List<Engineer> filtered = allEngineers.stream()
                .filter(e -> filterRole.equals("All") || e.getRole().contains(filterRole.split(" ")[0])) // Simple
                                                                                                         // contain
                                                                                                         // check
                .collect(Collectors.toList());

        for (Engineer eng : filtered) {
            engineersGrid.getChildren().add(createEngineerCard(eng));
        }
    }

    private VBox createEngineerCard(Engineer engineer) {
        VBox card = new VBox(10);
        card.getStyleClass().add("card");
        card.setPrefWidth(220);
        card.setPrefHeight(280);
        card.setAlignment(javafx.geometry.Pos.CENTER);

        // Image
        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(false); // Fill the circle

        // Circle clip
        javafx.scene.shape.Circle clip = new javafx.scene.shape.Circle(50, 50, 50);
        imageView.setClip(clip);

        String imageUrl = engineer.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                javafx.scene.image.Image image = null;
                if (imageUrl.startsWith("http")) {
                    image = new javafx.scene.image.Image(imageUrl, true);
                } else {
                    String path = "/org/example/techhive_studio_website_project_final/images/engineers/" + imageUrl;
                    java.net.URL resource = getClass().getResource(path);
                    if (resource != null) {
                        image = new javafx.scene.image.Image(resource.toExternalForm());
                    }
                }

                if (image != null) {
                    imageView.setImage(image);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Label name = new Label(engineer.getName());
        name.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: -color-text-primary;");

        Label role = new Label(engineer.getRole());
        role.setStyle("-fx-text-fill: -color-primary; -fx-font-size: 12px;");

        Label bio = new Label(engineer.getBio());
        bio.setWrapText(true);
        bio.setStyle("-fx-text-alignment: center; -fx-text-fill: -color-text-secondary; -fx-font-size: 11px;");

        // Skills (just first 2 for summary)
        Label skills = new Label(
                String.join(" • ", engineer.getSkills().subList(0, Math.min(engineer.getSkills().size(), 2))));
        skills.setStyle("-fx-text-fill: -color-text-muted; -fx-font-size: 10px;");

        card.getChildren().addAll(imageView, name, role, bio, skills);

        card.setOnMouseClicked(e -> {
            org.example.techhive_studio_website_project_final.core.SceneManager.getInstance()
                    .switchView("fxml/EngineerProfileView.fxml", (EngineerProfileController controller) -> {
                        controller.setEngineer(engineer);
                    });
        });

        return card;
    }
}
