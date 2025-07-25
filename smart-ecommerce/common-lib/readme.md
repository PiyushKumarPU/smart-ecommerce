### Building and Using the Common Library Locally via Gradle and Maven

This README provides step-by-step instructions to build the `common` library locally, publish it to a local Maven repository, and use it in other projects either via **Gradle** or **Maven**. Additionally, it includes instructions for ensuring Spring Boot scans the `common` library components.

---

### **1. Prerequisites**
Before proceeding, ensure you have the following installed on your system:
- **Java 24**
- **Maven 3.6+** (if you want to build or use the library in a Maven project)
- A local Maven repository (usually located at `~/.m2/repository`)

---

### **2. Build and Publish the Common Library Locally**

#### **Using Maven**
If you prefer to build and publish the library using Maven, follow these steps:

1. **Build and Install the Library**  
   Run the following Maven command to build and publish the library to your local Maven repository:
   ```bash
   mvn clean install
   ```
   This will:
   - Compile the code.
   - Run tests.
   - Package the library as a JAR.
   - Publish the library to your local Maven repository (`~/.m2/repository`).

2. **Verify the Published Artifact**  
   After running the command, verify that the library has been published to your local Maven repository:
   ```
   ~/.m2/repository/com/instaprepsai/common/1.0/
   ```

---

### **3. Use the Common Library in a Maven Project**
To use the `common` library in a Maven project, follow these steps:

1. **Add the Dependency in `pom.xml`**  
   Add the following dependency to the `dependencies` section of the consuming project’s `pom.xml` file:
   ```xml
   <dependency>
       <groupId>com.instaprepsai</groupId>
       <artifactId>common</artifactId>
       <version>1.0</version>
   </dependency>
   ```

2. **Build the Project**  
   Run the following Maven command to build the project and resolve the dependency:
   ```bash
   mvn clean install
   ```

---

### **4. Ensure Spring Boot Scans the Common Library Components**
If the `common` library contains Spring components (e.g., `@ControllerAdvice`, `@Service`, `@Component`), you need to ensure that Spring Boot scans the package where these components are located.

1. **Add `@ComponentScan` in the Main Application Class**  
   In the consuming project, update the main application class to include the package of the `common` library in the `@ComponentScan` annotation:
   ```java
   @SpringBootApplication
   @ComponentScan(basePackages = {"com.instaprepsai.common", "com.yourproject"})
   public class YourApplication {
       public static void main(String[] args) {
           SpringApplication.run(YourApplication.class, args);
       }
   }
   ```

   - Add any additional packages from your project as needed.

2. **Verify Component Scanning**  
   Ensure that Spring Boot detects the components from the `common` library. For example:
   - If you have a `@ControllerAdvice` class like `GlobalExceptionHandler`, it should automatically handle exceptions in the consuming project.

---

### **6. Notes**
- **Version Management**: If you update the `common` library, you’ll need to rebuild and republish it using either Gradle (`publishToMavenLocal`) or Maven (`mvn clean install`) for the changes to take effect in consuming projects.
- **Central Repository**: If you want to share the library across teams, consider publishing it to a remote Maven repository (e.g., Nexus, Artifactory, or Maven Central) instead of relying on `mavenLocal`.
- **Dependency Conflicts**: If there are version conflicts with other dependencies, use Gradle’s or Maven’s dependency resolution tools to debug.

---
