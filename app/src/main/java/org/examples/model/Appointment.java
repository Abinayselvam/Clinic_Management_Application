package org.examples.model;

import org.examples.enums.Shift;

public class Appointment {
    String id;
    Doctor doctor;
    Patient patient;
    String slot;
    public Appointment(String id,Doctor doctor, Patient patient, String slot) {
        this.id= id;
        this.doctor = doctor;
        this.patient = patient;
        this.slot = slot;
    }
    public String getId()
    {
        return id;
    }
    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }
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

        Appointment other = (Appointment) obj;
        return id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
