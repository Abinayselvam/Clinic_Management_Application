# Clinic Management Application

A console-based Clinic Management System built in Java. It supports two
personas — **Admin** (manages doctors and audit logs) and **Front Desk**
(registers patients and books appointments) — backed by a MySQL database
accessed through **Hibernate ORM / JPA**.

The project was built incrementally as a series of use cases (UC1–UC17),
starting from a simple in-memory console app and evolving into a
persistent, logged, ORM-backed application. This README documents what
each use case added.

---

## Tech Stack

| Layer          | Technology                          |
|----------------|--------------------------------------|
| Language       | Java 21                              |
| Build tool     | Maven                                |
| Persistence    | Hibernate ORM 6 (Jakarta Persistence 3) |
| Database       | MySQL                                |
| Logging        | Log4j 2 (console + rolling file)     |
| UI             | Console (`Scanner`-based menus)      |

---

## Project Structure

```
app/src/main/java/org/examples/
├── Main.java                  Entry point
├── enums/                     Gender, Shift, Specification
├── model/                     JPA entities: Doctor, Patient, Appointment, AuditLog
├── repository/                Hibernate-backed data access (one per entity)
├── operations/                Program (main menu), AdminMenu, FrontDeskMenu
├── helper/                    ScannerHelper (console I/O), AuditLogger
├── data/                      FileHandler (CSV bulk import)
└── util/                      HibernateUtil (SessionFactory), IdGenerator

app/src/main/resources/
├── hibernate.cfg.xml          DB connection + entity mappings
└── log4j2.xml                 Console + rolling-file logging config

applogs/application_logs.log   Log4j output (relative to project root)
```

---

## Prerequisites

- JDK 21+
- Maven 3.8+
- MySQL server running locally (or update `hibernate.cfg.xml` to point
  elsewhere)

## Database Setup

Hibernate is configured with `hibernate.hbm2ddl.auto=validate`, so the
schema must exist before the app starts — it will not auto-create tables.

1. Create the database:
   ```sql
   CREATE DATABASE clinicops;
   ```
2. Create tables matching the entities in `org.examples.model`
   (`doctor`, `patient`, `appointment`, `audit_log`) with columns matching
   each entity's fields.
3. Update credentials in
   `app/src/main/resources/hibernate.cfg.xml` if your MySQL username,
   password, or host differ from the defaults (`root` / `root` /
   `localhost:3306`).

## Running the Application

```bash
cd app
mvn compile exec:java -Dexec.mainClass="org.examples.Main"
```

(or run `org.examples.Main` directly from your IDE once dependencies are
resolved).

---

## Use Cases

| UC | Title | What it added |
|----|-------|----------------|
| **UC1** | Console & Doctor Entry | Initial console application skeleton and welcome menu. |
| **UC2** | Doctor's Data System | First version of doctor registration and a display/list view, storing doctors in memory. |
| **UC3** | Refactored UC2 | Introduced a proper `Doctor` object and an `ArrayList` to store doctor records instead of loose variables. |
| **UC4** | Fixed Specialization & Shift | Replaced free-text specialization/shift input with the `Specification` and `Shift` enums for validated, fixed choices. |
| **UC5** | Doctor Bulk Data | Added CSV-based bulk import of doctors via `FileHandler`. |
| **UC6** | CSV Library | Hardened the CSV parsing used for bulk doctor import (malformed-row handling, enum parsing). |
| **UC7** | Register Patient Data | Added patient registration and a method to view all registered patients. |
| **UC8** | Get Patient by Mobile | Added lookup of an existing patient by mobile number, to prevent duplicate registrations. |
| **UC9** | Appointment Booking System | Introduced the `Appointment` entity and the front-desk booking flow linking a patient to a doctor and a slot. |
| **UC10** | Appointment Consideration | Added doctor-availability checks so a doctor can't be double-booked for the same slot. |
| **UC11** | Shift-Aware Appointment | Restricted bookable slots to a doctor's actual shift (morning / night / both) via `Doctor.isShiftAvailable`. |
| **UC12** | Logging Infrastructure | Introduced the `AuditLog` entity and `AuditLogRepository` for persisting application events. |
| **UC13** | Functional Logging | Wired up `AuditLogger` and logging calls throughout the admin/front-desk flows (registrations, bookings, updates). |
| **UC14** | Error & Security Logging | Added logging around failure paths (invalid input, not-found records, failed operations) for traceability. |
| **UC15** | Log4j Migration | Added the Log4j 2 dependency and replaced ad-hoc console logging with structured `Logger` calls. |
| **UC16** | Relational Persistence | Introduced JDBC-backed persistence against MySQL as the first step away from an in-memory model. |
| **UC17** | ORM Persistence | Migrated persistence from raw JDBC to **Hibernate ORM** — entities annotated with JPA, repositories rewritten around `Session`/`Transaction`, and `hibernate.cfg.xml` added. |

### Post-UC17 hardening

After UC17, the codebase still had a few raw-JDBC leftovers (ID
generation, log deletion) and some bugs from the incremental build-up.
These were subsequently cleaned up:

- Removed the last raw-JDBC class (`DbConnection`) — ID generation and
  log deletion now go through Hibernate/HQL as well.
- Centralized session/transaction handling in `HibernateUtil`
  (`doInSession` / `doInTransaction`), so every repository method opens,
  commits/rolls back, and closes a session correctly.
- Fixed a wrong HQL field name in `findBySpecification`, `update()`/
  `delete()` methods that always returned `false`, a bulk CSV import
  that parsed doctors but never saved them, and patient IDs that could
  collide after a deletion.
- Removed dead code (unused constructors, unused fields, a hardcoded
  personal file path) and added the missing `log4j2.xml` configuration.

---

## Known Limitations

- No automated tests yet.
- DB credentials are stored in plaintext in `hibernate.cfg.xml` — fine
  for local development, but should move to environment variables or a
  secrets store before any shared/production use.
- `hibernate.hbm2ddl.auto=validate` means schema migrations must be
  applied manually (no Flyway/Liquibase yet).