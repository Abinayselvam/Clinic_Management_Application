package org.examples.model;

import org.examples.enums.Gender;

public class Patient {
    private String id;
    private String name;
    private Gender gender;
    private int age;
    private String phone;
    //constructor
    public Patient(String id, String name, Gender gender, int age, String phone){
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
    }
    public String toString(){
        return String.format("ID:%s | Name:%-15s | gender:%-6s | age:%d | phone:%s", id, name, gender, age, phone);
    }
    public String getName() {
        return name;
    }
    public String getPhone(){
        return phone;
    }
}
