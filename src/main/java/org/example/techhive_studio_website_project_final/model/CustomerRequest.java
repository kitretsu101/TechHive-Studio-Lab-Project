package org.example.techhive_studio_website_project_final.model;

import javafx.beans.property.*;

/**
 * Model class representing a customer project request.
 * Designed for TableView binding and SQLite-ready structure.
 */
public class CustomerRequest {
    private final IntegerProperty id;
    private final StringProperty customerName;
    private final StringProperty email;
    private final StringProperty projectDescription;
    private final StringProperty serviceType;
    private final StringProperty status; // "Pending", "Accepted", "Rejected"

    public CustomerRequest(int id, String customerName, String email,
            String projectDescription, String serviceType) {
        this.id = new SimpleIntegerProperty(id);
        this.customerName = new SimpleStringProperty(customerName);
        this.email = new SimpleStringProperty(email);
        this.projectDescription = new SimpleStringProperty(projectDescription);
        this.serviceType = new SimpleStringProperty(serviceType);
        this.status = new SimpleStringProperty("Pending");
    }

    public CustomerRequest(int id, String customerName, String email,
            String projectDescription, String serviceType, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.customerName = new SimpleStringProperty(customerName);
        this.email = new SimpleStringProperty(email);
        this.projectDescription = new SimpleStringProperty(projectDescription);
        this.serviceType = new SimpleStringProperty(serviceType);
        this.status = new SimpleStringProperty(status);
    }

    // ID
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    // Customer Name
    public String getCustomerName() {
        return customerName.get();
    }

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String name) {
        this.customerName.set(name);
    }

    // Email
    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    // Project Description
    public String getProjectDescription() {
        return projectDescription.get();
    }

    public StringProperty projectDescriptionProperty() {
        return projectDescription;
    }

    public void setProjectDescription(String desc) {
        this.projectDescription.set(desc);
    }

    // Service Type
    public String getServiceType() {
        return serviceType.get();
    }

    public StringProperty serviceTypeProperty() {
        return serviceType;
    }

    public void setServiceType(String type) {
        this.serviceType.set(type);
    }

    // Status
    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    @Override
    public String toString() {
        return "CustomerRequest{" +
                "id=" + getId() +
                ", customerName='" + getCustomerName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
