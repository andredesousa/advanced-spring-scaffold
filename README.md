# 🚀 Advanced Spring Scaffold

The [Spring Framework](https://spring.io/) is an application framework and inversion of control container for the **Java platform**.
This project was generated with [Spring Initializr](https://start.spring.io/).
It has a complete **RESTful API** configured, including build, test, and deploy scripts as examples.

## Table of Contents

- [Project structure](#project-structure)
- [Available gradle tasks](#available-gradle-tasks)
- [Development mode](#development-mode)
- [REST interface](#rest-interface)
- [Data persistence](#data-persistence)
- [Authentication and authorization](#authentication-and-authorization)
- [Internationalization](#internationalization)
- [Healthchecks and logging](#healthchecks-and-logging)
- [Error handling](#error-handling)
- [Linting and formatting code](#linting-and-formatting-code)
- [Running unit tests](#running-unit-tests)
- [Running integration tests](#running-integration-tests)
- [Running smoke tests](#running-smoke-tests)
- [Debugging](#debugging)
- [Profiling](#profiling)
- [Security, performance and best practices](#security-performance-and-best-practices)
- [Commit messages convention](#commit-messages-convention)
- [Build and deployment](#build-and-deployment)
- [Reference documentation](#reference-documentation)

## Project structure

When working in a large team with many developers that are responsible for the same codebase, having a common understanding of how the application should be structured is vital.
Based on best practices from the community, Spring, other github projects and developer experience, your project should look like this:

```html
├── cicd
|  ├── build
|  └── deploy
├── gradle
├── src
|  ├── integrationTest
|  ├── main
|  |  ├── java
|  |  |  └── app
|  |  |    ├── config
|  |  |    ├── controller
|  |  |    ├── dto
|  |  |    ├── entity
|  |  |    ├── filter
|  |  |    ├── mapper
|  |  |    ├── repository
|  |  |    ├── service
|  |  |    └── Application.java
|  |  └── resources
|  |    ├── db
|  |    ├── i18n
|  |    ├── application-prod.properties
|  |    └── application.properties
|  ├── smokeTest
|  └── test
├── .editorconfig
├── .gitignore
├── .prettierrc
├── build.gradle
├── CHANGELOG.md
├── changelog.mustache
├── checkstyle.xml
├── gradle.properties
├── gradlew
├── gradlew.bat
├── LICENSE
├── lombok.config
├── README.md
└── settings.gradle
```

All of the app's code goes in a folder named `src/main`.
It is generally recommend that you locate your main application class in a root package above other classes.
The `@SpringBootApplication` annotation is often placed on your main class, and it implicitly defines a base "search package" for certain items.
It is recommended that you avoid using the default package, that is, you should always declare the package in your classes.

You can use the following naming convention for your packages:

- The various entities of the application are organized under the `entity` package.
- The data transfer objects (DTOs) are present under the `dto` package.
- The data access objects (DAOs) are present in the `repository` package.
- The service layer is defined under the `service` package.
- The controller layer is present in the `controller` package.
- The security settings and other configurations are present under the `config` package.
- Other common packages are `mapper`, `filter`, `exception`, `util`, etc.
- Static files are placed in `src/main/resources` folder.

The **unit tests**, **integration tests** and **smoke tests** are in the `src/test`, `src/integrationTest` and `src/smokeTest` folders.

## Available gradle tasks

The tasks in [build.gradle](build.gradle) file were built with simplicity in mind to automate as much repetitive tasks as possible and help developers focus on what really matters.

The next tasks should be executed in a console inside the root directory:

- `./gradlew tasks` - Displays the tasks runnable from root project 'app'.
- `./gradlew bootRun` - Runs this project as a Spring Boot application.
- `./gradlew check` - Runs all checks.
- `./gradlew test` - Runs the unit tests.
- `./gradlew integrationTest` - Run the integration tests.
- `./gradlew smokeTest` - Run the smoke tests.
- `./gradlew lint` - Runs several static code analysis.
- `./gradlew format` - Applies code formatting steps to source code in-place.
- `./gradlew sonarqube` - Analyzes root project 'app' and its subprojects with SonarQube.
- `./gradlew flywayInfo` - Prints the details and status information about all the migrations.
- `./gradlew flywayMigrate` - Migrates the schema to the latest version.
- `./gradlew clean` - Deletes the build directory.
- `./gradlew javadoc` - Generates Javadoc API documentation for the main source code.
- `./gradlew generateEntities` - Generates the entities sources.
- `./gradlew generateOpenApiDocs` - Generates the spring doc openapi file.
- `./gradlew generateChangelog` - Generates a changelog from GIT repository.
- `./gradlew dependencyCheckAnalyze` - Identifies and reports known vulnerabilities in project dependencies.
- `./gradlew dependencyUpdates` - Displays the dependency updates for the project.
- `./gradlew build` - Assembles and tests this project.
- `./gradlew bootJar` - Assembles an executable jar archive containing the main classes and their dependencies.
- `./gradlew bootBuildImage` - Builds an OCI image of the application using the output of the bootJar task.
- `./gradlew release` - Performs release, creates tag and pushes it to remote.
- `./gradlew deploy` - Deploys the application to Docker Swarm.
- `./gradlew help` - Displays a help message.

For more details, read the [Command-Line Interface](https://docs.gradle.org/current/userguide/command_line_interface.html) documentation in the [Gradle User Manual](https://docs.gradle.org/current/userguide/userguide.html).

## Development mode

[Spring Boot](https://spring.io/projects/spring-boot) makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run".
Use `./gradlew bootRun` to run this project as a Spring Boot application.
Note that the `application.properties` file uses environment variables defined in the `gradle.properties` file.

Beyond that, you can serve the app using continuous build which will automatically re-execute builds on file change.
Run `./gradlew classes -t` and `./gradlew bootRun` on different terminals to enable auto restart on file change.
However, this functionality is provided by plugins for your IDE.

## REST interface

REST is acronym for REpresentational State Transfer.
The common resource methods are GET, POST, PUT and DELETE.
This project follows [JSON:API](https://jsonapi.org/) specification for building APIs in JSON.
This project includes [Swagger](https://swagger.io/). To get a visual representation of the interface and send requests for testing purposes go to [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/).
The OpenAPI Specification is automatically generated. Run `./gradlew generateOpenApiDocs` to see the [openapi.json](build/docs/openapi.json) file.
Alternatively, [Postman](https://www.postman.com/) is currently one of the most popular tools used for API testing.

## Data persistence

This project uses a [PostgreSQL](https://www.postgresql.org/) database.
In local development, you need to provide a connection to a PostgreSQL instance. You can use the next example:

```bash
docker run -d -p 5432:5432 -e POSTGRES_DB=spring -e POSTGRES_USER=root -e POSTGRES_PASSWORD=secret postgres
```

The project supports database migrations via [Flyway](https://flywaydb.org/).
Migrations are most commonly written in SQL but Flyway supports Java-based migrations.
In order to be picked up by Flyway, migrations must comply with the required [naming](https://flywaydb.org/documentation/concepts/migrations#naming) pattern.
Use `./gradlew flywayMigrate` to up all migrations. You can use `./gradlew flywayInfo` to see the details and status information about all the migrations.
Use `./gradlew generateEntities` to generate the JPA entities. This task generates Lombok-wired JPA entity source code.
[Lombok](https://projectlombok.org/) provides several annotations designed to avoid writing Java code known to be repetitive and/or boilerplate.

Alternatively, you can use [jOOQ](https://www.jooq.org/) to generate Java code from your database and build type safe SQL queries through its fluent API.

## Authentication and authorization

Authentication is an essential part of most applications. There are many different approaches and strategies to handle authentication.

[JSON Web Tokens](https://jwt.io/) is an authentication standard that works by generating and signing tokens, passing them around between the client-side and server-side applications, passed around via query strings, authorization headers, or other mediums.
Having such a valid and non-expired token, extracted from an HTTP Request, signals the fact that the user is authenticated and is allowed to access protected resources.
The default user is `admin` and the password is `admin`. Use for development only.

Authorization refers to the process that determines what a user is able to do.
For example, an administrative user is allowed to create, edit, and delete posts.
A non-administrative user is only authorized to read the posts.

## Internationalization

Internationalization or i18n is a process that makes your application adaptable to different languages and regions without engineering changes on the source code.
You can display messages, currencies, date, time etc. according to the specific region or language.
Spring Boot application takes the message sources from `src/main/resources/i18n` folder under the classpath.
The default locale message file name is `messages.properties` and the files for each locale should named `messages_xx.properties`.

You can access your messages through `MessageSource`. See the next example:

```java
@RestController
public class Controller {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/404")
    public String notFound(Locale locale) {
        return messageSource.getMessage("http.status.404", null, locale);
    }
}
```

## Healthchecks and logging

Spring Boot supports you with readiness/liveness health checks via [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html).
Monitoring your app, gathering metrics, understanding traffic, or the state of your database is trivial with this dependency.
It is available at [http://localhost:8080/health](http://localhost:8080/health).
A service, or a component of your infrastructure (e.g., Kubernetes) checks this address continuously.
Depending on the HTTP status code returned from a GET request to this address the service will take action when it receives an "unhealthy" response.

Spring Boot uses [Apache Commons Logging](https://commons.apache.org/proper/commons-logging/) for all internal logging.
It is used during application bootstrapping and several other circumstances such as displaying caught exceptions (i.e., system logging).
The available Log Level are ERROR, WARN, INFO, DEBUG, or TRACE.

## Error handling

The REST API signals all error conditions by an HTTP response status of either `4xx` or `5xx`.

Errors that were caused by invalid client requests are mapped to a response status of `4xx`:

- Request objects that violate a constraint given in the API description will result in a `400 Bad Request`,
- Request that does not have valid authentication credentials for the target resource will result in a `401 Unauthorized`,
- Requests that refer to a non-existing object will result in a `404 Not Found`.

On the other hand, technical errors that were caused inside the app will result in a `500 Internal Server Error`

## Linting and formatting code

A linter is a static code analysis tool used to flag programming errors, bugs, stylistic errors and suspicious constructs.

It includes [Prettier](https://prettier.io/), [Checkstyle](https://checkstyle.sourceforge.io/), [PMD](https://pmd.github.io/) and [SpotBugs](https://spotbugs.github.io/):

- **Prettier** enforces a consistent style by parsing your code and re-printing it with its own rules, wrapping code when necessary.
- **Checkstyle** finds class design problems, method design problems, and others. It also has the ability to check code layout and formatting issues.
- **PMD** finds common programming flaws like unused variables, empty catch blocks, unnecessary object creation, and so forth.
- **SpotBugs** is used to perform static analysis on Java code. It looks for instances of "bug patterns".

Use `./gradlew lint` to analyze your code. Many problems can be automatically fixed with `./gradlew format` task.
Depending on our editor, you may want to add an editor extension to lint and format your code while you type or on-save.
These checks are supplemented with [SonarQube](https://www.sonarqube.org/) and other tools.

## Running unit tests

Use `./gradlew test` to execute the unit tests via [JUnit 5](https://junit.org/junit5/), [Mockito](https://site.mockito.org/) and [AssertJ](https://assertj.github.io/doc/).
Use `./gradlew test -t` to keep executing unit tests in real time while watching for file changes in the background.
You can see the HTML report opening the [index.html](build/reports/tests/test/index.html) file in your web browser.

It's a common requirement to run subsets of a test suite, such as when you're fixing a bug or developing a new test case.
Gradle provides different mechanisms.
For example, the following command lines run either all or exactly one of the tests in the `SomeTestClass` test case:

```bash
./gradlew test --tests SomeTestClass
```

For more details, you can see the [Test filtering](https://docs.gradle.org/current/userguide/java_testing.html#test_filtering) section of the Gradle documentation.

This project uses [JaCoCo](https://www.eclemma.org/jacoco/) which provides code coverage metrics for Java.
The minimum code coverage is set to 80%.
You can see the HTML coverage report opening the [index.html](build/reports/jacoco/test/html/index.html) file in your web browser.

## Running integration tests

Use `./gradlew integrationTest` to execute the integration tests via [JUnit 5](https://junit.org/junit5/) and others.
Use `./gradlew integrationTest -t` to keep executing your tests while watching for file changes in the background.
You can see the HTML report opening the [index.html](build/reports/tests/integrationTest/index.html) file in your web browser.

Like unit tests, you can also run subsets of a test suite.
See the [Test filtering](https://docs.gradle.org/current/userguide/java_testing.html#test_filtering) section of the Gradle documentation.

## Running smoke tests

Smoke Testing is a technique to verify the critical functionalities of a software.
Use `./gradlew smokeTest` to execute the smoke tests via [JUnit 5](https://junit.org/junit5/) and [Testcontainers](https://www.testcontainers.org/).
Use `./gradlew smokeTest -t` to keep executing your tests while watching for file changes in the background.
You can see the HTML report opening the [index.html](build/reports/tests/smokeTest/index.html) file in your web browser.

Like other test suites, you can also run subsets of a test suite. See the Test filtering section of the Gradle documentation.

## Debugging

You can debug the source code, add breakpoints, inspect variables and view the application's call stack.
Also, you can use the IDE for debugging the source code, unit, integration and smoke tests.
These functionalities are provided natively or using **Spring Boot DevTools**.

You can customize the [log verbosity](https://docs.gradle.org/current/userguide/logging.html#logging) of gradle tasks using the `-i` or `--info` flag.

## Profiling

While writing code for complex and enterprise applications, you need to understand the JVM internals, such as total memory allocated, memory usage, garbage collection, CPU utilization, etc.
**Spring Boot DevTools** needs to be used in conjunction with profilers and other tools to debug your program, overcome any bottlenecks, and optimize its functionality.

[VisualVM](https://visualvm.github.io/) is a visual tool integrating command-line JDK tools and lightweight profiling capabilities.
It has a number of views, which can be useful for diagnosing performance problems.
To ensure you have a significant load, you can use [Postman](https://www.postman.com/) or [JMeter](https://jmeter.apache.org/) for this job.

## Security, performance and best practices

This project uses [Spring Security](https://spring.io/projects/spring-security) and [JSON Web Tokens](https://jwt.io/).
It has CORS enabled by default. You can see these configurations by opening the [WebSecurity.java](src/main/java/app/configs/WebSecurity.java) file.
The `./gradlew dependencyCheckAnalyze` task identifies and reports known vulnerabilities in project dependencies.
You can use `./gradlew dependencyUpdates` to display the dependency updates for the project.

Native images provide key advantages, such as instant startup, instant peak performance, and reduced memory consumption.
You can easily adapt this project to use [Spring Native](https://docs.spring.io/spring-native/docs/current/reference/htmlsingle/).
Spring Native provides support for compiling Spring applications to native executables using the **GraalVM** native-image compiler.

Code conventions are base rules that allow the creation of a uniform code base across an organization.
[Checkstyle](https://checkstyle.sourceforge.io/) is very popular and recommended.
These checks are supplemented with [SonarQube](https://www.sonarqube.org/) and other tools.

## Commit messages convention

In order to have a consistent git history every commit must follow a specific template. Here's the template:

```bash
<type>(<ITEM ID>?): <subject>
```

### Type

Must be one of the following:

- **build**: Changes that affect the build system or external dependencies (example scopes: Gradle, Maven)
- **ci**: Changes to our CI configuration files and scripts (example scopes: Jenkins, Travis, Circle, SauceLabs)
- **chore**: Changes to the build process or auxiliary tools and libraries such as documentation generation
- **docs**: Documentation only changes
- **feat**: A new feature
- **fix**: A bug fix
- **perf**: A code change that improves performance
- **refactor**: A code change that neither fixes a bug nor adds a feature
- **revert**: A commit that reverts a previous one
- **style**: Changes that do not affect the meaning of the code (white-space, formatting, missing semi-colons, etc.)
- **test**: Adding missing tests or correcting existing tests

### ITEM ID

The related **issue** or **user story** or even **defect**.

- For **user stories**, you shoud use `US-` as prefix. Example: `feat(US-4321): ...`
- For **no related issues** or **defects** you should leave it blank. Example: `feat: ...`

### Subject

The subject contains a succinct description of the change.

## Build and deployment

In `cicd` folder you can find scripts for your [Jenkins](https://www.jenkins.io/) CI pipeline and an example for deploying your application with [Ansible](https://www.ansible.com/) to [Docker Swarm](https://docs.docker.com/engine/swarm/).

This project follows [Semantic Versioning](https://semver.org/) and uses git tags to define the current version of the project.
Use `./gradlew currentVersion` to print the current version extracted from SCM and `./gradlew release` to release the current version.
Use `./gradlew bootJar` to build the project and `./gradlew bootBuildImage` to build the Docker image for production.
The build artifacts are stored in the `build/libs` directory.
You can deploy this project to Docker Swarm using `./gradlew deploy` task.

## Reference Documentation

For further reference, please consider the following articles:

- [Official Gradle documentation](https://docs.gradle.org)
- [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.5/gradle-plugin/reference/html/)
- [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.5.5/reference/htmlsingle/#using-boot-devtools)
- [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.5/reference/htmlsingle/#boot-features-developing-web-applications)
- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Rest Repositories](https://docs.spring.io/spring-boot/docs/2.5.4/reference/htmlsingle/#howto-use-exposing-spring-data-repositories-rest-endpoint)
- [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
- [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.5.5/reference/htmlsingle/#boot-features-jpa-and-spring-data)
- [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/)
- [Flyway Migration](https://docs.spring.io/spring-boot/docs/2.5.5/reference/htmlsingle/#howto-execute-flyway-database-migrations-on-startup)
- [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.5.5/reference/htmlsingle/#production-ready)
- [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
- [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.5/gradle-plugin/reference/html/#build-image)
- [Testcontainers Postgres Module Reference Guide](https://www.testcontainers.org/modules/databases/postgres/)
