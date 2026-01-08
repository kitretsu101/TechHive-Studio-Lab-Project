package com.techhive.controller;

import com.techhive.model.Engineer;
import com.techhive.util.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Engineer Profile page.
 */
public class EngineerProfileController implements Initializable {

    @FXML
    private HBox navBar;
    @FXML
    private Label logoLabel;
    @FXML
    private HBox navLinks;
    @FXML
    private Button startProjectBtn;
    @FXML
    private StackPane profileImageContainer;
    @FXML
    private Label engineerName;
    @FXML
    private Label engineerRole;
    @FXML
    private Label engineerBio;
    @FXML
    private FlowPane skillsPane;
    @FXML
    private VBox projectsSection;
    @FXML
    private Button backButton;

    private Engineer engineer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupNavigation();
    }

    public void setEngineer(Engineer engineer) {
        this.engineer = engineer;
        populateProfile();
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

        if (backButton != null) {
            backButton.setOnAction(e -> SceneManager.getInstance().navigateToEngineers());
        }
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

    private void populateProfile() {
        if (engineer == null)
            return;

        // Profile image placeholder
        if (profileImageContainer != null) {
            profileImageContainer.getChildren().clear();

            Circle imageCircle = new Circle(80);
            imageCircle.getStyleClass().add("profile-image-circle");

            Label initialsLabel = new Label(getInitials(engineer.getName()));
            initialsLabel.getStyleClass().add("profile-initials");

            profileImageContainer.getChildren().addAll(imageCircle, initialsLabel);
        }

        // Name and role
        if (engineerName != null) {
            engineerName.setText(engineer.getName());
        }
        if (engineerRole != null) {
            engineerRole.setText(engineer.getRole());
        }

        // Bio
        if (engineerBio != null) {
            engineerBio.setText(engineer.getBio());
        }

        // Skills
        if (skillsPane != null) {
            skillsPane.getChildren().clear();
            skillsPane.setHgap(12);
            skillsPane.setVgap(12);

            for (String skill : engineer.getSkills()) {
                Label skillTag = new Label(skill);
                skillTag.getStyleClass().add("skill-tag-large");
                skillsPane.getChildren().add(skillTag);
            }
        }

        // Projects section
        if (projectsSection != null) {
            setupProjectsSection();
        }
    }

    private void setupProjectsSection() {
        projectsSection.getChildren().clear();
        projectsSection.setSpacing(20);

        Label sectionTitle = new Label("Featured Contributions");
        sectionTitle.getStyleClass().add("section-title");
        projectsSection.getChildren().add(sectionTitle);

        // Sample projects for this engineer
        String[][] projects = {
                { "E-Commerce Platform Redesign",
                        "Led the frontend development of a modern e-commerce platform, improving conversion rates by 40%." },
                { "Analytics Dashboard", "Built real-time data visualization components using modern frameworks." },
                { "Mobile App Development", "Contributed to cross-platform mobile application development." }
        };

        for (String[] project : projects) {
            VBox projectCard = createProjectContributionCard(project[0], project[1]);
            projectsSection.getChildren().add(projectCard);
        }
    }

    private VBox createProjectContributionCard(String title, String description) {
        VBox card = new VBox(10);
        card.getStyleClass().add("contribution-card");
        card.setPadding(new Insets(20));

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("contribution-title");

        Label descLabel = new Label(description);
        descLabel.getStyleClass().add("contribution-description");
        descLabel.setWrapText(true);

        card.getChildren().addAll(titleLabel, descLabel);

        return card;
    }

    private String getInitials(String name) {
        String[] parts = name.split(" ");
        if (parts.length >= 2) {
            return "" + parts[0].charAt(0) + parts[1].charAt(0);
        }
        return name.substring(0, Math.min(2, name.length())).toUpperCase();
    }
}
