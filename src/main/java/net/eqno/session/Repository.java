package net.eqno.session;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 */
public interface Repository extends MongoRepository<Model, String> {

    /**
     * Delete all sessions which are older than X minutes.
     *
     * @param date All session older than this date will be deleted
     */
    @Query(value="{'lastUpdate' : {$lt : '?0'}}", delete = true)
    void deleteExpired(String date);
}
