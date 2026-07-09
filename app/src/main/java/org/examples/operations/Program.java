package org.examples.operations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.examples.helper.ScannerHelper;

public class Program {
    private static final int ROLE_ADMIN = 1;
    private static final int ROLE_FRONTDESK = 2;
    private static final int EXIT = 3;
    public static final Logger logger = LogManager.getLogger(Program.class);
    public static void startup() {
        boolean exitSystem = false;
        System.out.println("Welcome to the Clinic Management Application");
        while (!exitSystem) {
            System.out.print("-----*** Main Menu ***-----\n");
            System.out.print("Select persona 1. Admin | 2. Front Desktop | 3. Exit \n");
            int choice = ScannerHelper.readInt("Enter your choice: ");
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
                    logger.warn(
                            "Invalid Menu Option Selected");
            }
        }
    }
}
