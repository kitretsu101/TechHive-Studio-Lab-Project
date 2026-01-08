package com.techhive.controller;

import com.techhive.data.DataProvider;
import com.techhive.model.Engineer;
import com.techhive.util.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the Our Engineers page.
 */
public class EngineersController implements Initializable {

    @FXML
    private HBox navBar;
    @FXML
    private Label logoLabel;
    @FXML
    private HBox navLinks;
    @FXML
    private Button startProjectBtn;
    @FXML
    private HBox filterButtons;
    @FXML
    private FlowPane engineersGrid;
    @FXML
    private Label pageTitle;
    @FXML
    private Label pageSubtitle;

    private String currentFilter = "All";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupNavigation();
        setupFilterButtons();
        loadEngineers("All");
    }

    private void setupNavigation() {
        logoLabel.setOnMouseClicked(e -> SceneManager.getInstance().navigateToHome());

        String[] navItems = { "Home", "Services", "Portfolio", "Our Engineers", "Contribute", "Contact" };
        navLinks.getChildren().clear();

        for (String item : navItems) {
            Label link = new Label(item);
            link.getStyleClass().add("nav-link");
            if (item.equals("Our Engineers")) {
                link.getStyleClass().add("nav-link-active");
            }
            link.setOnMouseClicked(e -> handleNavClick(item));
            navLinks.getChildren().add(link);
        }

        startProjectBtn.setOnAction(e -> SceneManager.getInstance().navigateToContact());
    }

    private void handleNavClick(String item) {
        switch (item) {
            case "Home" -> SceneManager.getInstance().navigateToHome();
            case "Services" -> SceneManager.getInstance().navigateToServices();
            case "Portfolio" -> SceneManager.getInstance().navigateToPortfolio();
            case "Our Engineers" -> SceneManager.getInstance().navigateToEngineers();
            case "Contribute" -> SceneManager.getInstance().navigateToContribute();
            case "Contact" -> SceneManager.getInstance().navigateToContact();
        }
    }

    private void setupFilterButtons() {
        if (filterButtons == null)
            return;

        filterButtons.getChildren().clear();
        filterButtons.setSpacing(15);
        filterButtons.setAlignment(Pos.CENTER);

        String[] filters = { "All", "Frontend Engineer", "Backend Engineer", "Fullstack Engineer", "DevOps Engineer",
                "Product Designer" };

        for (String filter : filters) {
            Button btn = new Button(filter);
            btn.getStyleClass().add("filter-btn");
            if (filter.equals("All")) {
                btn.getStyleClass().add("filter-btn-active");
            }
            btn.setOnAction(e -> {
                currentFilter = filter;
                updateFilterButtonStyles();
                loadEngineers(filter);
            });
            filterButtons.getChildren().add(btn);
        }
    }

    private void updateFilterButtonStyles() {
        for (var node : filterButtons.getChildren()) {
            if (node instanceof Button btn) {
                btn.getStyleClass().remove("filter-btn-active");
                if (btn.getText().equals(currentFilter)) {
                    btn.getStyleClass().add("filter-btn-active");
                }
            }
        }
    }

    private void loadEngineers(String filter) {
        if (engineersGrid == null)
            return;

        engineersGrid.getChildren().clear();

        List<Engineer> engineers = DataProvider.getEngineersByCategory(filter);

        for (Engineer engineer : engineers) {
            VBox card = createEngineerCard(engineer);
            engineersGrid.getChildren().add(card);
        }
    }

    private VBox createEngineerCard(Engineer engineer) {
        VBox card = new VBox(15);
        card.getStyleClass().add("engineer-card");
        card.setPadding(new Insets(25));
        card.setAlignment(Pos.TOP_CENTER);
        card.setPrefWidth(300);
        card.setPrefHeight(400);

        // Profile image placeholder (circle)
        StackPane imageContainer = new StackPane();
        imageContainer.setPrefSize(120, 120);

        Circle imageCircle = new Circle(60);
        imageCircle.getStyleClass().add("engineer-image-circle");

        // Try to load image or use placeholder
        Label initialsLabel = new Label(getInitials(engineer.getName()));
        initialsLabel.getStyleClass().add("engineer-initials");

        imageContainer.getChildren().addAll(imageCircle, initialsLabel);

        // Name
        Label nameLabel = new Label(engineer.getName());
        nameLabel.getStyleClass().add("engineer-name");

        // Role
        Label roleLabel = new Label(engineer.getRole());
        roleLabel.getStyleClass().add("engineer-role");

        // Short bio (truncated)
        Label bioLabel = new Label(truncateBio(engineer.getBio(), 100));
        bioLabel.getStyleClass().add("engineer-bio");
        bioLabel.setWrapText(true);
        bioLabel.setMaxWidth(250);

        // Skills tags
        FlowPane skillsPane = new FlowPane();
        skillsPane.setHgap(8);
        skillsPane.setVgap(8);
        skillsPane.setAlignment(Pos.CENTER);

        int maxSkills = Math.min(4, engineer.getSkills().size());
        for (int i = 0; i < maxSkills; i++) {
            Label skillTag = new Label(engineer.getSkills().get(i));
            skillTag.getStyleClass().add("skill-tag");
            skillsPane.getChildren().add(skillTag);
        }

        if (engineer.getSkills().size() > 4) {
            Label moreTag = new Label("+" + (engineer.getSkills().size() - 4));
            moreTag.getStyleClass().add("skill-tag-more");
            skillsPane.getChildren().add(moreTag);
        }

        card.getChildren().addAll(imageContainer, nameLabel, roleLabel, bioLabel, skillsPane);

        // Click handler
        card.setOnMouseClicked(e -> SceneManager.getInstance().navigateToEngineerProfile(engineer));

        // Hover effects
        card.setOnMouseEntered(e -> card.getStyleClass().add("engineer-card-hover"));
        card.setOnMouseExited(e -> card.getStyleClass().remove("engineer-card-hover"));

        return card;
    }

    private String getInitials(String name) {
        String[] parts = name.split(" ");
        if (parts.length >= 2) {
            return "" + parts[0].charAt(0) + parts[1].charAt(0);
        }
        return name.substring(0, Math.min(2, name.length())).toUpperCase();
    }

    private String truncateBio(String bio, int maxLength) {
        if (bio.length() <= maxLength)
            return bio;
        return bio.substring(0, maxLength - 3) + "...";
    }
}
