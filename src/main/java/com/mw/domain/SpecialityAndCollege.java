package com.mw.domain;

/*
 * join查询时，记录结果
 */
public class SpecialityAndCollege {
    private Speciality speciality;
    private College college;

    // Getters and Setters for Speciality
    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    // Getters and Setters for College
    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }
}