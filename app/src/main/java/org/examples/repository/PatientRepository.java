package org.examples.repository;

import org.examples.data.DbConnection;
import org.examples.enums.Gender;
import org.examples.model.Patient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PatientRepository {

    public void savePatient(Patient patient) {
        String sql = "insert into patient values(?,?,?,?,?)";
        try(Connection conn = DbConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);)
        {
            pstmt.setString(1,patient.getId());
            pstmt.setString(2,patient.getName());
            pstmt.setString(3,patient.getGender().name());
            pstmt.setInt(4,patient.getAge());
            pstmt.setString(5,patient.getPhone());
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Patient> getAllPatients() {
        String sql = "SELECT * FROM patient";
        try(Connection conn = DbConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);)
        {
            stmt.setQueryTimeout(10);
            ResultSet rs = stmt.executeQuery();
            List<Patient> patients = new ArrayList<Patient>();
            while (rs.next()) {
                Patient patient = new Patient(
                        rs.getString("id"),
                        rs.getString("name"),
                        Gender.valueOf( rs.getString("gender")),
                        rs.getInt("age"),
                        rs.getString("phone")
                );
                patients.add(patient);
            }
            return patients;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generatePatientId() {
        String sql = "SELECT MAX(id) AS last_id FROM patient";

        try (Connection connection = DbConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {

                String lastId = rs.getString("last_id");

                if (lastId == null) {
                    return "P0001";
                }

                int number = Integer.parseInt(lastId.substring(1));
                return String.format("P%04d", number + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "P0001";
    }
    public Patient getById(String id) {

        String sql = "SELECT * FROM patient WHERE id=?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Patient patient = new Patient();

                patient.setId(rs.getString("id"));
                patient.setName(rs.getString("name"));
                patient.setGender(Gender.valueOf(rs.getString("gender")));
                patient.setAge(rs.getInt("age"));
                patient.setPhone(rs.getString("phone"));

                return patient;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
