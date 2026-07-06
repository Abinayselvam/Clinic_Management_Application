package org.examples.model;

import org.examples.enums.Shift;
import org.examples.enums.Specialization;

public class Doctor {

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
}