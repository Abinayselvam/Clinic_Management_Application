package org.examples.repository;

import org.examples.enums.Specification;
import org.examples.model.Doctor;
import org.examples.util.HibernateUtil;
import org.examples.util.IdGenerator;

import java.util.List;

/**
 * Data-access operations for {@link Doctor} entities. All queries go
 * through {@link HibernateUtil}; no raw JDBC is used anywhere here.
 */
public class DoctorRepository {

    private static final String ID_PREFIX = "D";

    public void save(Doctor doctor) {
        HibernateUtil.doInTransaction(session -> {
            session.persist(doctor);
            return null;
        });
    }

    public List<Doctor> findAll() {
        return HibernateUtil.doInSession(session ->
                session.createQuery("from Doctor", Doctor.class).list());
    }

    public List<Doctor> findBySpecification(Specification specialization) {
        return HibernateUtil.doInSession(session ->
                session.createQuery(
                                "from Doctor d where d.specialization = :spec",
                                Doctor.class)
                        .setParameter("spec", specialization)
                        .list());
    }

    public List<Doctor> findByExperience(int experience) {
        return HibernateUtil.doInSession(session ->
                session.createQuery(
                                "from Doctor d where d.experience = :exp",
                                Doctor.class)
                        .setParameter("exp", experience)
                        .list());
    }

    public Doctor findById(String id) {
        return HibernateUtil.doInSession(session ->
                session.get(Doctor.class, id));
    }

    /** @return true if a doctor with this ID existed and was updated */
    public boolean update(Doctor doctor) {
        return Boolean.TRUE.equals(HibernateUtil.doInTransaction(session -> {
            if (session.get(Doctor.class, doctor.getId()) == null) {
                return false;
            }
            session.merge(doctor);
            return true;
        }));
    }

    /** @return true if a doctor with this ID existed and was deleted */
    public boolean delete(String id) {
        return Boolean.TRUE.equals(HibernateUtil.doInTransaction(session -> {
            Doctor doctor = session.get(Doctor.class, id);
            if (doctor == null) {
                return false;
            }
            session.remove(doctor);
            return true;
        }));
    }

    public String generateDoctorId() {
        List<String> existingIds = HibernateUtil.doInSession(session ->
                session.createQuery("select d.id from Doctor d", String.class).list());

        return IdGenerator.nextId(existingIds, ID_PREFIX);
    }
}
