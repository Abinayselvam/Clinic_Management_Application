package org.examples.operations;

import org.examples.helper.ScannerHelper;

import java.util.Scanner;

public class Program {
    private static final int ROLE_ADMIN = 1;
    private static final int ROLE_FRONTDESK = 2;
    private static final int EXIT = 3;
    public static void startup() {
        Scanner scanner = new Scanner(System.in);
        boolean exitSystem = false;
        System.out.println("Welcome to the Clinic Management Application");
        while (!exitSystem) {
            System.out.print("-----*** Main Menu ***-----\n");
            System.out.print("Select persona 1. Admin | 2. Front Desktop | 3. Exit \n");
            System.out.print("Enter your choice: ");
            int choice = ScannerHelper.readIntegerInputScanner(scanner);
            //using switch for better readability
            switch (choice) {
                case ROLE_ADMIN:
                    AdminMenu.show();
                    break;
                case ROLE_FRONTDESK:
                    FrontDeskMenu.show();
                    break;
                case EXIT:
                    System.out.println("\nThank you for using TownClinic.");
                    System.out.println("Good Bye!");
                    exitSystem = true;
                    break;
                default:
                    System.out.println("Invalid choice");

            }
        }
    }
}
