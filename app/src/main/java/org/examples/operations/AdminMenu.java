package org.examples.operations;

import org.examples.helper.ScannerHelper;

import java.util.Scanner;

public class AdminMenu {
    public static void show()
    {
        Scanner sc = new Scanner(System.in);
        boolean exitSystem = false;

        while(!exitSystem)
            {
                System.out.println("----WELCOME TO CLINIC ADMIN MENU----\n");
                System.out.println("Select persona 1.Doctor's Entry 2.Bulk Entry(csv) 3.View Audit Logs 4.Exit\n");
                int choice = ScannerHelper.readIntegerInputScanner(sc);
                switch(choice)
                {
                    case 1: doctorEntry(); break;
                    case 2: bulkEntry(); break;
                    case 3: viewAuditLogs(); break;
                    case 4:System.out.println("Shutting down admin clinic...goodbye"); exitSystem = true; break;
                    default:
                        System.out.println("Invalid choice");

                }

            }
    }
    private static void doctorEntry()
    {
        System.out.println("Welcome to Doctor Entry");

    }
    private static void bulkEntry()
    {
      System.out.println("Welcome to Bulk Entry");
    }
    private static void viewAuditLogs()
    {
        System.out.println("Welcome to View Audit Logs");

    }

}
