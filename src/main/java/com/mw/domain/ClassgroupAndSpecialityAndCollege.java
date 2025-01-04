package com.mw.domain;

/*
 * join查询时，记录结果
 */
public class ClassgroupAndSpecialityAndCollege {
    private Classgroup classgroup;
    private Speciality speciality;
    private College college;

    public Classgroup getClassgroup() {
        return classgroup;
    }

    public void setClassgroup(Classgroup classgroup) {
        this.classgroup = classgroup;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }
}