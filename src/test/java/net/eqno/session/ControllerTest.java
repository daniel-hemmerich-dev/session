package net.eqno.session;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void whenIdIsInvalid_thenReturnStatus400() throws Exception {
        String shortInvalidId = "42";
        String longInvalidId = "42424242424242424242424242";

        mvc.perform(
            get("/")
            .param("id", shortInvalidId)
        ).andExpect(status().isBadRequest());
        mvc.perform(
            get("/")
            .param("id", longInvalidId)
        ).andExpect(status().isBadRequest());

        mvc.perform(
            delete("/")
            .param("id", shortInvalidId)
        ).andExpect(status().isBadRequest());
        mvc.perform(
            delete("/")
            .param("id", longInvalidId)
        ).andExpect(status().isBadRequest());

        mvc.perform(
            put("/")
            .param("id", shortInvalidId)
        ).andExpect(status().isBadRequest());
        mvc.perform(
            put("/")
            .param("id", longInvalidId)
        ).andExpect(status().isBadRequest());
    }
}
