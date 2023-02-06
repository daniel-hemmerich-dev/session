package net.eqno.session;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.ZonedDateTime;

@RestController
@Validated
public class Cron extends API {
    @Autowired
    Repository repository;

    /**
     *
     * @param apiKey The API Key used for authentication
     * @throws AuthException If the API key is wrong
     */
    @PostMapping("/cleanup")
    @JsonView
    public void cleanup(@RequestParam(value = "api-key") String apiKey) throws AuthException {
        this.authenticate(apiKey);

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime nowMinus30Minutes = now.minusMinutes(30);

        repository.deleteExpired(nowMinus30Minutes.toString());
    }
}
