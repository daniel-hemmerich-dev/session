package net.eqno.session.integrations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CronTest {
    /** */
    @Value(value="${local.server.port}")
    private int port;

    /** */
    @Value(value="${api.key}")
    private String apiKey;

    /** */
    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     *
     */
    @Test
    public void testCreateAndDelete() {
        ResponseEntity<String> response = this.testRestTemplate.exchange(
            "http://localhost:" + this.port + "/cleanup?api-key=" + this.apiKey,
            HttpMethod.POST,
            HttpEntity.EMPTY,
            String.class
        );

        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }
}
