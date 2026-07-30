package org.examples.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.function.Function;

/**
 * Central access point to the Hibernate {@link SessionFactory} and the
 * only place in the application that knows how to open/close a
 * {@link Session} or manage a {@link Transaction}.
 * <p>
 * Repositories should never open a {@code Session} themselves; instead
 * they call {@link #doInSession(Function)} for read-only work or
 * {@link #doInTransaction(Function)} for work that needs to be
 * committed. Both helpers guarantee the session is closed and that a
 * transaction is rolled back if the supplied work throws.
 */
public final class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private HibernateUtil() {
        // utility class - no instances
    }

    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry registry =
                    new StandardServiceRegistryBuilder()
                            .configure("hibernate.cfg.xml")
                            .build();

            return new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();

        } catch (Exception e) {
            throw new ExceptionInInitializerError(
                    "Failed to build Hibernate SessionFactory: " + e.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    /** Call once when the application shuts down to release DB resources. */
    public static void shutdown() {
        if (!SESSION_FACTORY.isClosed()) {
            SESSION_FACTORY.close();
        }
    }

    /**
     * Runs read-only {@code work} inside a fresh {@link Session} and
     * guarantees the session is closed afterwards.
     */
    public static <T> T doInSession(Function<Session, T> work) {
        try (Session session = SESSION_FACTORY.openSession()) {
            return work.apply(session);
        }
    }

    /**
     * Runs {@code work} inside a transaction, committing on success and
     * rolling back if {@code work} throws. The session is always closed.
     */
    public static <T> T doInTransaction(Function<Session, T> work) {
        try (Session session = SESSION_FACTORY.openSession()) {

            Transaction tx = session.beginTransaction();

            try {
                T result = work.apply(session);
                tx.commit();
                return result;

            } catch (RuntimeException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                throw e;
            }
        }
    }
}
