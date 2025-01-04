package com.mw.controller;

import com.mw.domain.*;
import com.mw.tool.BeanPropertyRowMapperWithPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {
    @Autowired
    private JdbcClient jdbcClient;

    @GetMapping("/students")
    public AjaxResult getAll() {
        /*
         * student表、classgroup表、speciality表和college表联查
         * student表列添加前缀s_
         * classgroup表列添加前缀cg_
         * speciality表列添加前缀sp_
         * college表列添加前缀c_
         */
        String sql = """
            select 
                s.id as s_id, s.name as s_name, s.code as s_code, s.classgroup_id as s_classgroupId, s.remark as s_remark,
                cg.id as cg_id, cg.name as cg_name, cg.code as cg_code, cg.speciality_id as cg_specialityId, cg.remark as cg_remark,
                sp.id as sp_id, sp.name as sp_name, sp.grade as sp_grade, sp.code as sp_code, sp.college_id as sp_collegeId, sp.remark as sp_remark,
                c.id as c_id, c.name as c_name, c.remark as c_remark
            from student s
            left join classgroup cg on s.classgroup_id = cg.id
            left join speciality sp on cg.speciality_id = sp.id
            left join college c on sp.college_id = c.id
        """;

        // 使用自定义的BeanPropertyRowMapperWithPrefix为列名添加前缀
        BeanPropertyRowMapper<Student> rowMapper1 = new BeanPropertyRowMapperWithPrefix<>("s_");
        rowMapper1.setMappedClass(Student.class);
        BeanPropertyRowMapper<Classgroup> rowMapper2 = new BeanPropertyRowMapperWithPrefix<>("cg_");
        rowMapper2.setMappedClass(Classgroup.class);
        BeanPropertyRowMapper<Speciality> rowMapper3 = new BeanPropertyRowMapperWithPrefix<>("sp_");
        rowMapper3.setMappedClass(Speciality.class);
        BeanPropertyRowMapper<College> rowMapper4 = new BeanPropertyRowMapperWithPrefix<>("c_");
        rowMapper4.setMappedClass(College.class);

        // 自定义RowMapper，用于将查询结果映射为StudentAndClassgroupAndSpecialityAndCollege对象
        RowMapper<StudentAndClassgroupAndSpecialityAndCollege> rowMapper = new RowMapper<StudentAndClassgroupAndSpecialityAndCollege>() {
            public StudentAndClassgroupAndSpecialityAndCollege mapRow(ResultSet rs, int rowNum) throws SQLException {
                Student student = rowMapper1.mapRow(rs, rowNum);
                Classgroup classgroup = rowMapper2.mapRow(rs, rowNum);
                Speciality speciality = rowMapper3.mapRow(rs, rowNum);
                College college = rowMapper4.mapRow(rs, rowNum);

                StudentAndClassgroupAndSpecialityAndCollege result = new StudentAndClassgroupAndSpecialityAndCollege();
                result.setStudent(student);
                result.setClassgroup(classgroup);
                result.setSpeciality(speciality);
                result.setCollege(college);

                return result;
            }
        };

        List<StudentAndClassgroupAndSpecialityAndCollege> items = jdbcClient.sql(sql)
                .query(rowMapper)
                .list();

        return AjaxResult.success().selectResult(items.size(), items);
    }

    @GetMapping("/students/{id}")
    public AjaxResult getById(@PathVariable("id") int expectedId) {
        String sql = """
            select 
                s.id as s_id, s.name as s_name, s.code as s_code, s.classgroup_id as s_classgroupId, s.remark as s_remark,
                cg.id as cg_id, cg.name as cg_name, cg.code as cg_code, cg.speciality_id as cg_specialityId, cg.remark as cg_remark,
                sp.id as sp_id, sp.name as sp_name, sp.grade as sp_grade, sp.code as sp_code, sp.college_id as sp_collegeId, sp.remark as sp_remark,
                c.id as c_id, c.name as c_name, c.remark as c_remark
            from student s
            left join classgroup cg on s.classgroup_id = cg.id
            left join speciality sp on cg.speciality_id = sp.id
            left join college c on sp.college_id = c.id
            where s.id = :id
        """;

        BeanPropertyRowMapper<Student> rowMapper1 = new BeanPropertyRowMapperWithPrefix<>("s_");
        rowMapper1.setMappedClass(Student.class);
        BeanPropertyRowMapper<Classgroup> rowMapper2 = new BeanPropertyRowMapperWithPrefix<>("cg_");
        rowMapper2.setMappedClass(Classgroup.class);
        BeanPropertyRowMapper<Speciality> rowMapper3 = new BeanPropertyRowMapperWithPrefix<>("sp_");
        rowMapper3.setMappedClass(Speciality.class);
        BeanPropertyRowMapper<College> rowMapper4 = new BeanPropertyRowMapperWithPrefix<>("c_");
        rowMapper4.setMappedClass(College.class);

        RowMapper<StudentAndClassgroupAndSpecialityAndCollege> rowMapper = new RowMapper<StudentAndClassgroupAndSpecialityAndCollege>() {
            public StudentAndClassgroupAndSpecialityAndCollege mapRow(ResultSet rs, int rowNum) throws SQLException {
                Student student = rowMapper1.mapRow(rs, rowNum);
                Classgroup classgroup = rowMapper2.mapRow(rs, rowNum);
                Speciality speciality = rowMapper3.mapRow(rs, rowNum);
                College college = rowMapper4.mapRow(rs, rowNum);

                StudentAndClassgroupAndSpecialityAndCollege result = new StudentAndClassgroupAndSpecialityAndCollege();
                result.setStudent(student);
                result.setClassgroup(classgroup);
                result.setSpeciality(speciality);
                result.setCollege(college);

                return result;
            }
        };

        List<StudentAndClassgroupAndSpecialityAndCollege> items = jdbcClient.sql(sql)
                .param("id", expectedId)
                .query(rowMapper)
                .list();

        return AjaxResult.success().selectResult(items.size(), items);
    }

    @GetMapping("/students/search")
    public AjaxResult searchStudents(@RequestParam("keyword") String keyword) {
        // 根据输入的关键词进行模糊搜索
        String sql = """
            select 
                s.id as s_id, s.name as s_name, s.code as s_code, s.classgroup_id as s_classgroupId, s.remark as s_remark,
                cg.id as cg_id, cg.name as cg_name, cg.code as cg_code, cg.speciality_id as cg_specialityId, cg.remark as cg_remark,
                sp.id as sp_id, sp.name as sp_name, sp.grade as sp_grade, sp.code as sp_code, sp.college_id as sp_collegeId, sp.remark as sp_remark,
                c.id as c_id, c.name as c_name, c.remark as c_remark
            from student s
            left join classgroup cg on s.classgroup_id = cg.id
            left join speciality sp on cg.speciality_id = sp.id
            left join college c on sp.college_id = c.id
            where s.name like :keyword or s.code like :keyword or cg.name like :keyword or sp.name like :keyword or c.name like :keyword
        """;

        BeanPropertyRowMapper<Student> rowMapper1 = new BeanPropertyRowMapperWithPrefix<>("s_");
        rowMapper1.setMappedClass(Student.class);
        BeanPropertyRowMapper<Classgroup> rowMapper2 = new BeanPropertyRowMapperWithPrefix<>("cg_");
        rowMapper2.setMappedClass(Classgroup.class);
        BeanPropertyRowMapper<Speciality> rowMapper3 = new BeanPropertyRowMapperWithPrefix<>("sp_");
        rowMapper3.setMappedClass(Speciality.class);
        BeanPropertyRowMapper<College> rowMapper4 = new BeanPropertyRowMapperWithPrefix<>("c_");
        rowMapper4.setMappedClass(College.class);

        RowMapper<StudentAndClassgroupAndSpecialityAndCollege> rowMapper = new RowMapper<StudentAndClassgroupAndSpecialityAndCollege>() {
            public StudentAndClassgroupAndSpecialityAndCollege mapRow(ResultSet rs, int rowNum) throws SQLException {
                Student student = rowMapper1.mapRow(rs, rowNum);
                Classgroup classgroup = rowMapper2.mapRow(rs, rowNum);
                Speciality speciality = rowMapper3.mapRow(rs, rowNum);
                College college = rowMapper4.mapRow(rs, rowNum);

                StudentAndClassgroupAndSpecialityAndCollege result = new StudentAndClassgroupAndSpecialityAndCollege();
                result.setStudent(student);
                result.setClassgroup(classgroup);
                result.setSpeciality(speciality);
                result.setCollege(college);

                return result;
            }
        };

        List<StudentAndClassgroupAndSpecialityAndCollege> items = jdbcClient.sql(sql)
                .param("keyword", "%" + keyword + "%")
                .query(rowMapper)
                .list();

        return AjaxResult.success().selectResult(items.size(), items);
    }

    @PutMapping("/students")
    public AjaxResult update(@RequestBody Student student) {
        // 更新student表
        String sql = """
            update student set 
                name = :name, 
                code = :code, 
                classgroup_id = :classgroupId, 
                remark = :remark 
            where id = :id
        """;

        int n = jdbcClient.sql(sql)
                .paramSource(student)
                .update();

        return AjaxResult.success().updateResult(n);
    }

    @DeleteMapping("/students/{id}")
    public AjaxResult deleteById(@PathVariable("id") int expectedId) {
        // 删除student表中的记录
        String sql = "delete from student where id = :id";

        int n = jdbcClient.sql(sql)
                .param("id", expectedId)
                .update();

        return AjaxResult.success().deleteResult(n);
    }

    @PostMapping("/students")
    public AjaxResult insert(@RequestBody Student student) {
        // 插入新的student记录
        String sql = """
            insert into student
                (name, code, classgroup_id, remark) 
            values
                (:name, :code, :classgroupId, :remark)
        """;

        int n = jdbcClient.sql(sql)
                .paramSource(student)
                .update();

        return AjaxResult.success().insertResult(n);
    }

    @PostMapping("/students/login")
    public AjaxResult login(@RequestBody Student student) {
        String sql = """
            select id, name, code, password
            from student
            where name = :name and password = :password
        """;

        List<Student> users = jdbcClient.sql(sql)
                .param("name", student.getName())
                .param("code", student.getCode())
                .param("password", student.getPassword())
                .query(Student.class)
                .list();

        if (users.isEmpty()) {
            return AjaxResult.bad().msg("用户名/工号或密码错误");
        } else {
            // 登录成功，可以设置一些登录后的逻辑，比如生成token等
            return AjaxResult.success().data(users.get(0));
        }
    }

    @PostMapping("/stidents/register")
    public AjaxResult register(@RequestBody Student student) {
        String sql = """
            insert into student (username, code, password)
            values (:name, :code, :password)
        """;

        int n = jdbcClient.sql(sql)
                .paramSource(student)
                .update();

        if (n > 0) {
            return AjaxResult.success().msg("注册成功");
        } else {
            return AjaxResult.error().msg("注册失败");
        }
    }

}