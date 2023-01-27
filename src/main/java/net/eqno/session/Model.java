package net.eqno.session;

import java.time.ZonedDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document("sessions")
public class Model {
    @Id
    @JsonView(View.Default.class)
    @NotNull
    @Length(min = 24, max = 24)
    //@Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")
    public String id;

    @JsonView(View.Default.class)
    @NotNull
    @Length(min = 32, max = 32)
    //@Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")
    public String csrfToken;

    @NotNull
    @Length(min = 32, max = 32)
    //@Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")
    public String httpHeaderHash;

    @JsonView(View.Default.class)
    public Map<String, String> values;

    @JsonView(View.Default.class)
    @DateTimeFormat
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
