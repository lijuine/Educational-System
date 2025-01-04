package com.mw.controller;

import com.mw.domain.AjaxResult;
import com.mw.domain.College;
import com.mw.domain.Speciality;
import com.mw.domain.SpecialityAndCollege;
import com.mw.tool.BeanPropertyRowMapperWithPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
public class SpecialityController {
    @Autowired
    private JdbcClient jdbcClient;

    @GetMapping("/specialities")
    public AjaxResult getAll() {
        String sql = """
            select 
                s.id as s_id, s.name as s_name, s.grade as s_grade, 
                s.code as s_code, s.college_id as s_collegeId, s.remark as s_remark,
                c.name as c_name, c.remark as c_remark, c.id as c_id
            from speciality s
            left join college c
            on s.college_id = c.id
        """;

        BeanPropertyRowMapper<Speciality> rowMapper1 = new BeanPropertyRowMapperWithPrefix<Speciality>("s_");
        rowMapper1.setMappedClass(Speciality.class);
        BeanPropertyRowMapper<College> rowMapper2 = new BeanPropertyRowMapperWithPrefix<College>("c_");
        rowMapper2.setMappedClass(College.class);

        RowMapper<SpecialityAndCollege> rowMapper = new RowMapper<SpecialityAndCollege>() {
            public SpecialityAndCollege mapRow(ResultSet rs, int rowNum) throws SQLException {
                Speciality speciality = rowMapper1.mapRow(rs, rowNum);
                College college = rowMapper2.mapRow(rs, rowNum);

                SpecialityAndCollege specialityAndCollege = new SpecialityAndCollege();
                specialityAndCollege.setSpeciality(speciality);
                specialityAndCollege.setCollege(college);

                return specialityAndCollege;
            }
        };

        List<SpecialityAndCollege> items = jdbcClient.sql(sql)
                .query(rowMapper)
                .list();

        return AjaxResult.success().selectResult(items.size(), items);
    }

    @GetMapping("/specialities/{id}")
    public AjaxResult getById(@PathVariable("id") int expectedId) {
        String sql = """
            select 
                s.id as s_id, s.name as s_name, s.grade as s_grade, 
                s.code as s_code, s.college_id as s_collegeId, s.remark as s_remark,
                c.name as c_name, c.remark as c_remark, c.id as c_id
            from speciality s
            left join college c
            on s.college_id = c.id
            where s.id = :id
        """;

        BeanPropertyRowMapper<Speciality> rowMapper1 = new BeanPropertyRowMapperWithPrefix<Speciality>("s_");
        rowMapper1.setMappedClass(Speciality.class);
        BeanPropertyRowMapper<College> rowMapper2 = new BeanPropertyRowMapperWithPrefix<College>("c_");
        rowMapper2.setMappedClass(College.class);

        RowMapper<SpecialityAndCollege> rowMapper = new RowMapper<SpecialityAndCollege>() {
            public SpecialityAndCollege mapRow(ResultSet rs, int rowNum) throws SQLException {
                Speciality speciality = rowMapper1.mapRow(rs, rowNum);
                College college = rowMapper2.mapRow(rs, rowNum);

                SpecialityAndCollege specialityAndCollege = new SpecialityAndCollege();
                specialityAndCollege.setSpeciality(speciality);
                specialityAndCollege.setCollege(college);

                return specialityAndCollege;
            }
        };

        List<SpecialityAndCollege> items = jdbcClient.sql(sql)
                .param("id", expectedId)
                .query(rowMapper)
                .list();

        return AjaxResult.success().selectResult(items.size(), items);
    }

    @GetMapping("/specialities/search")
    public AjaxResult searchSpecialities(@RequestParam("keyword") String keyword) {
        String sql = """
            select 
                s.id as s_id, s.name as s_name, s.grade as s_grade, 
                s.code as s_code, s.college_id as s_collegeId, s.remark as s_remark,
                c.name as c_name, c.remark as c_remark, c.id as c_id
            from speciality s
            left join college c
            on s.college_id = c.id
            where s.name like :keyword or s.code like :keyword or c.name like :keyword or c.remark like :keyword
        """;

        BeanPropertyRowMapper<Speciality> rowMapper1 = new BeanPropertyRowMapperWithPrefix<Speciality>("s_");
        rowMapper1.setMappedClass(Speciality.class);
        BeanPropertyRowMapper<College> rowMapper2 = new BeanPropertyRowMapperWithPrefix<College>("c_");
        rowMapper2.setMappedClass(College.class);

        RowMapper<SpecialityAndCollege> rowMapper = new RowMapper<SpecialityAndCollege>() {
            public SpecialityAndCollege mapRow(ResultSet rs, int rowNum) throws SQLException {
                Speciality speciality = rowMapper1.mapRow(rs, rowNum);
                College college = rowMapper2.mapRow(rs, rowNum);

                SpecialityAndCollege specialityAndCollege = new SpecialityAndCollege();
                specialityAndCollege.setSpeciality(speciality);
                specialityAndCollege.setCollege(college);

                return specialityAndCollege;
            }
        };

        List<SpecialityAndCollege> items = jdbcClient.sql(sql)
                .param("keyword", "%" + keyword + "%")
                .query(rowMapper)
                .list();

        return AjaxResult.success().selectResult(items.size(), items);
    }

    @PutMapping("/specialities")
    public AjaxResult update(@RequestBody Speciality speciality) {
        String sql = """
            update speciality set 
                name = :name, 
                grade = :grade, 
                code = :code, 
                college_id = :collegeId, 
                remark = :remark 
            where id = :id
        """;

        int n = jdbcClient.sql(sql)
                .paramSource(speciality)
                .update();

        return AjaxResult.success().updateResult(n);
    }

    @DeleteMapping("/specialities/{id}")
    public AjaxResult deleteById(@PathVariable("id") int expectedId) {
        String sql = "delete from speciality where id = :id";

        int n = jdbcClient.sql(sql)
                .param("id", expectedId)
                .update();

        return AjaxResult.success().deleteResult(n);
    }

    @PostMapping("/specialities")
    public AjaxResult insert(@RequestBody Speciality speciality) {
        String sql = """
            insert into speciality
                (name, grade, code, college_id, remark) 
            values
                (:name, :grade, :code, :collegeId, :remark)
        """;

        int n = jdbcClient.sql(sql)
                .paramSource(speciality)
                .update();

        return AjaxResult.success().insertResult(n);
    }
}