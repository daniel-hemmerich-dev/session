package net.eqno.session;

import java.util.UUID;

/**
 *
 */
public class CSRF {
    /**
     *
     * @return The generated CSRF token
     */
    public static String generateToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
