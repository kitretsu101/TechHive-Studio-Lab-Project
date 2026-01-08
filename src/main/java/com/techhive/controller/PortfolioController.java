package com.techhive.controller;

import com.techhive.data.DataProvider;
import com.techhive.model.Project;
import com.techhive.util.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the Portfolio page.
 */
public class PortfolioController implements Initializable {

    @FXML
    private HBox navBar;
    @FXML
    private Label logoLabel;
    @FXML
    private HBox navLinks;
    @FXML
    private Button startProjectBtn;
    @FXML
    private VBox projectsContainer;
    @FXML
    private Label pageTitle;
    @FXML
    private Label pageSubtitle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupNavigation();
        loadProjects();
    }

    private void setupNavigation() {
        logoLabel.setOnMouseClicked(e -> SceneManager.getInstance().navigateToHome());

        String[] navItems = { "Home", "Services", "Portfolio", "Our Engineers", "Contribute", "Contact" };
        navLinks.getChildren().clear();

        for (String item : navItems) {
            Label link = new Label(item);
            link.getStyleClass().add("nav-link");
            if (item.equals("Portfolio")) {
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

    private void loadProjects() {
        if (projectsContainer == null)
            return;

        projectsContainer.getChildren().clear();
        projectsContainer.setSpacing(40);

        List<Project> projects = DataProvider.getAllProjects();

        boolean imageOnLeft = true;
        for (Project project : projects) {
            HBox projectCard = createProjectCard(project, imageOnLeft);
            projectsContainer.getChildren().add(projectCard);
            imageOnLeft = !imageOnLeft; // Alternate layout
        }
    }

    private HBox createProjectCard(Project project, boolean imageOnLeft) {
        HBox card = new HBox(40);
        card.getStyleClass().add("project-card");
        card.setPadding(new Insets(40));
        card.setAlignment(Pos.CENTER);

        // Image placeholder
        StackPane imagePlaceholder = createImagePlaceholder(project);

        // Content section
        VBox content = createProjectContent(project);

        if (imageOnLeft) {
            card.getChildren().addAll(imagePlaceholder, content);
        } else {
            card.getChildren().addAll(content, imagePlaceholder);
        }

        return card;
    }

    private StackPane createImagePlaceholder(Project project) {
        StackPane container = new StackPane();
        container.setPrefSize(400, 280);
        container.getStyleClass().add("project-image-container");

        Rectangle rect = new Rectangle(400, 280);
        rect.getStyleClass().add("project-image-placeholder");
        rect.setArcWidth(20);
        rect.setArcHeight(20);

        Label projectInitial = new Label(project.getName().substring(0, 1));
        projectInitial.getStyleClass().add("project-initial");

        container.getChildren().addAll(rect, projectInitial);

        return container;
    }

    private VBox createProjectContent(Project project) {
        VBox content = new VBox(20);
        content.setMaxWidth(500);
        content.setAlignment(Pos.TOP_LEFT);

        // Category badge
        Label categoryBadge = new Label(project.getCategory());
        categoryBadge.getStyleClass().add("category-badge");

        // Project name
        Label nameLabel = new Label(project.getName());
        nameLabel.getStyleClass().add("project-name");

        // Problem section
        VBox problemSection = createContentSection("The Challenge", project.getProblem());

        // Solution section
        VBox solutionSection = createContentSection("Our Solution", project.getSolution());

        // Outcome section
        VBox outcomeSection = createContentSection("The Results", project.getOutcome());

        // Tech stack
        FlowPane techStack = new FlowPane();
        techStack.setHgap(10);
        techStack.setVgap(10);
        techStack.getStyleClass().add("tech-stack");

        for (String tech : project.getTechStack()) {
            Label techBadge = new Label(tech);
            techBadge.getStyleClass().add("tech-badge");
            techStack.getChildren().add(techBadge);
        }

        content.getChildren().addAll(categoryBadge, nameLabel, problemSection, solutionSection, outcomeSection,
                techStack);

        return content;
    }

    private VBox createContentSection(String title, String text) {
        VBox section = new VBox(8);

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("content-section-title");

        Label textLabel = new Label(text);
        textLabel.getStyleClass().add("content-section-text");
        textLabel.setWrapText(true);
        textLabel.setMaxWidth(480);

        section.getChildren().addAll(titleLabel, textLabel);

        return section;
    }
}
