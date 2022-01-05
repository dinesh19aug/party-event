package com.party.service;

import com.party.vo.Event;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.transaction.Transaction;

import java.util.Map;
import java.util.UUID;


/**
 *
 * @author dines
 */

public interface IEventService {
    Event execute();

    default UUID runInTransaction(Runnable runnable, Session session) {
        Transaction transaction = session.beginTransaction();
        try {
            UUID actionId = UUID.randomUUID();
            runnable.run();
            session.query("CREATE (:Action {actionId: $actionId, timestamp: TIMESTAMP()})", Map.of("actionId", actionId));
            transaction.commit();
            transaction.close();
            return actionId;
        } catch (RuntimeException e) {
            System.err.println("Could not execute transaction: " + e);
            transaction.rollback();
            throw e;
        }
    }
}
