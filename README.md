# TechHive Studio - JavaFX Desktop Application

A premium JavaFX desktop application that replicates the TechHive Studio website with a stunning dark theme, neon blue-purple accents, and agency-grade UI quality.

## 🚀 Features

- **Dark Theme**: Deep navy/black gradient backgrounds
- **Neon Accents**: Blue (#00d4ff) and purple (#8b5cf6) glow effects
- **Smooth Animations**: Hover effects on cards and buttons
- **4 Complete Screens**:
  - Home Page (Hero, Services, Stats, CTA)
  - Our Engineers (Filter by role, Card grid)
  - Engineer Profile (Detailed view with skills)
  - Portfolio (Case-study style project cards)

## 📋 Requirements

- **Java 17+** (Java 17, 18, 19, 20, or 21)
- **IntelliJ IDEA** (recommended)

## 🏃 Running the Application

### Option 1: IntelliJ IDEA (Recommended)

1. Open IntelliJ IDEA
2. **File → Open** → Select the `TechHive Studio website` folder
3. Wait for Gradle to sync (may take a minute on first load)
4. Navigate to `src/main/java/com/techhive/Launcher.java`
5. Right-click `Launcher.java` → **Run 'Launcher.main()'**

### Option 2: Command Line

```powershell
cd "c:\Users\DFIT\OneDrive\Desktop\TechHive Studio website"
.\gradlew run
```

## 📁 Project Structure

```
TechHive Studio website/
├── build.gradle              # Gradle build config with JavaFX 21
├── settings.gradle           # Project name
├── README.md                 # This file
└── src/main/
    ├── java/com/techhive/
    │   ├── Launcher.java     # Entry point (run this!)
    │   ├── TechHiveApp.java  # Main JavaFX application
    │   ├── controller/       # Page controllers
    │   │   ├── HomeController.java
    │   │   ├── EngineersController.java
    │   │   ├── EngineerProfileController.java
    │   │   └── PortfolioController.java
    │   ├── model/            # Data models
    │   │   ├── Engineer.java
    │   │   └── Project.java
    │   ├── data/             # Static data (ready for SQLite)
    │   │   └── DataProvider.java
    │   └── util/             # Utilities
    │       └── SceneManager.java
    └── resources/
        ├── fxml/             # FXML layouts
        │   ├── home.fxml
        │   ├── engineers.fxml
        │   ├── engineer-profile.fxml
        │   └── portfolio.fxml
        └── css/              # Stylesheets
            └── styles.css
```

## 🎨 UI Overview

### Home Page
- Navigation bar with logo and menu items
- Hero section with gradient text
- Services cards with hover glow effects
- Statistics section
- Call-to-action banner

### Our Engineers
- Filter buttons (All, Frontend, Backend, Fullstack, DevOps, Designer)
- Responsive grid of engineer cards
- Click card to view profile

### Engineer Profile
- Large profile avatar with initials
- Name, role, and bio
- Skills displayed as tags
- Featured contributions section

### Portfolio
- Case-study style layout
- Problem/Solution/Outcome sections
- Tech stack badges
- Alternating image placement

## 🔮 Future Enhancements

- **SQLite Database**: Replace static data in `DataProvider.java`
- **Real Images**: Add engineer and project images to `/resources/images/`
- **Contact Form**: Implement a working contact form
- **Animations**: Add entrance animations using Timeline

## 🛠️ Built With

- **Java 17+**
- **JavaFX 21**
- **FXML** for layouts
- **CSS** for styling
- **Gradle** for build management
- **MVC Architecture**

## 📄 License

© 2024 TechHive Studio. All rights reserved.
