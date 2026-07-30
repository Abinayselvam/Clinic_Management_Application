package org.examples.model;

<<<<<<< HEAD
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
=======
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
>>>>>>> 7bb398e2a56e4a9475b741915125afbb57a30061
                  int experience,
                  Shift shift) {

        this.id = id;
        this.name = name;
<<<<<<< HEAD
        this.specialization = specialization;
=======
        this.specification = specification;
>>>>>>> 7bb398e2a56e4a9475b741915125afbb57a30061
        this.experience = experience;
        this.shift = shift;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

<<<<<<< HEAD
    public Specification getSpecialization() {
        return specialization;
    }
=======
>>>>>>> 7bb398e2a56e4a9475b741915125afbb57a30061
    public int getExperience() {
        return experience;
    }

<<<<<<< HEAD
=======
    public Specialization getSpecification() {
        return specification;
    }

    public Specialization getSpecialization() {
        return specification;
    }

>>>>>>> 7bb398e2a56e4a9475b741915125afbb57a30061
    public Shift getShift() {
        return shift;
    }

<<<<<<< HEAD
=======
    /**
     * Checks whether a slot is free.
     */
>>>>>>> 7bb398e2a56e4a9475b741915125afbb57a30061
    public boolean isSlotAvailable(String slot) {
        return !bookedSlots.contains(slot);
    }

<<<<<<< HEAD
=======
    /**
     * Books the slot.
     */
>>>>>>> 7bb398e2a56e4a9475b741915125afbb57a30061
    public void bookSlot(String slot) {
        bookedSlots.add(slot);
    }

<<<<<<< HEAD
=======
    /**
     * Returns booked slots.
     */
>>>>>>> 7bb398e2a56e4a9475b741915125afbb57a30061
    public List<String> getBookedSlots() {
        return bookedSlots;
    }

<<<<<<< HEAD
=======
    /**
     * UC11 - Shift validation
     */
>>>>>>> 7bb398e2a56e4a9475b741915125afbb57a30061
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
<<<<<<< HEAD
=======

>>>>>>> 7bb398e2a56e4a9475b741915125afbb57a30061
        return String.format(
                "ID:%s | Name:%-15s | Specialization:%-20s | Experience:%2d yrs | Shift:%s",
                id,
                name,
<<<<<<< HEAD
                specialization,
=======
                specification,
>>>>>>> 7bb398e2a56e4a9475b741915125afbb57a30061
                experience,
                shift
        );
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

<<<<<<< HEAD
        if (!(obj instanceof Doctor))
=======
        if (obj == null || getClass() != obj.getClass())
>>>>>>> 7bb398e2a56e4a9475b741915125afbb57a30061
            return false;

        Doctor other = (Doctor) obj;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}