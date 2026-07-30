package org.examples.repository;

<<<<<<< HEAD
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
=======
import org.examples.data.DbConnection;
import org.examples.model.AuditLog;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AuditLogRepository {
    private static final String LOG_FILE =
            "C:\\Users\\Abinaya S\\OneDrive\\Desktop\\JAVA\\Clinic_Management_Application\\applogs\\application_logs.log";

    public void saveLog(AuditLog log) {

        String sql =
                "INSERT INTO audit_log(log_time,level,message) VALUES(?,?,?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(
                    1,
                    Timestamp.valueOf(log.getLogTime())
            );

            ps.setString(
                    2,
                    log.getLevel()
            );

            ps.setString(
                    3,
                    log.getMessage()
            );

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<AuditLog> findAll() {

        List<AuditLog> logs = new ArrayList<>();

        try {

            List<String> lines = Files.readAllLines(Paths.get(LOG_FILE));

            for (String line : lines) {

                String[] parts = line.split("\\|");

                if(parts.length >= 3){
                    DateTimeFormatter formatter =
                            DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");

                    logs.add(new AuditLog(
                            LocalDateTime.parse(parts[0].trim(), formatter),
                            parts[1].trim(),
                            parts[2].trim()
                    ));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return logs;
    }
    public boolean deleteAllLogs() {

        String sql = "DELETE FROM audit_log";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
>>>>>>> 7bb398e2a56e4a9475b741915125afbb57a30061
