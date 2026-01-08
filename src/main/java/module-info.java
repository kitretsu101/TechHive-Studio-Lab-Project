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

    opens org.example.techhive_studio_website_project_final to javafx.fxml;
    opens org.example.techhive_studio_website_project_final.controller to javafx.fxml;

    exports org.example.techhive_studio_website_project_final;
}