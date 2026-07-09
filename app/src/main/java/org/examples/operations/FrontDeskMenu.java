package org.examples.operations;

import org.examples.enums.Gender;
import org.examples.enums.Specialization;
import org.examples.helper.ScannerHelper;
import org.examples.model.Appointment;
import org.examples.model.Doctor;
import org.examples.model.Patient;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class FrontDeskMenu {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Patient> patients = new ArrayList<>();
    private static List<Appointment> appointmentList = new ArrayList<>();
    private static int idConter = 1;

    public static void show() {
        boolean exitSystem = false;
        while (!exitSystem) {
            System.out.println("----WELCOME TO FRONT DESK MANAGEMENT----\n");
            System.out.println("Select persona 1.Register new patient 2.Book new appointment 3.View Patient Data 4.Exit\n");
            int choice = ScannerHelper.readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    registerPatient();
                    break;
                case 2:
                    bookAppointment();
                    break;
                case 3:
                    viewPatientDetails();
                    break;
                case 4:
                    System.out.println("Shutting down front desk management...Gud bye");
                    exitSystem = true;
                    break;
                default:
                    Program.logger.warn(
                            "Invalid Menu Option Selected");
            }
        }

    }

    private static void registerPatient() {
        System.out.println("Welcome to Register Patient Management System");
        String phone = ScannerHelper.readMobileNumber(
                scanner,
                "Enter Mobile Number : "
        );

        Patient existingPatient = findByMobileNumber(phone);

        if (existingPatient != null) {
           Program.logger.warn(
                    "Patient Already Registered.");
            System.out.println(existingPatient);
            System.out.println("Welcome back " + existingPatient.getName());
            return;
        }
           String id = String.format("P%04d", idConter++);
           String name = ScannerHelper.readString("Enter name: ");
           Gender gender = ScannerHelper.readEnumChoice("Gender: ", Gender.values());
           int age = ScannerHelper.readInt("Age: ");
           patients.add(new Patient(id, name, gender, age, phone));

            //uc13
       Program.logger.info(
                "Patient Registered : " +id);
        }

    private static void viewPatientDetails() {
        System.out.println("Welcome to View Patient Management System");
        if (patients.isEmpty()) {
            Program.logger.warn(
                    "There is no patient to view");
        } else {
            for (Patient p :patients) {
                System.out.println(p);
            }
        }

    }

    private static void bookAppointment() {
        System.out.println("Welcome to Book Appointment Management System");
        //1)Identify patient registered
        String phone = ScannerHelper.readMobileNumber(scanner, "Enter Mobile Number : ");
        Patient patient = findByMobileNumber(phone);
        if(patient==null)
        {
            Program.logger.warn(
                    "No Patient Registered " );
            registerPatient();
            return;
        }

        List<Doctor> doctors = AdminMenu.getDoctorList();

        Specialization specialization =
                ScannerHelper.readEnumChoice(

                        "Select Required Specialization",Specialization.values()
                );
        String slot = ScannerHelper.readAppointmentSlot(scanner);

        List<Doctor> availableDoctors =
                AdminMenu.getDoctorList()
                        .stream()
                        .filter(doctor ->
                                doctor.getSpecialization() == specialization)
                        .filter(doctor ->
                                doctor.isSlotAvailable(slot))
                        //uc11 filter check
                        .filter(doctor ->
                                doctor.isShiftAvailable(slot))
                        .toList();
        if(availableDoctors.isEmpty())
        {
         Program.logger.warn("No Doctors Available");
            return;
        }

        Random random = new Random();

        Doctor assignedDoctor =
                availableDoctors.get(
                        random.nextInt(availableDoctors.size())
                );

        assignedDoctor.bookSlot(slot);

        Appointment appointment =
                new Appointment(assignedDoctor,patient,slot);

        appointmentList.add(appointment);

        System.out.println();

        System.out.println("Appointment Booked Successfully.");
       Program.logger.info(
                "Appointment Booked for "
                        + patient.getName()
                        + " with "
                        + assignedDoctor.getName()
                        + " at "
                        + slot,
                "INFO");
        System.out.println(appointment);
    }

    private static Patient findByMobileNumber(String mobileNumber) {
        for (Patient p : patients) {
            if (p.getPhone().equals(mobileNumber)) {
                return p;
            }
        }
        return null;
    }
}
