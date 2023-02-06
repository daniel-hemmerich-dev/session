package net.eqno.session;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface Repository extends MongoRepository<Model, String> {
    /**
     * @Query("{name:'?0'}")
     *     GroceryItem findItemByName(String name);
     *
     *     @Query(value="{category:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
     *     List<GroceryItem> findAll(String category);
     *
     *     public long count();
     */

    /**
     * Delete all sessions which are older than X minutes.
     * @param date All session older than this date will be deleted
     * @return The number of deleted sessions
     */
    @Query(value="{'lastUpdate' : {$lt : '?0'}}", delete = true)
    public long deleteExpired(String date);
}
