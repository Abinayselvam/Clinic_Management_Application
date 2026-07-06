package org.examples.helper;

import java.util.Scanner;

public class ScannerHelper {
    public static int readIntegerInputScanner(Scanner scanner) {
        try {
            int number = scanner.nextInt();
            scanner.nextLine();//clear buffer
            return number;
        }
        catch (Exception e) {
            scanner.nextLine();//clear the invalid input
            System.out.println("Error: please enter numeric value");
            return -1;
        }

    }
}
