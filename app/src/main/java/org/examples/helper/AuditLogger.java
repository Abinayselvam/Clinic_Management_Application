//package org.examples.helper;
//
//import org.examples.model.AuditLog;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//public class AuditLogger {
//   private static final List<AuditLog> logs =new ArrayList<>();
//   public static void  log(String level, String message) {
//       DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
//       logs.add(new AuditLog( LocalDateTime.now().format(dtf), level, message));
//   }
//   public static List<AuditLog> getLogs() {
//       return logs;
//   }
//}
