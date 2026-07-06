package org.examples.operations;

import org.examples.helper.ScannerHelper;

import java.util.Scanner;

public class FrontDeskMenu {
  public static void show()
  {
      Scanner sc = new Scanner(System.in);
      boolean exitSystem = false;
            while (!exitSystem) {
                System.out.println("----WELCOME TO FRONT DESK MANAGEMENT----\n");
                System.out.println("Select persona 1.Register new patient 2.Book new appointment 3.Exit\n");
                int choice = ScannerHelper.readIntegerInputScanner(sc);
          switch(choice)
          {
              case 1: registerPatient(); break;
              case 2: bookAppointment(); break;
              case 3: System.out.println("Shutting down front desk management...Gud bye"); exitSystem = true; break;
              default: System.out.println("Invalid choice");
          }
      }

  }
  private static void registerPatient()
  {
      System.out.println("Welcome to Register Patient Management System");

  }
  private static void bookAppointment()
  {
      System.out.println("Welcome to Book Appointment Management System");

  }

}
