package org.examples.helper;

import org.examples.model.AuditLog;
import org.examples.repository.AuditLogRepository;

import java.time.LocalDateTime;

public class AuditLogger {

    private static AuditLogRepository repository =
            new AuditLogRepository();

    public static void log(String level,
                           String message) {

        AuditLog log =
                new AuditLog(
                        LocalDateTime.now(),
                        level,
                        message
                );

        repository.saveLog(log);
    }
}