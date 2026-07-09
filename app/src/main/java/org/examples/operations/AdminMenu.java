package org.examples.operations;

import org.examples.data.FileHandler;
import org.examples.enums.Shift;
import org.examples.enums.Specialization;
import org.examples.helper.ScannerHelper;
import org.examples.model.Doctor;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class AdminMenu {
     private static List<Doctor> doctorsDetails = new ArrayList<>();
   //counter to track the serious id
    private static int idCounter =1;
    private static final String LOG_FILE =
            "C:\\Users\\Abinaya S\\OneDrive\\Desktop\\JAVA\\Clinic_Management_Application\\applogs\\application_logs.log";
    public static void show()
    {

        boolean exitSystem = false;
        while(!exitSystem)
            {
                System.out.println("----WELCOME TO CLINIC ADMIN MENU----\n");
                System.out.println("Select persona 1.Doctor's Entry 2.Bulk Entry(csv) 3.View Audit Logs 4.Show Doctor's List 5.Exit\n");
                int choice = ScannerHelper.readInt("Enter Choice : ");
                switch(choice)
                {
                    case 1: doctorEntry(); break;
                    case 2: bulkEntry(); break;
                    case 3: viewAuditLogs(); break;
                    case 4: doctorsList(); break;
                    case 5:System.out.println("Shutting down admin clinic...goodbye"); exitSystem = true; break;
                    default:
                        System.out.println("Invalid choice");
                        Program.logger.warn(
                                "Invalid Menu Option Selected");
                }

            }
    }
    private static void doctorEntry()
    {
        System.out.println("Register the New Doctor Entry");
        //Generate the ID automatically
        //String.formate ensure it looks like D001,D002.....
        System.out.println("How many doctors you want to register ?");
        int numberOfDoctors = ScannerHelper.readInt("Enter Number of Doctors : ");
        for(int i=0;i<numberOfDoctors;i++)
        {

            //Get Doctor Details
            String name = ScannerHelper.readString("Enter Name : ");
            Specialization specialization = ScannerHelper.readEnumChoice("Select Specialization",Specialization.values());
            int experience = ScannerHelper.readInt("Enter Experience : ");
            Shift shift= ScannerHelper.readEnumChoice( "Select Shift ",Shift.values());
            boolean duplicate = false;

            for (Doctor doctor : doctorsDetails) {
                if (doctor.getName().equalsIgnoreCase(name)
                        && doctor.getSpecialization() == specialization
                        && doctor.getExperience() == experience) {

                    Program.logger.warn("Doctor already exists.");
                    duplicate = true;
                    break;
                }
            }

            if (duplicate) {
                continue;   // Skip only this doctor, continue with the next one
            }
            String  generatedID = String.format("D%04d", idCounter++);
            Doctor doctor = new Doctor(generatedID,name,specialization,experience,shift);
            doctorsDetails.add(doctor);
            Program.logger.info(
                    "Doctor Registered : " + doctor.getId());
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

        System.out.println("========= AUDIT LOGS =========");

        Path path = Paths.get(
                LOG_FILE);

        if (!Files.exists(path)) {
            System.out.println("No Audit Logs Found.");
            return;
        }

        try (Stream<String> logs = Files.lines(path)) {

            logs.forEach(System.out::println);

        } catch (IOException e) {

            System.out.println("Unable to read log file.");
        }

        System.out.println("==============================");
    }
    private static void doctorsList()
    {
        System.out.println("\n=========== DOCTOR LIST ===========");
        if(doctorsDetails.isEmpty())
        {
            Program.logger.warn(
                    "No Doctors Found");
        }
        System.out.println("Total Doctor List: "+doctorsDetails.size());

       doctorsDetails.forEach(doctor -> System.out.println(doctor));

    }
    public static List<Doctor> getDoctorList()
    {
        return doctorsDetails;
    }

}
