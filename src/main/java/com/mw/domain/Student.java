package com.mw.domain;

import java.io.Serializable;

public class Student implements Serializable {
    private Integer id; // 学生ID

    private String name; // 学生姓名

    private String password; // 密码

    private String code; // 学号

    private Integer classgroupId; // 班级ID

    private String remark; // 备注

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getClassgroupId() {
        return classgroupId;
    }

    public void setClassgroupId(Integer classgroupId) {
        this.classgroupId = classgroupId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}