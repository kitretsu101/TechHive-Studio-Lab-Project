package com.techhive.controller;

import com.techhive.util.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Home page.
 */
public class HomeController implements Initializable {

    @FXML
    private HBox navBar;
    @FXML
    private Label logoLabel;
    @FXML
    private HBox navLinks;
    @FXML
    private Button startProjectBtn;
    @FXML
    private VBox heroSection;
    @FXML
    private Label heroTitle;
    @FXML
    private Label heroSubtitle;
    @FXML
    private HBox heroButtons;
    @FXML
    private FlowPane servicesGrid;
    @FXML
    private HBox statsSection;
    @FXML
    private VBox ctaSection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupNavigation();
        setupHeroSection();
        setupServicesSection();
        setupStatsSection();
        setupCtaSection();
    }

    private void setupNavigation() {
        // Logo click handler
        logoLabel.setOnMouseClicked(e -> SceneManager.getInstance().navigateToHome());

        // Navigation links
        String[] navItems = { "Home", "Services", "Portfolio", "Our Engineers", "Contribute", "Contact" };
        navLinks.getChildren().clear();

        for (String item : navItems) {
            Label link = new Label(item);
            link.getStyleClass().add("nav-link");
            link.setOnMouseClicked(e -> handleNavClick(item));
            navLinks.getChildren().add(link);
        }

        // Start Project button
        startProjectBtn.setOnAction(e -> handleStartProject());
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

    private void scrollToServices() {
        // In a real implementation, scroll to services section
        if (servicesGrid != null) {
            servicesGrid.requestFocus();
        }
    }

    private void handleStartProject() {
        // Could open contact form or navigate to contact page
        SceneManager.getInstance().navigateToContact();
    }

    private void setupHeroSection() {
        // Hero content is set in FXML, add button handlers
        if (heroButtons != null) {
            heroButtons.getChildren().clear();

            Button startBtn = new Button("Start a Project");
            startBtn.getStyleClass().addAll("btn", "btn-primary");
            startBtn.setOnAction(e -> handleStartProject());

            Button portfolioBtn = new Button("View Portfolio");
            portfolioBtn.getStyleClass().addAll("btn", "btn-secondary");
            portfolioBtn.setOnAction(e -> SceneManager.getInstance().navigateToPortfolio());

            heroButtons.getChildren().addAll(startBtn, portfolioBtn);
            heroButtons.setSpacing(20);
        }
    }

    private void setupServicesSection() {
        if (servicesGrid == null)
            return;

        servicesGrid.getChildren().clear();

        String[][] services = {
                { "🎨", "UI/UX Design",
                        "Crafting intuitive and beautiful user experiences that delight users and drive engagement." },
                { "💻", "Full-Stack Development",
                        "Building robust web and mobile applications with cutting-edge technologies." },
                { "🗄️", "Database Engineering",
                        "Designing scalable database architectures that power your applications efficiently." },
                { "📊", "Technical Consulting",
                        "Strategic technology guidance to help your business make informed decisions." },
                { "🔧", "24/7 Support", "Round-the-clock technical support to keep your systems running smoothly." }
        };

        for (String[] service : services) {
            VBox card = createServiceCard(service[0], service[1], service[2]);
            servicesGrid.getChildren().add(card);
        }
    }

    private VBox createServiceCard(String icon, String title, String description) {
        VBox card = new VBox(15);
        card.getStyleClass().add("service-card");
        card.setPadding(new Insets(30));
        card.setAlignment(Pos.TOP_LEFT);
        card.setPrefWidth(320);
        card.setPrefHeight(220);

        Label iconLabel = new Label(icon);
        iconLabel.getStyleClass().add("service-icon");

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("service-title");

        Label descLabel = new Label(description);
        descLabel.getStyleClass().add("service-description");
        descLabel.setWrapText(true);

        card.getChildren().addAll(iconLabel, titleLabel, descLabel);

        // Hover effect
        card.setOnMouseEntered(e -> card.getStyleClass().add("service-card-hover"));
        card.setOnMouseExited(e -> card.getStyleClass().remove("service-card-hover"));

        return card;
    }

    private void setupStatsSection() {
        if (statsSection == null)
            return;

        statsSection.getChildren().clear();

        String[][] stats = {
                { "150+", "Projects Delivered" },
                { "50+", "Happy Clients" },
                { "10+", "Years Experience" },
                { "99%", "Client Satisfaction" }
        };

        for (String[] stat : stats) {
            VBox statBox = createStatBox(stat[0], stat[1]);
            statsSection.getChildren().add(statBox);
        }

        statsSection.setAlignment(Pos.CENTER);
        statsSection.setSpacing(80);
    }

    private VBox createStatBox(String number, String label) {
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);

        Label numLabel = new Label(number);
        numLabel.getStyleClass().add("stat-number");

        Label textLabel = new Label(label);
        textLabel.getStyleClass().add("stat-label");

        box.getChildren().addAll(numLabel, textLabel);
        return box;
    }

    private void setupCtaSection() {
        if (ctaSection == null)
            return;

        ctaSection.getChildren().clear();
        ctaSection.setAlignment(Pos.CENTER);
        ctaSection.setSpacing(20);
        ctaSection.getStyleClass().add("cta-section");
        ctaSection.setPadding(new Insets(60, 40, 60, 40));

        Label ctaTitle = new Label("Ready to Build Something Amazing?");
        ctaTitle.getStyleClass().add("cta-title");

        Label ctaSubtitle = new Label("Let's turn your vision into reality. Contact us today to start your project.");
        ctaSubtitle.getStyleClass().add("cta-subtitle");

        Button ctaButton = new Button("Get Started Now");
        ctaButton.getStyleClass().addAll("btn", "btn-primary", "btn-large");
        ctaButton.setOnAction(e -> handleStartProject());

        ctaSection.getChildren().addAll(ctaTitle, ctaSubtitle, ctaButton);
    }
}
