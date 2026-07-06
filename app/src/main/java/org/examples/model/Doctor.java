package org.examples.model;
public class Doctor {

    public String id;
    public String name;
    public String specification;
    public int experience;
    public String shift;

    public Doctor(String id,
                  String name,
                  String specification,
                  int experience,
                  String shift) {

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