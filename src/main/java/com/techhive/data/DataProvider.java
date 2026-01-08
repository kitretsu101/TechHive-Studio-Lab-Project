package com.techhive.data;

import com.techhive.model.Engineer;
import com.techhive.model.Project;

import java.util.Arrays;
import java.util.List;

/**
 * Static data provider for engineers and projects.
 * Replace with SQLite database queries in the future.
 */
public class DataProvider {

    private static List<Engineer> engineers;
    private static List<Project> projects;

    static {
        initializeEngineers();
        initializeProjects();
    }

    private static void initializeEngineers() {
        engineers = Arrays.asList(
                new Engineer(1, "Sarah Chen", "Senior Frontend Engineer",
                        "Sarah is a passionate frontend developer with 7+ years of experience building beautiful, responsive web applications. She specializes in React, Vue.js, and modern CSS frameworks. Her focus on user experience and attention to detail makes her an invaluable asset to any project.",
                        "/images/engineers/sarah.png",
                        Arrays.asList("React", "Vue.js", "TypeScript", "Tailwind CSS", "Next.js", "Figma")),

                new Engineer(2, "Marcus Johnson", "Lead Backend Engineer",
                        "Marcus brings 10+ years of backend development expertise to the team. He architects scalable microservices and has deep knowledge of cloud infrastructure. His solutions power applications serving millions of users worldwide.",
                        "/images/engineers/marcus.png",
                        Arrays.asList("Java", "Spring Boot", "Python", "PostgreSQL", "AWS", "Docker", "Kubernetes")),

                new Engineer(3, "Elena Rodriguez", "Fullstack Developer",
                        "Elena is a versatile fullstack developer who bridges the gap between frontend and backend seamlessly. With expertise in both React and Node.js, she delivers end-to-end solutions that are both elegant and performant.",
                        "/images/engineers/elena.png",
                        Arrays.asList("React", "Node.js", "MongoDB", "GraphQL", "TypeScript", "Redis")),

                new Engineer(4, "David Kim", "DevOps Engineer",
                        "David ensures our infrastructure runs smoothly 24/7. He specializes in CI/CD pipelines, container orchestration, and cloud architecture. His automation scripts have saved countless hours of manual work.",
                        "/images/engineers/david.png",
                        Arrays.asList("Docker", "Kubernetes", "Terraform", "Jenkins", "AWS", "Azure", "Linux")),

                new Engineer(5, "Aisha Patel", "Senior UI/UX Designer",
                        "Aisha creates stunning visual designs that users love. With a background in psychology and design, she crafts experiences that are both beautiful and intuitive. Her designs have won multiple industry awards.",
                        "/images/engineers/aisha.png",
                        Arrays.asList("Figma", "Adobe XD", "Sketch", "Prototyping", "User Research", "Design Systems")),

                new Engineer(6, "James Thompson", "Senior Fullstack Engineer",
                        "James is a veteran developer with expertise across the entire stack. He leads technical decisions and mentors junior developers. His code is known for being clean, maintainable, and well-documented.",
                        "/images/engineers/james.png",
                        Arrays.asList("Python", "Django", "React", "PostgreSQL", "Redis", "AWS", "System Design")));
    }

    private static void initializeProjects() {
        projects = Arrays.asList(
                new Project(1, "Nova eCommerce Platform",
                        "A growing retail brand needed a modern eCommerce platform to handle increasing traffic and provide a seamless shopping experience across all devices.",
                        "We built a custom headless eCommerce solution using Next.js and a microservices backend. The platform features real-time inventory management, AI-powered recommendations, and a lightning-fast checkout process.",
                        "300% increase in conversion rate, 50% reduction in page load times, and seamless handling of 10x traffic during peak sales.",
                        Arrays.asList("Next.js", "Node.js", "PostgreSQL", "Redis", "Stripe", "AWS"),
                        "/images/projects/nova.png",
                        "E-Commerce"),

                new Project(2, "Pulse Analytics Dashboard",
                        "A healthcare startup needed a real-time analytics dashboard to help medical professionals monitor patient data and identify trends quickly.",
                        "We developed an interactive dashboard with real-time data visualization, custom reporting tools, and ML-powered anomaly detection. The interface was designed for quick decision-making in high-pressure environments.",
                        "Reduced data analysis time by 75%, enabled early detection of critical conditions, and improved overall patient outcomes by 40%.",
                        Arrays.asList("React", "D3.js", "Python", "TensorFlow", "PostgreSQL", "Docker"),
                        "/images/projects/pulse.png",
                        "Healthcare"),

                new Project(3, "Atlas Booking App",
                        "A travel agency wanted to modernize their booking system and provide customers with a mobile-first experience for planning trips.",
                        "We created a cross-platform mobile app with intuitive trip planning features, real-time availability checking, and integrated payment processing. The app includes offline functionality for travelers.",
                        "200% increase in mobile bookings, 4.8-star app store rating, and 60% reduction in customer support calls.",
                        Arrays.asList("React Native", "Node.js", "MongoDB", "Stripe", "Google Maps API", "Firebase"),
                        "/images/projects/atlas.png",
                        "Travel"),

                new Project(4, "Beacon CMS Migration",
                        "A media company with 15 years of content needed to migrate from a legacy CMS to a modern, scalable content management system without any downtime.",
                        "We executed a zero-downtime migration strategy, moving 500,000+ articles to a headless CMS. We built custom migration tools, implemented content versioning, and created a new editorial workflow.",
                        "Zero downtime during migration, 80% improvement in content publishing speed, and 50% reduction in infrastructure costs.",
                        Arrays.asList("Strapi", "Next.js", "PostgreSQL", "Elasticsearch", "AWS S3", "CloudFront"),
                        "/images/projects/beacon.png",
                        "Media"));
    }

    public static List<Engineer> getAllEngineers() {
        return engineers;
    }

    public static List<Engineer> getEngineersByCategory(String category) {
        if (category == null || category.equals("All")) {
            return engineers;
        }
        return engineers.stream()
                .filter(e -> e.getCategory().equals(category))
                .toList();
    }

    public static Engineer getEngineerById(int id) {
        return engineers.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static List<Project> getAllProjects() {
        return projects;
    }

    public static Project getProjectById(int id) {
        return projects.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
