package net.eqno.session;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

/**
 * The session controller containing requests for all CRUD operations
 */
@RestController
public class Controller extends API {
    @Autowired
    Repository repository;

    /**
     * Creates and returns a new session
     * @param headers The HTTP headers of the request
     * @return The created session object
     * @throws NoSuchAlgorithmException Thrown if the MD5 hash algorithm is not supported
     */
    @PostMapping("/")
    @JsonView(View.Default.class)
    public Model create(
            @RequestHeader Map<String, String> headers
    ) throws NoSuchAlgorithmException, AuthException {

        this.authenticate(headers.getOrDefault("api-key", ""));

        Model session = new Model();
        session.setCsrfToken(Util.generateCSRFToken());
        session.setHttpHeaderHash(
            Util.generateHTTPHeaderHash(
                headers.get("host"),
                headers.get("connection"),
                headers.get("pragma"),
                headers.get("cache-control"),
                headers.get("user-agent"),
                headers.get("accept"),
                headers.get("accept-encoding"),
                headers.get("accept-language")
            )
        );

        session.setLastUpdate(ZonedDateTime.now().toString());

        repository.save(session);
        return session;
    }

    /**
     * Returns an existing session identified by the id parameter
     * @param headers The HTTP headers of the request
     * @param id The session id
     * @return The session object
     * @throws NoSuchAlgorithmException Thrown if the MD5 hash algorithm is not supported
     */
    @GetMapping("/")
    @JsonView(View.Default.class)
    public Model read(
          @RequestHeader Map<String, String> headers,
          @RequestParam(value = "id") String id
    ) throws Exception {

        this.authenticate(headers.getOrDefault("api-key", ""));

        Optional<Model> session = repository.findById(id);
        if(session.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");

        this.authorize(
            session.get(),
            headers
        );

        session.get().setLastUpdate(ZonedDateTime.now().toString());

        return session.get();
    }

    /**
     * Updates and returns the updated session
     * @param headers The HTTP headers
     * @param id The id of the session
     * @param body The session values to update
     * @return The updated session object
     */
    @PatchMapping("/")
    @JsonView(View.Default.class)
    public Model update(
       @RequestHeader Map<String, String> headers,
       @RequestParam(value = "id") String id,
       @RequestBody Map<String, String> body
    ) throws Exception {

        this.authenticate(headers.getOrDefault("api-key", ""));

        Optional<Model> session = repository.findById(id);
        if(session.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");

        this.authorize(
            session.get(),
            headers
        );

        session.get().setValues(body);
        session.get().setLastUpdate(ZonedDateTime.now().toString());

        repository.save(session.get());
        return session.get();
    }

    /**
     * Deletes the session associated with the id parameter
     * @param headers The HTTP headers of the request
     * @param id The session id
     */
    @DeleteMapping("/")
    public void delete(
        @RequestHeader Map<String, String> headers,
        @RequestParam(value = "id") String id
    ) throws Exception {

        this.authenticate(headers.getOrDefault("api-key", ""));

        Optional<Model> session = repository.findById(id);
        if(session.isPresent()) {
            this.authorize(
                session.get(),
                headers
            );

            this.repository.deleteById(id);
        }
    }
}