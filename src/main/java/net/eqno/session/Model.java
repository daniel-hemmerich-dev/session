package net.eqno.session;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 */
@Document("sessions")
public class Model {
    @Id
    @JsonView(View.Default.class)
    @NotBlank
    @Length(min = 24, max = 24)
    @Pattern(regexp = "^[a-z0-9]+$")
    public String id;

    @JsonView(View.Default.class)
    @NotBlank
    @Length(min = 36, max = 36)
    @Pattern(regexp = "^[a-z0-9-]+$")
    public String csrfToken;

    @NotBlank
    @Length(min = 32, max = 32)
    @Pattern(regexp = "^[a-z0-9]+$")
    public String httpHeaderHash;

    @JsonView(View.Default.class)
    @Size(max = 32)
    public Map<String, String> values;

    @JsonView(View.Default.class)
    @DateTimeFormat
    @NotBlank
    public String lastUpdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCsrfToken() {
        return csrfToken;
    }

    public void setCsrfToken(String csrfToken) {
        this.csrfToken = csrfToken;
    }

    public String getHttpHeaderHash() {
        return httpHeaderHash;
    }

    public void setHttpHeaderHash(String httpHeaderHash) {
        this.httpHeaderHash = httpHeaderHash;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
