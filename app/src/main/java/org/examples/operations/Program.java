package org.examples.operations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.examples.helper.ScannerHelper;

public class Program {

    private static final int ROLE_ADMIN = 1;
    private static final int ROLE_FRONTDESK = 2;
    private static final int EXIT = 3;

    public static final Logger logger =
            LogManager.getLogger(Program.class);

    public static void startup() {

        boolean exitSystem = false;

        System.out.println("Welcome to the Clinic Management Application");

        while (!exitSystem) {

            System.out.println("-----*** Main Menu ***-----");
            System.out.println("Select persona 1. Admin | 2. Front Desk | 3. Exit");

            int choice = ScannerHelper.readInt("Enter your choice: ");

            switch (choice) {

                case ROLE_ADMIN:
                    AdminMenu.show();
                    break;

                case ROLE_FRONTDESK:
                    FrontDeskMenu.show();
                    break;

                case EXIT:
                    System.out.println("Thank you for using TownClinic.");
                    exitSystem = true;
                    break;

                default:
                    System.out.println("Invalid choice");
                    logger.warn("Invalid Menu Option Selected");
            }
        }
    }
}