package org.examples.repository;

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
    }
}
