package org.examples.helper;

import org.examples.model.Patient;

import java.util.Scanner;

public class ScannerHelper {
    private static final Scanner scanner = new Scanner(System.in);
    //Standardized 30 minutes slots
    public static final String[] APPOINTMENT_SLOTS ={
       "09:00 AM", "09:30 AM" ,"10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 AM", "12:30 AM",
       "04:00 PM", "04:30 PM" ,"05:00 PM", "05:30 PM", "06:00 PM", "06:30 PM", "07:00 PM", "07:30 PM",
    };

    public static int readInt(String prompt) {

        while (true) {

            System.out.print(prompt);

            try {
                return Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {

                System.out.println("Invalid Input! Please enter only numbers.");
            }
        }
    }

    public static String readString(String prompt) {

        while (true) {

            System.out.print(prompt);

            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Input cannot be empty.");
        }

    }
    // Generic Enum Reader
    public static <T extends Enum<T>> T readEnumChoice(
            String title,
            T[] values) {

        while (true) {

            System.out.println("\n" + title);

            for (int i = 0; i < values.length; i++) {

                System.out.println((i + 1) + ". " + values[i]);
            }

            int choice = readInt("Enter Choice : ");

            if (choice >= 1 && choice <= values.length) {

                return values[choice - 1];
            }

            System.out.println("Invalid Choice.");
        }
    }
    public static String readMobileNumber(Scanner scanner,String prompt) {
        String mobileRegex = "^[6-9]\\d{9}$";
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine();
            if(value.matches(mobileRegex)) {
                return value;
            }else{
                System.out.println("Invalid Mobile Number.");
                System.out.println("Indian Mobile Number Start with 6,7,8 and 9.");
            }
        }
    }
    public static String readAppointmentSlot(Scanner scanner) {

        System.out.println("\nAvailable Slots");

        for(int i=0;i<APPOINTMENT_SLOTS.length;i++)
        {
            System.out.println((i+1)+". "+APPOINTMENT_SLOTS[i]);
        }

        while(true)
        {
            int choice = readInt("Choose Slot : ");

            if(choice>=1 && choice<=APPOINTMENT_SLOTS.length)
            {
                return APPOINTMENT_SLOTS[choice-1];
            }

            System.out.println("Invalid Slot.");
        }
    }




}
