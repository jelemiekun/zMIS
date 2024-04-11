package com.example.zmis;

import java.time.LocalDate;

public class Account {
    private String email;
    private String LRN;
    private String fullName;
    private String phoneNumber;
    private String strand;
    private String section;
    private int age;
    private String sex;
    private LocalDate birthdate;
    private String address;
    private String civilStatus;
    private String elemSchool;
    private String elemSY;
    private String juniorHS;
    private String juniorHSSY;
    private String documentStatus;
    private boolean form137;
    private boolean form138;
    private boolean goodMoral;
    private boolean isApplied;
    private boolean isAccepted;

    public Account(String email, String LRN, String fullName, String phoneNumber, String strand, String section, int age, String sex, LocalDate birthdate, String address, String civilStatus, String elemSchool, String elemSY, String juniorHS, String juniorHSSY, String documentStatus, boolean form137, boolean form138, boolean goodMoral, boolean isApplied) {
        this.email = email;
        this.LRN = LRN;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.strand = strand;
        this.section = section;
        this.age = age;
        this.sex = sex;
        this.birthdate = birthdate;
        this.address = address;
        this.civilStatus = civilStatus;
        this.elemSchool = elemSchool;
        this.elemSY = elemSY;
        this.juniorHS = juniorHS;
        this.juniorHSSY = juniorHSSY;
        this.documentStatus = documentStatus;
        this.form137 = form137;
        this.form138 = form138;
        this.goodMoral = goodMoral;
        this.isApplied = isApplied;
    }

    public Account(String email) {
        this.email = email;
    }
}
