module org.example.techhive_studio_website_project_final {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    // New dependencies for Firebase and SQLite
    requires com.google.gson;
    requires java.sql;
    requires java.desktop;
    requires firebase.admin;

    opens org.example.techhive_studio_website_project_final to javafx.fxml, com.google.gson;
    opens org.example.techhive_studio_website_project_final.controller to javafx.fxml;
    opens org.example.techhive_studio_website_project_final.model to com.google.gson;
    opens org.example.techhive_studio_website_project_final.data to javafx.fxml;
    opens org.example.techhive_studio_website_project_final.service to javafx.fxml;

    exports org.example.techhive_studio_website_project_final;
    exports org.example.techhive_studio_website_project_final.model;
    exports org.example.techhive_studio_website_project_final.data;
    exports org.example.techhive_studio_website_project_final.service;
}