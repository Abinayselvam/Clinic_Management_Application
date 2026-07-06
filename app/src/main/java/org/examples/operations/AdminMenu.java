package org.examples.operations;

import org.examples.data.FileHandler;
import org.examples.enums.Shift;
import org.examples.enums.Specialization;
import org.examples.helper.ScannerHelper;
import org.examples.model.Doctor;
import java.util.ArrayList;

public class AdminMenu {
   private static ArrayList<Doctor> doctorsDetails = new ArrayList<>();
   //counter to track the serious id
    private static int idCounter =1;
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
            String  generatedID = String.format("D%04d", idCounter++);
            //Get Doctor Details
            String name = ScannerHelper.readString("Enter Name : ");
            Specialization specialization = ScannerHelper.readEnumChoice("Select Specialization",Specialization.values());
            int experience = ScannerHelper.readInt("Enter Experience : ");
            Shift shift= ScannerHelper.readEnumChoice( "Enter Shift : ",Shift.values());
            Doctor doctor = new Doctor(generatedID,name,specialization,experience,shift);
            doctorsDetails.add(doctor);

            System.out.println("\nDoctor Registered Successfully. Doctor ID:"+generatedID);
        }


    }
    private static void bulkEntry()
    {
        System.out.println("Welcome to Bulk Entry");
        String filename = ScannerHelper.readString("Enter Filename : ");
        ArrayList<Doctor> importedDoctors =
                FileHandler.bulkLoadDoctors(filename, doctorsDetails.size());
        if(!importedDoctors.isEmpty())
        {
            doctorsDetails.addAll(importedDoctors);
            idCounter += importedDoctors.size();
            System.out.println(importedDoctors.size()
                    + " Doctors Imported Successfully.");
        }else{
            System.out.println("Upload failed or file was empty.");
        }


    }
    private static void viewAuditLogs()
    {
        System.out.println("Welcome to View Audit Logs");
    }
    private static void doctorsList()
    {
        System.out.println("\n=========== DOCTOR LIST ===========");
        System.out.println("Total Doctor List: "+doctorsDetails.size());
       doctorsDetails.forEach(doctor -> System.out.println(doctor));


    }

}
