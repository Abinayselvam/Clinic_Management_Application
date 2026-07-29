package org.examples.model;

import org.examples.enums.Shift;
import org.examples.enums.Specialization;

import java.util.ArrayList;
import java.util.List;

public class Doctor {

    // Stores booked appointment slots
    private List<String> bookedSlots = new ArrayList<>();

    private String id;
    private String name;
    private Specialization specification;
    private int experience;
    private Shift shift;

    public Doctor(String id,
                  String name,
                  Specialization specification,
                  int experience,
                  Shift shift) {

        this.id = id;
        this.name = name;
        this.specification = specification;
        this.experience = experience;
        this.shift = shift;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getExperience() {
        return experience;
    }

    public Specialization getSpecification() {
        return specification;
    }

    public Specialization getSpecialization() {
        return specification;
    }

    public Shift getShift() {
        return shift;
    }

    /**
     * Checks whether a slot is free.
     */
    public boolean isSlotAvailable(String slot) {
        return !bookedSlots.contains(slot);
    }

    /**
     * Books the slot.
     */
    public void bookSlot(String slot) {
        bookedSlots.add(slot);
    }

    /**
     * Returns booked slots.
     */
    public List<String> getBookedSlots() {
        return bookedSlots;
    }

    /**
     * UC11 - Shift validation
     */
    public boolean isShiftAvailable(String slot) {

        if (shift == Shift.BOTH)
            return true;

        if (shift == Shift.MORNING) {
            return slot.startsWith("09")
                    || slot.startsWith("10")
                    || slot.startsWith("11")
                    || slot.startsWith("12");
        }

        if (shift == Shift.NIGHT) {
            return slot.startsWith("04")
                    || slot.startsWith("05")
                    || slot.startsWith("06")
                    || slot.startsWith("07");
        }

        return false;
    }

    @Override
    public String toString() {

        return String.format(
                "ID:%s | Name:%-15s | Specialization:%-20s | Experience:%2d yrs | Shift:%s",
                id,
                name,
                specification,
                experience,
                shift
        );
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Doctor other = (Doctor) obj;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}