package org.examples.operations;

import org.examples.enums.Gender;
import org.examples.enums.Specialization;
import org.examples.helper.ScannerHelper;
import org.examples.model.Appointment;
import org.examples.model.Doctor;
import org.examples.model.Patient;
import org.examples.repository.AppointmentRepository;
import org.examples.repository.DoctorRepository;
import org.examples.repository.PatientRepository;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class FrontDeskMenu {

    private static final Scanner scanner = new Scanner(System.in);

    private static PatientRepository repository = new PatientRepository();

    private static DoctorRepository doctorRepository =
            new DoctorRepository();

    private static AppointmentRepository aRepository =
            new AppointmentRepository(
                    doctorRepository,
                    repository
            );

    public FrontDeskMenu(PatientRepository repo,
                         AppointmentRepository appointmentRepo) {

        repository = repo;
        aRepository = appointmentRepo;
    }

    public static void show() {

        boolean exitSystem = false;

        while (!exitSystem) {

            System.out.println("\n====== FRONT DESK MENU ======");
            System.out.println("1. Register Patient");
            System.out.println("2. Book Appointment");
            System.out.println("3. View Patients");
            System.out.println("4. View Appointments");
            System.out.println("5. Search Appointment");
            System.out.println("6. Patient Appointment History");
            System.out.println("7. Doctor Appointment History");
            System.out.println("8. Update Appointment Slot");
            System.out.println("9. Cancel Appointment");
            System.out.println("10. Exit");

            int choice =
                    ScannerHelper.readInt("Enter Choice : ");

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
                    viewAppointments();
                    break;

                case 5:
                    searchAppointment();
                    break;

                case 6:
                    patientAppointmentHistory();
                    break;

                case 7:
                    doctorAppointmentHistory();
                    break;

                case 8:
                    updateAppointmentSlot();
                    break;

                case 9:
                    cancelAppointment();
                    break;

                case 10:
                    exitSystem = true;
                    System.out.println("Logging Out...");
                    break;

                default:
                    Program.logger.warn("Invalid Menu Option Selected");
            }
        }
    }

    private static void registerPatient() {

        System.out.println("\n===== Patient Registration =====");

        String phone =
                ScannerHelper.readMobileNumber(
                        scanner,
                        "Enter Mobile Number : ");

        Patient existingPatient =
                findByMobileNumber(phone);

        if (existingPatient != null) {

            System.out.println(existingPatient);
            System.out.println("Patient Already Registered.");

            return;
        }

        String id = String.format(
                "P%04d",
                repository.getAllPatients().size() + 1);

        String name =
                ScannerHelper.readString("Enter Name : ");

        Gender gender =
                ScannerHelper.readEnumChoice(
                        "Select Gender",
                        Gender.values());

        int age =
                ScannerHelper.readInt("Enter Age : ");

        Patient patient =
                new Patient(id, name, gender, age, phone);

        repository.savePatient(patient);

        Program.logger.info("Patient Registered : {}", id);

        System.out.println("Patient Registered Successfully.");
    }

    private static void viewPatientDetails() {

        List<Patient> patients =
                repository.getAllPatients();

        if (patients.isEmpty()) {

            System.out.println("No Patients Found.");
            return;
        }

        patients.forEach(System.out::println);
    }

    private static void bookAppointment() {

        System.out.println("\n===== Appointment Booking =====");

        String appointmentId =
                aRepository.generateAppointmentId();

        String phone =
                ScannerHelper.readMobileNumber(
                        scanner,
                        "Enter Patient Mobile Number : ");

        Patient patient =
                findByMobileNumber(phone);

        if (patient == null) {

            System.out.println("Please Register Patient First.");
            return;
        }

        Specialization specialization =
                ScannerHelper.readEnumChoice(
                        "Select Specialization",
                        Specialization.values());

        String slot =
                ScannerHelper.readAppointmentSlot(scanner);

        List<Doctor> availableDoctors =
                AdminMenu.getDoctorList()
                        .stream()
                        .filter(d ->
                                d.getSpecialization() == specialization)
                        .filter(d ->
                                d.isShiftAvailable(slot))
                        .filter(d ->
                                aRepository.isDoctorAvailable(
                                        d.getId(),
                                        slot))
                        .toList();

        if (availableDoctors.isEmpty()) {

            System.out.println("No Doctor Available.");
            return;
        }

        Doctor assignedDoctor =
                availableDoctors.get(
                        new Random().nextInt(
                                availableDoctors.size()));

        Appointment appointment =
                new Appointment(
                        appointmentId,
                        assignedDoctor,
                        patient,
                        slot);

        aRepository.saveAppointment(appointment);

        System.out.println("\nAppointment Booked Successfully");
        System.out.println("--------------------------------");
        System.out.println("Appointment ID : " + appointmentId);
        System.out.println("Patient        : " + patient.getName());
        System.out.println("Doctor         : " + assignedDoctor.getName());
        System.out.println("Specialization : " + assignedDoctor.getSpecialization());
        System.out.println("Slot           : " + slot);

        Program.logger.info(
                "Appointment Booked : {}",
                appointmentId);
    }

    private static void viewAppointments() {

        List<Appointment> appointments =
                aRepository.getAllAppointment();

        if (appointments.isEmpty()) {

            System.out.println("No Appointments Found.");
            return;
        }

        appointments.forEach(System.out::println);
    }

    private static void searchAppointment() {

        String id =
                ScannerHelper.readString(
                        "Enter Appointment ID : ");

        Appointment appointment =
                aRepository.findById(id);

        if (appointment == null) {

            System.out.println("Appointment Not Found.");
            return;
        }

        System.out.println(appointment);
    }

    private static void patientAppointmentHistory() {

        String patientId =
                ScannerHelper.readString(
                        "Enter Patient ID : ");

        List<Appointment> appointments =
                aRepository.findByPatientId(patientId);

        if (appointments.isEmpty()) {

            System.out.println("No Appointments Found.");
            return;
        }

        appointments.forEach(System.out::println);
    }

    private static void doctorAppointmentHistory() {

        String doctorId =
                ScannerHelper.readString(
                        "Enter Doctor ID : ");

        List<Appointment> appointments =
                aRepository.findByDoctorId(doctorId);

        if (appointments.isEmpty()) {

            System.out.println("No Appointments Found.");
            return;
        }

        appointments.forEach(System.out::println);
    }

    private static void updateAppointmentSlot() {

        String appointmentId =
                ScannerHelper.readString(
                        "Enter Appointment ID : ");

        String slot =
                ScannerHelper.readAppointmentSlot(scanner);

        boolean updated =
                aRepository.updateSlot(
                        appointmentId,
                        slot);

        if (updated) {

            System.out.println("Appointment Updated Successfully.");
        } else {

            System.out.println("Appointment Not Found.");
        }
    }

    private static void cancelAppointment() {

        String appointmentId =
                ScannerHelper.readString(
                        "Enter Appointment ID : ");

        boolean deleted =
                aRepository.deleteAppointment(
                        appointmentId);

        if (deleted) {

            System.out.println("Appointment Cancelled Successfully.");
        } else {

            System.out.println("Appointment Not Found.");
        }
    }

    private static Patient findByMobileNumber(String mobileNumber) {

        for (Patient patient : repository.getAllPatients()) {

            if (patient.getPhone().equals(mobileNumber)) {

                return patient;
            }
        }

        return null;
    }
}