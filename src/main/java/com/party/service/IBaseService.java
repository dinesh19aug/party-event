package com.party.service;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.transaction.Transaction;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IBaseService {
    default String[] getNullPropertyNames(Object source) {
        List<String> nullValuePropertyNames = new ArrayList<>();
        for (Field f : source.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.get(source) == null) {
                    nullValuePropertyNames.add(f.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return nullValuePropertyNames.toArray(new String[0]);
    }

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
