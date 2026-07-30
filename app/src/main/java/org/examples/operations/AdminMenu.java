package org.examples.operations;

import org.examples.data.FileHandler;
import org.examples.enums.Shift;
import org.examples.enums.Specification;
import org.examples.helper.ScannerHelper;
import org.examples.model.AuditLog;
import org.examples.model.Doctor;
import org.examples.repository.AuditLogRepository;
import org.examples.repository.DoctorRepository;

import java.util.List;

public class AdminMenu {

    private static final DoctorRepository repository = new DoctorRepository();
    private static final AuditLogRepository auditLogRepository = new AuditLogRepository();

    private AdminMenu() {
        // static menu - no instances
    }

    public static void show() {

        boolean exitSystem = false;

        while (!exitSystem) {

            System.out.println("----WELCOME TO CLINIC ADMIN MENU----\n");
            System.out.println("Select persona 1.Doctor's Entry 2.Update Doctor 3.Delete Doctor Data " +
                    "4.Bulk Entry(csv) 5.View Audit Logs 6.Show Doctor's  List 7.Exit\n");

            int choice = ScannerHelper.readInt("Enter Choice : ");

            switch (choice) {
                case 1 -> doctorEntry();
                case 2 -> updateDoctor();
                case 3 -> deleteDoctor();
                case 4 -> bulkEntry();
                case 5 -> viewAuditLogs();
                case 6 -> doctorsList();
                case 7 -> {
                    System.out.println("Shutting down admin clinic...goodbye");
                    exitSystem = true;
                }
                default -> {
                    System.out.println("Invalid choice");
                    Program.logger.warn("Invalid Menu Option Selected");
                }
            }
        }
    }

    private static void doctorEntry() {

        System.out.println("Register the New Doctor Entry");

        int count = ScannerHelper.readInt("Enter Number of Doctors : ");

        List<Doctor> doctorList = repository.findAll();

        for (int i = 0; i < count; i++) {

            String name = ScannerHelper.readString("Enter Name : ");

            Specification specialization =
                    ScannerHelper.readEnumChoice("Select Specialization", Specification.values());

            int experience = ScannerHelper.readInt("Enter Experience : ");

            Shift shift = ScannerHelper.readEnumChoice("Select Shift", Shift.values());

            boolean duplicate = doctorList.stream()
                    .anyMatch(d ->
                            d.getName().equalsIgnoreCase(name)
                                    && d.getSpecialization() == specialization
                                    && d.getExperience() == experience);

            if (duplicate) {
                System.out.println("Doctor already exists.");
                continue;
            }

            String id = repository.generateDoctorId();

            Doctor doctor = new Doctor(id, name, specialization, experience, shift);

            repository.save(doctor);

            doctorList.add(doctor);

            Program.logger.info("Doctor Registered : {}", id);

            System.out.println("Doctor Registered Successfully.");
        }
    }

    private static void deleteDoctor() {

        String id = ScannerHelper.readString("Enter Doctor's ID : ");

        boolean deleted = repository.delete(id);

        if (deleted) {
            System.out.println("Doctor Deleted Successfully.");
            Program.logger.info("Doctor Deleted : {}", id);
        } else {
            System.out.println("No Doctor Found with ID : " + id);
            Program.logger.warn("Delete Failed. Doctor not found : {}", id);
        }
    }

    private static void bulkEntry() {

        System.out.println("Welcome to Bulk Entry");

        String filename = ScannerHelper.readString("Enter Filename : ");

        List<Doctor> parsedDoctors = FileHandler.bulkLoadDoctors(filename);

        if (parsedDoctors.isEmpty()) {
            System.out.println("Upload failed or file was empty.");
            Program.logger.error("Bulk upload failed or file was empty.");
            return;
        }

        int savedCount = 0;

        for (Doctor parsed : parsedDoctors) {

            String id = repository.generateDoctorId();

            Doctor doctor = new Doctor(
                    id,
                    parsed.getName(),
                    parsed.getSpecialization(),
                    parsed.getExperience(),
                    parsed.getShift());

            repository.save(doctor);
            savedCount++;
        }

        System.out.println(savedCount + " doctor(s) imported successfully.");
        Program.logger.info("Bulk doctor import completed : {} records", savedCount);
    }

    private static void viewAuditLogs() {

        List<AuditLog> logs = auditLogRepository.findAll();

        if (logs.isEmpty()) {
            System.out.println("No Logs Available");
            return;
        }

        System.out.println("-----------------------------------------------");

        for (AuditLog log : logs) {
            System.out.println(log);
        }

        System.out.println("-----------------------------------------------");
    }

    private static void doctorsList() {

        System.out.println("\n=========== DOCTOR LIST ===========");

        if (repository.findAll().isEmpty()) {
            Program.logger.warn("No Doctors Found");
            System.out.println("No Doctors Found.");
            return;
        }

        System.out.println("1.All Available Doctors List");
        System.out.println("2.Specialization Available Doctors List");
        System.out.println("3.Experience Available Doctors List");

        int choice = ScannerHelper.readInt("Enter Choice : ");

        switch (choice) {
            case 1 -> {
                List<Doctor> allDoctors = repository.findAll();
                System.out.println("Total Doctor List: " + allDoctors.size());
                allDoctors.forEach(System.out::println);
            }
            case 2 -> getSpecificationDoctorList().forEach(System.out::println);
            case 3 -> findByExperience().forEach(System.out::println);
            default -> System.out.println("Invalid choice");
        }
    }

    public static List<Doctor> getDoctorList() {
        return repository.findAll();
    }

    public static List<Doctor> getSpecificationDoctorList() {
        Specification specialization =
                ScannerHelper.readEnumChoice("Select Specialization", Specification.values());
        return repository.findBySpecification(specialization);
    }

    private static List<Doctor> findByExperience() {
        int experience = ScannerHelper.readInt("Enter Experience : ");
        return repository.findByExperience(experience);
    }

    private static void updateDoctor() {

        String id = ScannerHelper.readString("Enter Doctor ID : ");

        Doctor doctor = repository.findById(id);

        if (doctor == null) {
            System.out.println("No Doctor Found with ID : " + id);
            Program.logger.warn("Update Failed. Doctor not found : {}", id);
            return;
        }

        System.out.println("\nCurrent Doctor Details");
        System.out.println(doctor);

        String name = ScannerHelper.readString("Enter New Name : ");

        Specification specialization =
                ScannerHelper.readEnumChoice("Select New Specialization", Specification.values());

        int experience = ScannerHelper.readInt("Enter New Experience : ");

        Shift shift = ScannerHelper.readEnumChoice("Select New Shift", Shift.values());

        Doctor updatedDoctor = new Doctor(id, name, specialization, experience, shift);

        boolean updated = repository.update(updatedDoctor);

        if (updated) {
            System.out.println("Doctor Updated Successfully.");
            Program.logger.info("Doctor Updated : {}", id);
        } else {
            System.out.println("Doctor Update Failed.");
            Program.logger.error("Doctor Update Failed : {}", id);
        }
    }
}
