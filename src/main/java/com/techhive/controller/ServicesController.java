package com.techhive.controller;

import com.techhive.util.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ServicesController implements Initializable {

    @FXML
    private Label logoLabel;
    @FXML
    private HBox navLinks;
    @FXML
    private Button startProjectBtn;
    @FXML
    private VBox servicesContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupNavigation();
        setupServicesList();
    }

    private void setupNavigation() {
        logoLabel.setOnMouseClicked(e -> SceneManager.getInstance().navigateToHome());

        String[] navItems = { "Home", "Services", "Portfolio", "Our Engineers", "Contribute", "Contact" };
        navLinks.getChildren().clear();

        for (String item : navItems) {
            Label link = new Label(item);
            link.getStyleClass().add("nav-link");
            if (item.equals("Services")) {
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

    private void setupServicesList() {
        if (servicesContainer == null)
            return;
        servicesContainer.getChildren().clear();
        servicesContainer.setSpacing(30);

        String[][] allServices = {
                { "UI/UX Design", "User-Centric Interfaces",
                        "We start with the user. Our design team creates intuitive, accessible, and visually stunning interfaces that drive engagement and satisfaction.",
                        "Figma • Adobe XD • Prototyping" },
                { "Full-Stack Development", "End-to-End Solutions",
                        "From database design to frontend interactivity, we build robust, scalable web  applications using the latest frameworks and best practices.",
                        "Java • React • Node.js" },
                { "Mobile App Development", "iOS & Android",
                        "Native and cross-platform mobile solutions that provide seamless experiences on all devices.",
                        "Swift • Kotlin • React Native" },
                { "Cloud Architecture", "Scalable Infrastructure",
                        "We design and deploy secure, auto-scaling cloud infrastructure to ensure your application can grow without limits.",
                        "AWS • Azure • Docker • K8s" },
                { "DevOps & CI/CD", "Streamlined Delivery",
                        "Automated pipelines and infrastructure as code to speed up deployment and increase reliability.",
                        "Jenkins • GitHub Actions • Terraform" }
        };

        for (String[] svc : allServices) {
            servicesContainer.getChildren().add(createServiceDetailCard(svc[0], svc[1], svc[2], svc[3]));
        }
    }

    private VBox createServiceDetailCard(String title, String subtitle, String description, String tech) {
        VBox card = new VBox(15);
        card.getStyleClass().add("service-card-detail"); // We will add this class to CSS
        card.setPadding(new javafx.geometry.Insets(30));

        Label titleLbl = new Label(title);
        titleLbl.getStyleClass().add("service-detail-title");

        Label subLbl = new Label(subtitle);
        subLbl.getStyleClass().add("service-detail-subtitle");

        Label descLbl = new Label(description);
        descLbl.getStyleClass().add("service-detail-desc");
        descLbl.setWrapText(true);

        Label techLbl = new Label(tech);
        techLbl.getStyleClass().add("service-detail-tech");

        card.getChildren().addAll(titleLbl, subLbl, descLbl, techLbl);
        return card;
    }
}
