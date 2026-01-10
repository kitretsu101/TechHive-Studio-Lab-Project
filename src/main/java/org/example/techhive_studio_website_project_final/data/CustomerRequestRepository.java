package org.example.techhive_studio_website_project_final.data;

import org.example.techhive_studio_website_project_final.model.CustomerRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * In-memory data store for customer requests.
 * Designed for easy SQLite replacement later.
 */
public class CustomerRequestRepository {
    private static CustomerRequestRepository instance;
    private final List<CustomerRequest> requests;
    private int nextId = 1;

    private CustomerRequestRepository() {
        requests = new ArrayList<>();
        loadSampleData();
    }

    public static CustomerRequestRepository getInstance() {
        if (instance == null) {
            instance = new CustomerRequestRepository();
        }
        return instance;
    }

    /**
     * Load sample data for testing.
     */
    private void loadSampleData() {
        requests.add(new CustomerRequest(nextId++, "John Smith", "john@example.com",
                "E-commerce platform with payment integration and inventory management",
                "Web Development"));

        requests.add(new CustomerRequest(nextId++, "Sarah Johnson", "sarah@company.co",
                "Cross-platform mobile app for fitness tracking with wearable integration",
                "Mobile App"));

        requests.add(new CustomerRequest(nextId++, "Tech Solutions Ltd", "contact@techsolutions.io",
                "Complete redesign of our SaaS dashboard with modern UX",
                "UI/UX Design"));

        requests.add(new CustomerRequest(nextId++, "Global Logistics Inc", "projects@globallogistics.com",
                "Cloud migration for legacy systems with CI/CD pipeline setup",
                "Cloud Infrastructure"));

        requests.add(new CustomerRequest(nextId++, "StartupXYZ", "founder@startupxyz.com",
                "Technical consultation for MVP development and architecture planning",
                "Consulting"));

        requests.add(new CustomerRequest(nextId++, "MediCare Hospital", "it@medicare.org",
                "Patient management system with appointment scheduling",
                "Web Development", "Accepted"));

        requests.add(new CustomerRequest(nextId++, "Fashion Boutique", "orders@fashionboutique.com",
                "Online store with AR try-on feature",
                "Mobile App", "Rejected"));
    }

    /**
     * Get all customer requests.
     */
    public List<CustomerRequest> findAll() {
        return new ArrayList<>(requests);
    }

    /**
     * Get requests by status.
     */
    public List<CustomerRequest> findByStatus(String status) {
        return requests.stream()
                .filter(r -> r.getStatus().equalsIgnoreCase(status))
                .toList();
    }

    /**
     * Find request by ID.
     */
    public Optional<CustomerRequest> findById(int id) {
        return requests.stream()
                .filter(r -> r.getId() == id)
                .findFirst();
    }

    /**
     * Add a new customer request.
     * 
     * @return the generated ID
     */
    public int save(CustomerRequest request) {
        request.setId(nextId++);
        requests.add(request);
        return request.getId();
    }

    /**
     * Update request status.
     */
    public boolean updateStatus(int id, String status) {
        Optional<CustomerRequest> request = findById(id);
        if (request.isPresent()) {
            request.get().setStatus(status);
            return true;
        }
        return false;
    }

    /**
     * Delete a request.
     */
    public boolean delete(int id) {
        return requests.removeIf(r -> r.getId() == id);
    }

    /**
     * Get count of pending requests.
     */
    public long getPendingCount() {
        return requests.stream()
                .filter(r -> "Pending".equals(r.getStatus()))
                .count();
    }

    /**
     * Get count of accepted requests.
     */
    public long getAcceptedCount() {
        return requests.stream()
                .filter(r -> "Accepted".equals(r.getStatus()))
                .count();
    }

    /**
     * Get count of rejected requests.
     */
    public long getRejectedCount() {
        return requests.stream()
                .filter(r -> "Rejected".equals(r.getStatus()))
                .count();
    }
}
