# Vaadin Integration in Spring PetClinic

This project is a modified version of the [Spring PetClinic](https://github.com/spring-projects/spring-petclinic), demonstrating how to add Vaadin to a running project while preserving existing backend with business logic.

## Prerequisites
- JDK 17 or higher
- Maven 3.6 or higher
- Spring Boot 3.x

## Integration Steps

### 1. Define Vaadin Version
Add a property for managing the Vaadin version in `pom.xml`:

```xml
<properties>
    <vaadin.version>24.6.1</vaadin.version>
</properties>
```

### 2. Set Spring Boot Starter Parent
Ensure `spring-boot-starter-parent` is set as the parent in your `pom.xml` and update the version appropriately:

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.4.1</version>
    <relativePath />
</parent>
```

### 3. Add Vaadin Dependencies
Include the required dependencies for Vaadin in your `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>com.vaadin</groupId>
        <artifactId>vaadin</artifactId>
    </dependency>
    <dependency>
        <groupId>com.vaadin</groupId>
        <artifactId>vaadin-spring-boot-starter</artifactId>
    </dependency>
</dependencies>
```

### 4. Add Vaadin Maven Plugins
Add the Vaadin Maven plugin to the `build` section for managing frontend resources:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.vaadin</groupId>
            <artifactId>vaadin-maven-plugin</artifactId>
            <version>${vaadin.version}</version>
            <executions>
                <execution>
                    <goals>
                        <goal>prepare-frontend</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

### 5. Add Production Build Profile
Add a Maven profile for production builds:

```xml
<profiles>
    <profile>
        <id>production</id>
        <build>
            <plugins>
                <plugin>
                    <groupId>com.vaadin</groupId>
                    <artifactId>vaadin-maven-plugin</artifactId>
                    <configuration>
                        <productionMode>true</productionMode>
                    </configuration>
                </plugin>
            </plugins>
        </build>
    </profile>
</profiles>
```

### 6. Update Welcome Page
Refactor the Thymeleaf-based welcome page to default to the Vaadin-based UI by updating routing configurations. Replace the Thymeleaf default view with the Vaadin view class.

```java
@Controller
class WelcomeController {
    @GetMapping("/") //add "welcome" to keep welcome page available
    public String welcome() {
        return "welcome";
    }
}
```

### 7. Implement Vaadin View
Create a new package `org.springframework.samples.petclinic.ui` and implement a Vaadin view that replaces the welcome page:

```java
@Route("")
@RouteAlias("vets")
public class VetView extends VerticalLayout {
    public VetView(VetRepository vetRepository) {
        var grid = new Grid<>(Vet.class);
        grid.setItems(vetRepository.findAll());
        grid.setColumns("firstName", "lastName", "specialties");
        grid.setSizeFull();
        add(grid);

        setSizeFull();
    }
}
```

### 8. Packaging
Ensure the `pom.xml` is configured to use `jar` packaging:

```xml
<packaging>jar</packaging>
```

## Build and Run

### Development Mode
To run the application in development mode:

```bash
mvn spring-boot:run
```

Access the app at `http://localhost:8080`.

### Production Build
For a production-ready package, build the project with the `production` profile:

```bash
mvn clean package -Pproduction
```

---

This modified project integrates Vaadin into the Spring PetClinic while maintaining the application's core functionalities. Let us know if additional steps or refinements are required!
