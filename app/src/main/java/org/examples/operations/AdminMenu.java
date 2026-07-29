package org.examples.operations;

import org.examples.data.FileHandler;
import org.examples.enums.Shift;
import org.examples.enums.Specialization;
import org.examples.helper.ScannerHelper;
import org.examples.model.AuditLog;
import org.examples.model.Doctor;
import org.examples.repository.AuditLogRepository;
import org.examples.repository.DoctorRepository;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class AdminMenu {
     private static List<Doctor> doctorsDetails = new ArrayList<>();
    private static final String LOG_FILE =
            "C:\\Users\\Abinaya S\\OneDrive\\Desktop\\JAVA\\Clinic_Management_Application\\applogs\\application_logs.log";
    private static DoctorRepository repository = new DoctorRepository();
    public AdminMenu(DoctorRepository doctorRepository)
    {
      repository = doctorRepository;
    }
    public static void show()
    {

        boolean exitSystem = false;
        while(!exitSystem)
            {
                System.out.println("----WELCOME TO CLINIC ADMIN MENU----\n");
                System.out.println("Select persona 1.Doctor's Entry 2.Update Doctor 3.Delete Doctor Data 4.Bulk Entry(csv) 5.View Audit Logs 6.Show Doctor's  List 7.Exit\n");
                int choice = ScannerHelper.readInt("Enter Choice : ");
                switch(choice)
                {
                    case 1: doctorEntry(); break;
                    case 2: updateDoctor(); break;
                    case 3: String id = ScannerHelper.readString("Enter Doctor's ID : ");
                            repository.delete(id); break;
                    case 4: bulkEntry(); break;
                    case 5: viewAuditLogs(); break;
                    case 6: doctorsList(); break;
                    case 7: System.out.println("Shutting down admin clinic...goodbye"); exitSystem = true; break;
                    default:
                        System.out.println("Invalid choice");
                        Program.logger.warn(
                                "Invalid Menu Option Selected");
                }
            }
    }
    private static void doctorEntry() {

        System.out.println("Register the New Doctor Entry");

        int count = ScannerHelper.readInt("Enter Number of Doctors : ");

        List<Doctor> doctorList = repository.findAll();

        for (int i = 0; i < count; i++) {

            String name = ScannerHelper.readString("Enter Name : ");

            Specialization specialization =
                    ScannerHelper.readEnumChoice(
                            "Select Specialization",
                            Specialization.values());

            int experience =
                    ScannerHelper.readInt("Enter Experience : ");

            Shift shift =
                    ScannerHelper.readEnumChoice(
                            "Select Shift",
                            Shift.values());

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

            Doctor doctor = new Doctor(
                    id,
                    name,
                    specialization,
                    experience,
                    shift);

            repository.save(doctor);

            doctorList.add(doctor);

            Program.logger.info("Doctor Registered : " + id);

            System.out.println("Doctor Registered Successfully.");
        }
    }
    private static void bulkEntry()
    {
        System.out.println("Welcome to Bulk Entry");
        String filename = ScannerHelper.readString("Enter Filename : ");
        ArrayList<Doctor> importedDoctors = FileHandler.bulkLoadDoctors(filename, doctorsDetails.size());
        if(!importedDoctors.isEmpty())
        {
            doctorsDetails.addAll(importedDoctors);

            Program.logger.info(
                    "Doctor Registered : {}",
                    importedDoctors.size());
        }else{
            Program.logger.error(
                    "Upload failed or file was empty.");
        }
    }
    private static void viewAuditLogs() {

        AuditLogRepository repository =
                new AuditLogRepository();

        List<AuditLog> logs =
                repository.findAll();

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
    private static void doctorsList()
    {
        System.out.println("\n=========== DOCTOR LIST ===========");
        if(repository.findAll().isEmpty())
        {
            Program.logger.warn(
                    "No Doctors Found");
            return;
        }
        System.out.println("1.All Available Doctors List");
        System.out.println("2.Specialization Available Doctors List");
        System.out.println("3.Experience Available Doctors List");


            int choice = ScannerHelper.readInt("Enter Choice : ");
            switch (choice) {
                case 1:
                    System.out.println("Total Doctor List: " + repository.findAll().size());
                    repository.findAll().forEach(doctor -> System.out.println(doctor));
                    break;
                case 2:
                    getSpecificationDoctorList().forEach(doctor -> System.out.println(doctor));
                    break;
                case 3:
                    findByExperience().forEach(doctor -> System.out.println(doctor));
                    break;
                default:
                    System.out.println("Invalid choice");
            }
    }
    public static List<Doctor> getDoctorList()
    {
        return repository.findAll();
    }
    public static List<Doctor> getSpecificationDoctorList()
    {
        Specialization specialization = ScannerHelper.readEnumChoice("Select Specialization",Specialization.values());
         List<Doctor> splDoctor= repository.findBySpecification(specialization);
         return splDoctor;
    }
    private static List<Doctor> findByExperience()
    {
        int exp =  ScannerHelper.readInt("Enter Experience : ");
        return repository.findByExperience(exp);
    }
    private static boolean updateDoctor() {

        String id = ScannerHelper.readString("Enter Doctor ID : ");

        Doctor doctor = repository.findById(id);

        if (doctor == null) {
            System.out.println("No Doctor Found with ID : " + id);
            Program.logger.warn("Update Failed. Doctor not found : " + id);
            return false;
        }

        System.out.println("\nCurrent Doctor Details");
        System.out.println(doctor);

        String name = ScannerHelper.readString("Enter New Name : ");

        Specialization specialization =
                ScannerHelper.readEnumChoice(
                        "Select New Specialization",
                        Specialization.values());

        int experience =
                ScannerHelper.readInt("Enter New Experience : ");

        Shift shift =
                ScannerHelper.readEnumChoice(
                        "Select New Shift",
                        Shift.values());

        Doctor updatedDoctor = new Doctor(
                id,
                name,
                specialization,
                experience,
                shift
        );

        boolean updated = repository.update(updatedDoctor);

        if (updated) {
            System.out.println("Doctor Updated Successfully.");
            Program.logger.info("Doctor Updated : " + id);
        } else {
            System.out.println("Doctor Update Failed.");
            Program.logger.error("Doctor Update Failed : " + id);
        }

        return updated;
    }
}
