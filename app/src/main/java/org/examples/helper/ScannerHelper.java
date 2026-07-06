package org.examples.helper;

import java.util.Scanner;

public class ScannerHelper {
    private static final Scanner scanner = new Scanner(System.in);

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
}
