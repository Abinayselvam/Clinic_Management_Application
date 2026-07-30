package org.examples.repository;

import org.examples.model.Appointment;
import org.examples.util.HibernateUtil;
import org.examples.util.IdGenerator;


import java.util.List;

/**
 * Data-access operations for {@link Appointment} entities.
 */
public class AppointmentRepository {

    private static final String ID_PREFIX = "A";

    public void saveAppointment(Appointment appointment) {
        HibernateUtil.doInTransaction(session -> {
            session.persist(appointment);
            return null;
        });
    }

    public String generateAppointmentId() {
        List<String> existingIds = HibernateUtil.doInSession(session ->
                session.createQuery("select a.id from Appointment a", String.class).list());

        return IdGenerator.nextId(existingIds, ID_PREFIX);
    }

    public List<Appointment> getAllAppointment() {

        org.hibernate.Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        String hql = """
        select a
        from Appointment a
        join fetch a.doctor
        join fetch a.patient
        """;

        List<Appointment> appointments =
                session.createQuery(hql, Appointment.class)
                        .list();

        session.close();

        return appointments;
    }

    public Appointment findById(String id) {
        return HibernateUtil.doInSession(session ->
                session.get(Appointment.class, id));
    }

    public List<Appointment> findByPatientId(String patientId) {
        return HibernateUtil.doInSession(session ->
                session.createQuery(
                                "from Appointment a where a.patient.id = :patientId",
                                Appointment.class)
                        .setParameter("patientId", patientId)
                        .list());
    }

    public List<Appointment> findByDoctorId(String doctorId) {
        return HibernateUtil.doInSession(session ->
                session.createQuery(
                                "from Appointment a where a.doctor.id = :doctorId",
                                Appointment.class)
                        .setParameter("doctorId", doctorId)
                        .list());
    }

    public List<Appointment> findBySlot(String slot) {
        return HibernateUtil.doInSession(session ->
                session.createQuery(
                                "from Appointment a where a.slot = :slot",
                                Appointment.class)
                        .setParameter("slot", slot)
                        .list());
    }

    /** @return true if no appointment already occupies this doctor/slot pair */
    public boolean isDoctorAvailable(String doctorId, String slot) {
        return HibernateUtil.doInSession(session -> {
            Long count = session.createQuery(
                            """
                            select count(a)
                            from Appointment a
                            where a.doctor.id = :doctorId
                            and a.slot = :slot
                            """,
                            Long.class)
                    .setParameter("doctorId", doctorId)
                    .setParameter("slot", slot)
                    .uniqueResult();

            return count != null && count == 0;
        });
    }

    /** @return true if the appointment existed and its slot was updated */
    public boolean updateSlot(String appointmentId, String slot) {
        return Boolean.TRUE.equals(HibernateUtil.doInTransaction(session -> {
            Appointment appointment = session.get(Appointment.class, appointmentId);

            if (appointment == null) {
                return false;
            }

            appointment.setSlot(slot);
            return true;
        }));
    }

    /** @return true if the appointment existed and was cancelled */
    public boolean deleteAppointment(String appointmentId) {
        return Boolean.TRUE.equals(HibernateUtil.doInTransaction(session -> {
            Appointment appointment = session.get(Appointment.class, appointmentId);

            if (appointment == null) {
                return false;
            }

            session.remove(appointment);
            return true;
        }));
    }

}
