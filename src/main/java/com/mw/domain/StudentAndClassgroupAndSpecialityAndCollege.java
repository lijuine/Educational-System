package com.mw.domain;

/**
 * 用于记录学生、班级、专业和学院四表联合查询的结果
 */
public class StudentAndClassgroupAndSpecialityAndCollege {
    private Student student;
    private Classgroup classgroup;
    private Speciality speciality;
    private College college;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

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