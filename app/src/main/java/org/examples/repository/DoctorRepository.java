package org.examples.repository;

import org.examples.data.DbConnection;
import org.examples.enums.Shift;
import org.examples.enums.Specialization;
import org.examples.model.Doctor;
import org.examples.operations.Program;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DoctorRepository {
    public void save(Doctor doctor) {

        String sql = "INSERT INTO doctor VALUES (?,?,?,?,?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, doctor.getId());   // <-- use doctor's id
            ps.setString(2, doctor.getName());
            ps.setString(3, doctor.getSpecification().name());
            ps.setInt(4, doctor.getExperience());
            ps.setString(5, doctor.getShift().name());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Doctor> findAll() {
      List<Doctor> doctors = new ArrayList<>();
        String sql = "select * from Doctor";
        try (Connection connection = DbConnection.getConnection(); Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery(sql)) {
            while (rs.next()) {
                Doctor doctor = new Doctor(
                        rs.getString("id"),
                        rs.getString("name"),
                        Specialization.valueOf(rs.getString("specification")),
                        rs.getInt("experience"),
                        Shift.valueOf(rs.getString("shift"))
                );
                    doctors.add(doctor);
            }
            return doctors;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Doctor> findBySpecification(Specialization specification) {
        String sql = "select * from Doctor where specification = ?";
        List<Doctor> splDoctors = new ArrayList<>();
        try (Connection connection = DbConnection.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, specification.name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Doctor doctor = new Doctor(
                        rs.getString("id"),
                        rs.getString("name"),
                        Specialization.valueOf(rs.getString("specification")),
                        rs.getInt("experience"),
                        Shift.valueOf(rs.getString("shift"))
                );
                if (specification.name().equals(doctor.getSpecification().name())) {
                }
                splDoctors.add(doctor);
            }
            return splDoctors;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Doctor> findByExperience(int experience) {
        String sql = "select * from Doctor where experience = ?";
        List<Doctor> expDoctors = new ArrayList<>();
        try (Connection connection = DbConnection.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, experience);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Doctor doctor = new Doctor(
                        rs.getString("id"),
                        rs.getString("name"),
                        Specialization.valueOf(rs.getString("specification")),
                        rs.getInt("experience"),
                        Shift.valueOf(rs.getString("shift"))
                );

                expDoctors.add(doctor);
            }
            return expDoctors;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Doctor findById(String id) {
        String sql = "select * from Doctor where id = ?";
        try(Connection connection = DbConnection.getConnection(); PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Doctor doctor = new Doctor(
                        rs.getString("id"),
                        rs.getString("name"),
                        Specialization.valueOf(rs.getString("specification")),
                        rs.getInt("experience"),
                        Shift.valueOf(rs.getString("shift"))
                );
                return doctor;
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(Doctor doctor) {

        String sql = "UPDATE doctor SET name=?, specification=?, experience=?, shift=? WHERE id=?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, doctor.getName());
            ps.setString(2, doctor.getSpecification().name());
            ps.setInt(3, doctor.getExperience());
            ps.setString(4, doctor.getShift().name());
            ps.setString(5, doctor.getId());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean delete(String id) {
        String sql = "DELETE from doctor where id = ?";
        try (Connection connection = DbConnection.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public String generateDoctorId() {

        String sql = "SELECT MAX(id) AS last_id FROM doctor";

        try (Connection connection = DbConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {

                String lastId = rs.getString("last_id");

                if (lastId == null) {
                    return "D0001";
                }

                int number = Integer.parseInt(lastId.substring(1));
                return String.format("D%04d", number + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "D0001";
    }
}
