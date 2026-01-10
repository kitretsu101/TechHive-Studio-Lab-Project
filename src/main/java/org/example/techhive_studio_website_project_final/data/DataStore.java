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
                // Engineers - CEO & Founder
                engineers.add(new Engineer("1", "Plabon Barua", "CEO & Founder",
                                "Visionary leader with 10+ years in full-stack development. Expert in Java, React, and cloud architecture. Passionate about building scalable solutions that transform businesses.",
                                "Plabon.jpg", Arrays.asList("Java", "React", "AWS", "System Design", "Leadership"),
                                true, true));

                // Founding Members
                engineers.add(new Engineer("2", "Hirobi Chakma", "Lead UI/UX Designer",
                                "Creative designer passionate about user-centric interfaces. Specializes in design systems and accessibility.",
                                "Hirobi.jpeg", Arrays.asList("Figma", "CSS", "Prototyping", "Design Systems"), false,
                                true));
                engineers.add(new Engineer("3", "Utsa Roy", "Senior Backend Engineer",
                                "Scalable systems architect with expertise in microservices and distributed systems.",
                                "utsa.jpeg", Arrays.asList("Spring Boot", "Kafka", "Docker", "PostgreSQL"), false,
                                true));
                engineers.add(new Engineer("4", "Tahmid Khan Mahin", "DevOps Lead",
                                "Infrastructure automation specialist. Building reliable CI/CD pipelines and cloud infrastructure.",
                                "tahmid.jpeg", Arrays.asList("Kubernetes", "CI/CD", "Terraform", "AWS"), false, true));
                engineers.add(new Engineer("5", "Fatiha Nazat", "Product Manager",
                                "Strategic product leader driving vision and execution. Expert in Agile methodologies.",
                                "nazat.jpeg", Arrays.asList("Agile", "Scrum", "Roadmap", "Analytics"), false, true));

                // Team Members
                engineers.add(new Engineer("6", "Sabbir Rahman", "Frontend Engineer",
                                "Pixel perfectionist crafting beautiful, responsive user interfaces.",
                                "https://via.placeholder.com/150", Arrays.asList("React", "TypeScript", "Tailwind"),
                                false, false));

                // Projects
                projects.add(new Project("1", "Daraz eCommerce Platform", "A high-performance eCommerce solution.",
                                "Legacy system was slow.", "Microservices architecture.", "50% faster checkout.",
                                Arrays.asList("Java", "Spring", "React"), "https://via.placeholder.com/300"));
                projects.add(new Project("2", "Pulse Analytics Dashboard", "Real-time data visualization.",
                                "Data silos.",
                                "Unified data lake.", "Real-time insights.", Arrays.asList("Python", "D3.js", "AWS"),
                                "https://via.placeholder.com/300"));
                projects.add(new Project("3", "KUET Library  Booking App", "Seamless travel booking.", "Complex UX.",
                                "Simplified flow.", "20% higher conversion.", Arrays.asList("Flutter", "Firebase"),
                                "https://via.placeholder.com/300"));
                projects.add(new Project("4", "KUET academic System", "Upgraded educational platform",
                                "Security vulnerabilities.",
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
