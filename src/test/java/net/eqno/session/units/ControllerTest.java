package net.eqno.session.units;

import net.eqno.session.Model;
import net.eqno.session.Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
public class ControllerTest {
    @Autowired
    private MockMvc mvc;

    @Value(value="${api.key}")
    private String apiKey;

    @MockBean
    private Repository repository;

    @Test
    void whenIdIsInvalid_thenReturnStatus400() throws Exception {

        when(this.repository.findById(anyString())).thenReturn(Optional.of(new Model()));
        when(this.repository.save(any())).thenReturn(any());

        String shortInvalidId = "42";
        String longInvalidId = "42424242424242424242424242";

        mvc.perform(
            MockMvcRequestBuilders
            .get("/" + shortInvalidId)
            .header("api-key", this.apiKey)
        ).andExpect(status().isBadRequest());
        mvc.perform(
            MockMvcRequestBuilders
            .get("/" + longInvalidId)
            .header("api-key", this.apiKey)
        ).andExpect(status().isBadRequest());

        mvc.perform(
            MockMvcRequestBuilders
            .delete("/" + shortInvalidId)
            .header("api-key", this.apiKey)
        ).andExpect(status().isBadRequest());
        mvc.perform(
            MockMvcRequestBuilders
            .delete("/" + longInvalidId)
            .header("api-key", this.apiKey)
        ).andExpect(status().isBadRequest());

        mvc.perform(
            MockMvcRequestBuilders
            .put("/" + shortInvalidId)
            .header("api-key", this.apiKey)
        ).andExpect(status().isBadRequest());
        mvc.perform(
            MockMvcRequestBuilders
            .put("/" + longInvalidId)
            .header("api-key", this.apiKey)
        ).andExpect(status().isBadRequest());
    }
}
