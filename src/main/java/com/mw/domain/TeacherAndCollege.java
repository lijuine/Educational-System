package com.mw.domain;

/*
 * join查询时，记录结果
 */
public class TeacherAndCollege {
    private Teacher teacher;
    private College college;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }
}
