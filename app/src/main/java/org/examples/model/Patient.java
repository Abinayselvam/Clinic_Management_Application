package org.examples.model;

import org.examples.enums.Gender;

public class Patient {
    private String id;
    private String name;
    private Gender gender;
    private int age;
    private String phone;
    //
    public Patient()
    {

    }
    //constructor
    public Patient(String id, String name, Gender gender, int age, String phone){
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
    }
    public String getId() {
        return id;
    }
    public int getAge() {
        return age;
    }
    public Gender getGender() {
        return gender;
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

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public  void setGender(Gender gender)
    {
        this.gender=gender;
    }
    public void setAge(int age)
    {
        this.age=age;
    }
    public void setPhone(String phone)
    {
        this.phone=phone;
    }
}
