package org.examples.repository;

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