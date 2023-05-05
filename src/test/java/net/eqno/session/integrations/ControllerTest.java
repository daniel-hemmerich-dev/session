package net.eqno.session.integrations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.eqno.session.Model;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {
    /** */
    @Value(value="${local.server.port}")
    private int port;

    /** */
    @Value(value="${api.key}")
    private String apiKey;

    /** */
    @Autowired
    private TestRestTemplate testRestTemplate;

    /** */
    private Model lastUsedModel;

    public Model getLastUsedModel() {
        return lastUsedModel;
    }

    public void setLastUsedModel(Model lastUsedModel) {
        this.lastUsedModel = lastUsedModel;
    }

    /**
     * Generates the HTTP Headers with the API key
     * @return The HTTP Headers
     */
    private HttpHeaders generateHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("api-key", this.apiKey);
        return headers;
    }

    /**
     *
     * @param model The model entity to assert
     */
    private void assertResponse(Model model) {
        assertThat(
            model.getId()
        ).containsPattern("^[0-9a-z]+$");

        assertThat(
            model.getCsrfToken()
        ).containsPattern("^[0-9a-z-]+$");
    }

    /**
     *
     */
    @Test
    public void testCreate() {
        Model model = this.testRestTemplate.postForObject(
            "http://localhost:" + this.port + "/",
            new HttpEntity<>(this.generateHeaders()),
            Model.class
        );

        this.assertResponse(model);
        this.setLastUsedModel(model);
    }

    /**
     *
     */
    @Test
    public void testCreateAndRead() {
        this.testCreate();

        ResponseEntity<Model> response = this.testRestTemplate.exchange(
            "http://localhost:" + this.port + "?id=" + this.getLastUsedModel().getId(),
            HttpMethod.GET,
            new HttpEntity<>(this.generateHeaders()),
            Model.class
        );

        this.assertResponse(Objects.requireNonNull(response.getBody()));
    }

    /**
     * The HTTP PATCH METHOD SEEMS TO BE NOT SUPPORTED?
     * https://github.com/spring-projects/spring-framework/issues/12640
     */
    @Test
    public void testCreateAndUpdate() throws JsonProcessingException {
        this.testCreate();
        Map<String, String> sessionValues = new HashMap<>();
        sessionValues.put("foo", "bar");
        this.getLastUsedModel().setValues(sessionValues);

        HttpHeaders httpHeaders = this.generateHeaders();
        httpHeaders.add("Content-Type", "application/json");

        ResponseEntity<Model> response = this.testRestTemplate.exchange(
            "http://localhost:" + this.port + "?id=" + this.getLastUsedModel().getId(),
            HttpMethod.PUT,
            new HttpEntity<>(
                new ObjectMapper().writeValueAsString(this.getLastUsedModel().getValues()),
                httpHeaders
            ),
            Model.class
        );

        this.assertResponse(Objects.requireNonNull(response.getBody()));
        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    /**
     *
     */
    @Test
    public void testCreateAndDelete() {
        this.testCreate();

        ResponseEntity<Model> response = this.testRestTemplate.exchange(
            "http://localhost:" + this.port + "?id=" + this.getLastUsedModel().getId(),
            HttpMethod.DELETE,
            new HttpEntity<>(this.getLastUsedModel(), this.generateHeaders()),
            Model.class
        );

        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }
}
