package net.eqno.session;

import jakarta.security.auth.message.AuthException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@ConfigurationProperties(prefix = "api")
@Validated
public class API {
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @param apiKey The API key to validate against
     */
    public void authenticate(String apiKey) throws AuthException {
        if(!this.getKey().equals(apiKey)) throw new AuthException();
    }

    /**
     *
     * @param session The session to validate against
     * @param headers The HTTP headers of the request
     * @throws AuthException Thrown if the request has no authorization for this session
     * @throws NoSuchAlgorithmException Thrown if the MD5 hash algorithm is not supported
     */
    public void authorize(
        Model session,
        Map<String, String> headers
    ) throws AuthException, NoSuchAlgorithmException {
        String httpHeaderHash = HTTPHeader.generateHash(
                headers.get("host"),
                headers.get("connection"),
                headers.get("pragma"),
                headers.get("cache-control"),
                headers.get("user-agent"),
                headers.get("accept"),
                headers.get("accept-encoding"),
                headers.get("accept-language")
        );
        if(!httpHeaderHash.equals(session.getHttpHeaderHash())) throw new AuthException();
    }
}
