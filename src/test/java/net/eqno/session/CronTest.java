package net.eqno.session;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CronTest {
    @Autowired
    private MockMvc mvc;

    /** */
    @Value(value="${api.key}")
    private String apiKey;

    @Test
    void whenIdIsInvalid_thenReturnStatus400() throws Exception {
        mvc.perform(
            post("/cleanup")
            .param("api-key", this.apiKey)
        ).andExpect(status().isOk());
    }
}
