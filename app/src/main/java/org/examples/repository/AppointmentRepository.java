package org.examples.repository;

import org.examples.data.DbConnection;
import org.examples.model.Appointment;
import org.examples.model.Doctor;
import org.examples.model.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {

    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;

    public AppointmentRepository(DoctorRepository doctorRepository,
                                 PatientRepository patientRepository) {

        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    // ==========================
    // Save Appointment
    // ==========================
    public void saveAppointment(Appointment appointment) {

        String sql = "INSERT INTO appointment VALUES (?,?,?,?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, appointment.getId());
            stmt.setString(2, appointment.getDoctor().getId());
            stmt.setString(3, appointment.getPatient().getId());
            stmt.setString(4, appointment.getSlot());

            stmt.executeUpdate();

            System.out.println("Appointment Saved Successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==========================
    // Generate Appointment ID
    // ==========================
    public String generateAppointmentId() {

        String sql = "SELECT MAX(appointment_id) last_id FROM appointment";

        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {

                String lastId = rs.getString("last_id");

                if (lastId == null)
                    return "A0001";

                int number = Integer.parseInt(lastId.substring(1));

                return String.format("A%04d", number + 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "A0001";
    }

    // ==========================
    // Get All Appointments
    // ==========================
    public List<Appointment> getAllAppointment() {

        List<Appointment> appointments = new ArrayList<>();

        String sql = "SELECT * FROM appointment";

        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Doctor doctor =
                        doctorRepository.findById(rs.getString("doctor_id"));

                Patient patient =
                        patientRepository.getById(rs.getString("patient_id"));

                appointments.add(new Appointment(
                        rs.getString("appointment_id"),
                        doctor,
                        patient,
                        rs.getString("slot")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return appointments;
    }

    // ==========================
    // Find Appointment By ID
    // ==========================
    public Appointment findById(String appointmentId) {

        String sql =
                "SELECT * FROM appointment WHERE appointment_id=?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, appointmentId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Doctor doctor =
                        doctorRepository.findById(rs.getString("doctor_id"));

                Patient patient =
                        patientRepository.getById(rs.getString("patient_id"));

                return new Appointment(
                        rs.getString("appointment_id"),
                        doctor,
                        patient,
                        rs.getString("slot")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ==========================
    // Find By Patient ID
    // ==========================
    public List<Appointment> findByPatientId(String patientId) {

        List<Appointment> appointments = new ArrayList<>();

        String sql =
                "SELECT * FROM appointment WHERE patient_id=?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, patientId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Doctor doctor =
                        doctorRepository.findById(rs.getString("doctor_id"));

                Patient patient =
                        patientRepository.getById(patientId);

                appointments.add(new Appointment(
                        rs.getString("appointment_id"),
                        doctor,
                        patient,
                        rs.getString("slot")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return appointments;
    }

    // ==========================
    // Find By Doctor ID
    // ==========================
    public List<Appointment> findByDoctorId(String doctorId) {

        List<Appointment> appointments = new ArrayList<>();

        String sql =
                "SELECT * FROM appointment WHERE doctor_id=?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, doctorId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Doctor doctor =
                        doctorRepository.findById(doctorId);

                Patient patient =
                        patientRepository.getById(rs.getString("patient_id"));

                appointments.add(new Appointment(
                        rs.getString("appointment_id"),
                        doctor,
                        patient,
                        rs.getString("slot")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return appointments;
    }

    // ==========================
    // Find By Slot
    // ==========================
    public List<Appointment> findBySlot(String slot) {

        List<Appointment> appointments = new ArrayList<>();

        String sql =
                "SELECT * FROM appointment WHERE slot=?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, slot);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Doctor doctor =
                        doctorRepository.findById(rs.getString("doctor_id"));

                Patient patient =
                        patientRepository.getById(rs.getString("patient_id"));

                appointments.add(new Appointment(
                        rs.getString("appointment_id"),
                        doctor,
                        patient,
                        slot
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return appointments;
    }

    // ==========================
    // Check Doctor Availability
    // ==========================
    public boolean isDoctorAvailable(String doctorId, String slot) {

        String sql =
                "SELECT COUNT(*) FROM appointment WHERE doctor_id=? AND slot=?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, doctorId);
            stmt.setString(2, slot);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) == 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==========================
    // Update Slot
    // ==========================
    public boolean updateSlot(String appointmentId,
                              String slot) {

        String sql =
                "UPDATE appointment SET slot=? WHERE appointment_id=?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, slot);
            stmt.setString(2, appointmentId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==========================
    // Delete Appointment
    // ==========================
    public boolean deleteAppointment(String appointmentId) {

        String sql =
                "DELETE FROM appointment WHERE appointment_id=?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, appointmentId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}