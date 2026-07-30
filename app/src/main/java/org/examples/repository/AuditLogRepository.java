package org.examples.repository;

import org.examples.model.AuditLog;
import org.examples.util.HibernateUtil;

import java.util.List;

/**
 * Data-access operations for {@link AuditLog} entries. Log writing must
 * never throw and abort a business operation, so callers such as
 * {@link org.examples.helper.AuditLogger} should treat failures here as
 * best-effort.
 */
public class AuditLogRepository {

    public void saveLog(AuditLog log) {
        HibernateUtil.doInTransaction(session -> {
            session.persist(log);
            return null;
        });
    }

    public List<AuditLog> findAll() {
        return HibernateUtil.doInSession(session ->
                session.createQuery("from AuditLog", AuditLog.class).list());
    }

    public AuditLog findById(int id) {
        return HibernateUtil.doInSession(session ->
                session.get(AuditLog.class, id));
    }

    /** @return true if at least one log row was deleted */
    public boolean deleteAllLogs() {
        Integer deletedCount = HibernateUtil.doInTransaction(session ->
                session.createQuery("delete from AuditLog").executeUpdate());

        return deletedCount != null && deletedCount > 0;
    }
}
