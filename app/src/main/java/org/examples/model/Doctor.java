package org.examples.model;

import jakarta.persistence.*;
import org.examples.enums.Shift;
import org.examples.enums.Specification;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "specialization")
    private Specification specialization;

    @Column(name = "experience")
    private int experience;

    @Enumerated(EnumType.STRING)
    @Column(name = "shift")
    private Shift shift;

    @Transient
    private List<String> bookedSlots = new ArrayList<>();

    public Doctor() {
    }

    public Doctor(String id,
                  String name,
                  Specification specialization,
                  int experience,
                  Shift shift) {

        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.experience = experience;
        this.shift = shift;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Specification getSpecialization() {
        return specialization;
    }
    public int getExperience() {
        return experience;
    }

    public Shift getShift() {
        return shift;
    }

    public boolean isSlotAvailable(String slot) {
        return !bookedSlots.contains(slot);
    }

    public void bookSlot(String slot) {
        bookedSlots.add(slot);
    }

    public List<String> getBookedSlots() {
        return bookedSlots;
    }

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
                specialization,
                experience,
                shift
        );
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof Doctor))
            return false;

        Doctor other = (Doctor) obj;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}