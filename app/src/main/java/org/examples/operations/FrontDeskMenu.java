package org.examples.operations;

import org.examples.enums.Gender;
import org.examples.helper.ScannerHelper;
import org.examples.model.Patient;
import java.util.ArrayList;
import java.util.Scanner;


public class FrontDeskMenu {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Patient>  patients = new ArrayList<>();
    private static int idConter =1;
  public static void show()
  {
      boolean exitSystem = false;
            while (!exitSystem) {
                System.out.println("----WELCOME TO FRONT DESK MANAGEMENT----\n");
                System.out.println("Select persona 1.Register new patient 2.Book new appointment 3.View Patient Data 4.Exit\n");
                int choice = ScannerHelper.readInt("Enter your choice: ");
          switch(choice)
          {
              case 1: registerPatient(); break;
              case 2: bookAppointment(); break;
              case 3: viewPatientDetails(); break;
              case 4: System.out.println("Shutting down front desk management...Gud bye"); exitSystem = true; break;
              default: System.out.println("Invalid choice");
          }
      }

  }
  private static void registerPatient()
  {
      System.out.println("Welcome to Register Patient Management System");
      System.out.println("How many patient to register ?");
      int numOfPatient = ScannerHelper.readInt("Enter patient number: ");
      for(int i=0;i<numOfPatient;i++)
      {
          String id = String.format("P%04d",idConter++);
          String name = ScannerHelper.readString("Enter name: ");
          Gender gender = ScannerHelper.readEnumChoice("Gender: ",Gender.values());
          int age = ScannerHelper.readInt("Age: ");
          String phone = ScannerHelper.readMobileNumber(scanner,"Phone number: ");
          patients.add(new Patient(id,name,gender,age,phone));
          System.out.println("Patient "+id+" registered");
      }

  }
  private static void viewPatientDetails()
  {
      System.out.println("Welcome to View Patient Management System");
      if(patients.isEmpty())
      {
          System.out.println("There is no patient to view");
      }
      else {
          for(Patient p:patients)
          {
              System.out.println(p);
          }
      }

  }
  private static void bookAppointment()
  {
      System.out.println("Welcome to Book Appointment Management System");

  }

}
