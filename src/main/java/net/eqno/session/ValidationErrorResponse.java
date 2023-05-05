package net.eqno.session;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ValidationErrorResponse {
    private final List<Violation> violations = new ArrayList<>();

    public List<Violation> getViolations() {
        return violations;
    }
}
