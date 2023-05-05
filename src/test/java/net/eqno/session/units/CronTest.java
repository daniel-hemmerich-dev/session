package net.eqno.session.units;

import net.eqno.session.Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@AutoConfigureMockMvc
public class CronTest {
    @Autowired
    private MockMvc mvc;

    /** */
    @Value(value="${api.key}")
    private String apiKey;

    @MockBean
    private Repository repository;

    @Test
    void whenIdIsInvalid_thenReturnStatus400() throws Exception {
        doNothing().when(this.repository).deleteExpired(anyString());

        mvc.perform(
            MockMvcRequestBuilders
            .post("/cleanup")
            .param("api-key", this.apiKey)
        ).andExpect(status().isOk());
    }
}
