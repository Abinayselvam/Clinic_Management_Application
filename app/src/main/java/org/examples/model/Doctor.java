package org.examples.model;

import org.examples.enums.Shift;
import org.examples.enums.Specialization;

import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private List<String> bookedSlots= new ArrayList<>(); //Trace Busy Times

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
    public String getName()
    {
        return name;
    }

    @Override
    public String toString() {

        return String.format(
                "ID:%s | Name:%-15s | Specification:%-15s | Experience:%2d yrs | Shift:%s",
                id,
                name,
                specification,
                experience,
                shift
        );
    }
    public boolean isSlotAvailable(String slot)
    {
        return !bookedSlots.contains(slot);
    }
    public void bookSlot(String slot)
    {
        bookedSlots.add(slot);
    }
    public Specialization getSpecialization()
    {
        return specification;
    }
    //uc11
    public boolean isShiftAvailable(String slot) {

        if (shift == Shift.BOTH)
            return true;

        if (shift == Shift.MORNING)
            return slot.startsWith("09")
                    || slot.startsWith("10")
                    || slot.startsWith("11")
                    || slot.startsWith("12");

        if (shift == Shift.NIGHT)
            return slot.startsWith("04")
                    || slot.startsWith("05")
                    || slot.startsWith("06")
                    || slot.startsWith("07");

        return false;
    }
}