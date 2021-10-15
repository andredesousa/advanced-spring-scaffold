package smoke;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@DisplayName("Smoke Tests")
public class HealthTests {

    static Network NETWORK = Network.newNetwork();

    static PostgreSQLContainer<?> PG_CONTAINER = new PostgreSQLContainer<>("postgres");

    static GenericContainer<?> APP_CONTAINER = new GenericContainer<>(DockerImageName.parse("spring-api"));

    @BeforeAll
    static void beforeAll() {
        PG_CONTAINER
            .withNetwork(NETWORK)
            .withNetworkAliases("postgres")
            .withExposedPorts(5432)
            .withDatabaseName("spring")
            .withUsername("root")
            .withPassword("secret")
            .waitingFor(Wait.forListeningPort())
            .start();

        APP_CONTAINER
            .withNetwork(NETWORK)
            .withExposedPorts(8080)
            .withEnv("SPRING_PROFILES_ACTIVE", "prod")
            .withEnv("DB_URL", "jdbc:postgresql://postgres:5432/spring")
            .withEnv("DB_USER", "root")
            .withEnv("DB_PASSWORD", "secret")
            .withEnv("DB_VALIDATE", "none")
            .withEnv("JWT_SECRET", "<ApiSecretKey>")
            .withEnv("JWT_EXPIRATION", "3600")
            .waitingFor(Wait.forListeningPort())
            .start();
    }

    @AfterAll
    static void afterAll() {
        PG_CONTAINER.stop();
        APP_CONTAINER.stop();
    }

    @Test
    @DisplayName("/health endpoint")
    void healthEndpoint() throws Exception {
        String address = "http://localhost:" + APP_CONTAINER.getMappedPort(8080) + "/health";
        String expected =
            "{status=UP, components={db={status=UP}, diskSpace={status=UP}, " +
            "livenessState={status=UP}, ping={status=UP}, readinessState={status=UP}}}";

        ResponseEntity<Object> response = new RestTemplate().getForEntity(address, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().toString()).isEqualTo(expected);
    }
}
