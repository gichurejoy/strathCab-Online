package com.example.strathcab;

public class Drivers {
    public String Name;
    public String Email;
    public String Admission_Number;
    public String Phone_number;


    public Drivers() {
    }

    public Drivers(String name, String email, String admission_Number, String phone_number) {
        Name = name;
        Email = email;
        Admission_Number = admission_Number;
        Phone_number = phone_number;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAdmission_Number() {
        return Admission_Number;
    }

    public void setAdmission_Number(String admission_Number) {
        Admission_Number = admission_Number;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public void setPhone_number(String phone_number) {
        Phone_number = phone_number;
    }
}
