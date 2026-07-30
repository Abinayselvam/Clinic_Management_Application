package org.examples.model;

<<<<<<< HEAD
import jakarta.persistence.*;
@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @Column(name = "appointment_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "slot")
    private String slot;

    public Appointment() {
    }

    public Appointment(String id, Doctor doctor, Patient patient, String slot) {
        this.id = id;
=======
import org.examples.enums.Shift;

public class Appointment {
    String id;
    Doctor doctor;
    Patient patient;
    String slot;
    public Appointment(String id,Doctor doctor, Patient patient, String slot) {
        this.id= id;
>>>>>>> 7bb398e2a56e4a9475b741915125afbb57a30061
        this.doctor = doctor;
        this.patient = patient;
        this.slot = slot;
    }
<<<<<<< HEAD

    public String getId() {
        return id;
    }

=======
    public String getId()
    {
        return id;
    }
>>>>>>> 7bb398e2a56e4a9475b741915125afbb57a30061
    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }
<<<<<<< HEAD

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    @Override
    public String toString() {
        return String.format("APPOINTMENT : [%s] |  Patient : %s  | Doctor : %s",
                slot, patient.getName(), doctor.getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
=======
    public String  getSlot() {
        return slot;
    }

    @Override
    public String toString()
    {
        return String.format("APPOINTMENT : [%s] |  Patient : %s  | Doctor : %s", slot, patient.getName(), doctor.getName());
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

>>>>>>> 7bb398e2a56e4a9475b741915125afbb57a30061
        Appointment other = (Appointment) obj;
        return id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
