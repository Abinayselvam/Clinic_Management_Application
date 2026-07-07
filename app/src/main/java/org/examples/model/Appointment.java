package org.examples.model;

public class Appointment {
    Doctor doctor;
    Patient patient;
    String slot;
    public Appointment(Doctor doctor, Patient patient, String slot) {
        this.doctor = doctor;
        this.patient = patient;
        this.slot = slot;
    }
    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }
    public String getSlot() {
        return slot;
    }

    @Override
    public String toString()
    {
        return String.format("APPOINTMENT : [%s] |  Patient : %s  | Doctor : %s", slot, patient.getName(), doctor.getName());
    }
}
