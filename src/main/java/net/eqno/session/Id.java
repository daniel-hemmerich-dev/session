package net.eqno.session;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

/**
 *
 */
@Validated
public class Id {
    @NotNull
    @NotEmpty
    @Length(min = 24, max = 24)
    @Pattern(regexp = "^[a-z0-9]+$")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
