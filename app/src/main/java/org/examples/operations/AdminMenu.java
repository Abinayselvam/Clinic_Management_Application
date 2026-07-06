package org.examples.operations;

import org.examples.helper.ScannerHelper;

import java.util.Scanner;

public class AdminMenu {
    // Doctor 1
    private static String doctorName1;
    private static String specialization1;
    private static int experience1;
    private static String shift1;

    // Doctor 2
    private static String doctorName2;
    private static String specialization2;
    private static int experience2;
    private static String shift2;

    // Doctor 3
    private static String doctorName3;
    private static String specialization3;
    private static int experience3;
    private static String shift3;
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
        System.out.println("\nEnter Doctor 1 Details");

        doctorName1 = ScannerHelper.readString("Name : ");
        specialization1 = ScannerHelper.readString("Specialization : ");
        experience1 = ScannerHelper.readInt("Experience : ");
        shift1 = ScannerHelper.readString("Shift : ");

        System.out.println("\nEnter Doctor 2 Details");

        doctorName2 = ScannerHelper.readString("Name : ");
        specialization2 = ScannerHelper.readString("Specialization : ");
        experience2 = ScannerHelper.readInt("Experience : ");
        shift2 = ScannerHelper.readString("Shift : ");

        System.out.println("\nEnter Doctor 3 Details");

        doctorName3 = ScannerHelper.readString("Name : ");
        specialization3 = ScannerHelper.readString("Specialization : ");
        experience3 = ScannerHelper.readInt("Experience : ");
        shift3 = ScannerHelper.readString("Shift : ");

        System.out.println("\nDoctors Registered Successfully.");

    }
    private static void bulkEntry()
    {
      System.out.println("Welcome to Bulk Entry");
    }
    private static void viewAuditLogs()
    {
        System.out.println("Welcome to View Audit Logs");
    }
    private static void doctorsList()
    {
        System.out.println("\n=========== DOCTOR LIST ===========");

        if (doctorName1 == null) {
            System.out.println("No Doctors Registered.");
            return;
        }

        System.out.println("--------------------------------");
        System.out.println("Doctor 1");
        System.out.println("Name           : " + doctorName1);
        System.out.println("Specialization : " + specialization1);
        System.out.println("Experience     : " + experience1);
        System.out.println("Shift          : " + shift1);

        System.out.println("--------------------------------");
        System.out.println("Doctor 2");
        System.out.println("Name           : " + doctorName2);
        System.out.println("Specialization : " + specialization2);
        System.out.println("Experience     : " + experience2);
        System.out.println("Shift          : " + shift2);

        System.out.println("--------------------------------");
        System.out.println("Doctor 3");
        System.out.println("Name           : " + doctorName3);
        System.out.println("Specialization : " + specialization3);
        System.out.println("Experience     : " + experience3);
        System.out.println("Shift          : " + shift3);

        System.out.println("--------------------------------");

    }

}
