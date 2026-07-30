package org.examples.repository;

<<<<<<< HEAD
import org.examples.model.Patient;
import org.examples.util.HibernateUtil;
import org.examples.util.IdGenerator;

import java.util.List;

/**
 * Data-access operations for {@link Patient} entities.
 */
public class PatientRepository {

    private static final String ID_PREFIX = "P";

    public void savePatient(Patient patient) {
        HibernateUtil.doInTransaction(session -> {
            session.persist(patient);
            return null;
        });
    }

    public List<Patient> getAllPatients() {
        return HibernateUtil.doInSession(session ->
                session.createQuery("from Patient", Patient.class).list());
    }

    public Patient getById(String id) {
        return HibernateUtil.doInSession(session ->
                session.get(Patient.class, id));
    }

    /** @return the patient registered under this mobile number, or null */
    public Patient findByPhone(String phone) {
        return HibernateUtil.doInSession(session ->
                session.createQuery(
                                "from Patient p where p.phone = :phone",
                                Patient.class)
                        .setParameter("phone", phone)
                        .uniqueResultOptional()
                        .orElse(null));
    }

    /** @return true if a patient with this ID existed and was updated */
    public boolean update(Patient patient) {
        return Boolean.TRUE.equals(HibernateUtil.doInTransaction(session -> {
            if (session.get(Patient.class, patient.getId()) == null) {
                return false;
            }
            session.merge(patient);
            return true;
        }));
    }

    /** @return true if a patient with this ID existed and was deleted */
    public boolean delete(String id) {
        return Boolean.TRUE.equals(HibernateUtil.doInTransaction(session -> {
            Patient patient = session.get(Patient.class, id);
            if (patient == null) {
                return false;
            }
            session.remove(patient);
            return true;
        }));
    }

    public String generatePatientId() {
        List<String> existingIds = HibernateUtil.doInSession(session ->
                session.createQuery("select p.id from Patient p", String.class).list());

        return IdGenerator.nextId(existingIds, ID_PREFIX);
=======
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
>>>>>>> 7bb398e2a56e4a9475b741915125afbb57a30061
    }
}
