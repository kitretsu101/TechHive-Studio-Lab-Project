package org.example.techhive_studio_website_project_final.data;

import org.example.techhive_studio_website_project_final.model.Engineer;
import org.example.techhive_studio_website_project_final.model.Project;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataStore {
    private static DataStore instance;
    private List<Engineer> engineers;
    private List<Project> projects;

    private DataStore() {
        engineers = new ArrayList<>();
        projects = new ArrayList<>();
        loadDummyData();
    }

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    private void loadDummyData() {
        // Engineers
        engineers.add(new Engineer("1", "Plabon Barua", "Fullstack Engineer", "Expert in Java and React.",
                "Plabon.jpg", Arrays.asList("Java", "React", "AWS")));
        engineers.add(new Engineer("2", "Hirobi Chakma", "UI/UX Designer", "Passionate about user-centric design.",
                "Hirobi.jpeg", Arrays.asList("Figma", "CSS", "Prototyping")));
        engineers.add(new Engineer("3", "Utsa Roy", "Backend Engineer", "Scalable systems architect.",
                "utsa.jpeg", Arrays.asList("Spring Boot", "Kafka", "Docker")));
        engineers.add(new Engineer("4", "Tahmid Khan Mahin", "DevOps Engineer", "Automating everything.",
                "tahmid.jpeg", Arrays.asList("Kubernetes", "CI/CD", "Terraform")));
        engineers.add(new Engineer("5", "Sabbir Rahman", "Frontend Engineer", "Pixel perfectionist.",
                "https://via.placeholder.com/150", Arrays.asList("React", "TypeScript", "Tailwind")));
        engineers.add(new Engineer("6", "Fatiha Nazat", "Product Manager", "Leading the vision.",
                "nazat.jpeg", Arrays.asList("Agile", "Scrum", "Roadmap")));

        // Projects
        projects.add(new Project("1", "Nova eCommerce Platform", "A high-performance eCommerce solution.",
                "Legacy system was slow.", "Microservices architecture.", "50% faster checkout.",
                Arrays.asList("Java", "Spring", "React"), "https://via.placeholder.com/300"));
        projects.add(new Project("2", "Pulse Analytics Dashboard", "Real-time data visualization.", "Data silos.",
                "Unified data lake.", "Real-time insights.", Arrays.asList("Python", "D3.js", "AWS"),
                "https://via.placeholder.com/300"));
        projects.add(new Project("3", "Atlas Booking App", "Seamless travel booking.", "Complex UX.",
                "Simplified flow.", "20% higher conversion.", Arrays.asList("Flutter", "Firebase"),
                "https://via.placeholder.com/300"));
        projects.add(new Project("4", "Beacon CMS Migration", "Enterprise CMS upgrade.", "Security vulnerabilities.",
                "Headless CMS.", "Secure and fast.", Arrays.asList("Node.js", "Next.js", "GraphQL"),
                "https://via.placeholder.com/300"));
    }

    public List<Engineer> getEngineers() {
        return engineers;
    }

    public List<Project> getProjects() {
        return projects;
    }
}
