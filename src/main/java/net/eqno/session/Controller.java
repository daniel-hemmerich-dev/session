package net.eqno.session;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class Controller extends API {
    @Autowired
    private Repository repository;

    public Repository getRepository() { return repository; }

    public void setRepository(Repository repository) { this.repository = repository; }

    /**
     * Creates and returns a new session
     * @param headers The HTTP headers of the request
     * @return The created session object
     * @throws NoSuchAlgorithmException Thrown if the MD5 hash algorithm is not supported
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(View.Default.class)
    public @Valid Model create(
        @RequestHeader @Valid Map<@NotEmpty String, @NotEmpty String> headers
    ) throws NoSuchAlgorithmException, AuthException {

        this.authenticate(headers.getOrDefault("api-key", ""));

        Model session = new Model();
        session.setCsrfToken(CSRF.generateToken());
        session.setHttpHeaderHash(
            HTTPHeader.generateHash(
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
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(View.Default.class)
    public @Valid Model read(
        @RequestHeader @Valid Map<@NotEmpty String, @NotEmpty String> headers,
        @PathVariable @Valid Id id
    ) throws Exception {

        this.authenticate(headers.getOrDefault("api-key", ""));

        Optional<Model> session = repository.findById(id.getId());
        if(session.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found " + id.getId());

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
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(View.Default.class)
    public @Valid Model update(
        @RequestHeader @Valid Map<@NotEmpty String, @NotEmpty String> headers,
        @PathVariable @Valid Id id,
        @RequestBody @Valid Map<@NotEmpty String, @NotEmpty String> body
    ) throws Exception {

        this.authenticate(headers.getOrDefault("api-key", ""));

        Optional<Model> session = repository.findById(id.getId());
        if(session.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found " + id.getId());

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
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
        @RequestHeader @Valid Map<@NotEmpty String, @NotEmpty String> headers,
        @PathVariable @Valid Id id
    ) throws Exception {

        this.authenticate(headers.getOrDefault("api-key", ""));

        Optional<Model> session = repository.findById(id.getId());
        if(session.isPresent()) {
            this.authorize(
                session.get(),
                headers
            );

            this.repository.deleteById(id.getId());
        }
    }
}