package org.examples.model;

<<<<<<< HEAD
import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name="audit_log")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
=======
import java.time.LocalDateTime;

public class AuditLog {

>>>>>>> 7bb398e2a56e4a9475b741915125afbb57a30061
    private int id;
    private LocalDateTime logTime;
    private String level;
    private String message;

    public AuditLog() {
    }

    public AuditLog(LocalDateTime logTime,
                    String level,
                    String message) {

        this.logTime = logTime;
        this.level = level;
        this.message = message;
    }

    public AuditLog(int id,
                    LocalDateTime logTime,
                    String level,
                    String message) {

        this.id = id;
        this.logTime = logTime;
        this.level = level;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getLogTime() {
        return logTime;
    }

    public String getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogTime(LocalDateTime logTime) {
        this.logTime = logTime;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {

        return String.format(
                "%-20s | %-8s | %s",
                logTime,
                level,
                message
        );
    }
}