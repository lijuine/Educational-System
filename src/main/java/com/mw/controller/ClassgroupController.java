package com.mw.controller;

import com.mw.domain.AjaxResult;
import com.mw.domain.Classgroup;
import com.mw.domain.Speciality;
import com.mw.domain.College;
import com.mw.domain.ClassgroupAndSpecialityAndCollege;
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
public class ClassgroupController {
    @Autowired
    private JdbcClient jdbcClient;

    @GetMapping("/classgroups")
    public AjaxResult getAll() {
        /*
         * classgroup表、speciality表和college表联查
         * classgroup表列添加前缀cg_
         * speciality表列添加前缀s_
         * college表列添加前缀c_
         */
        String sql = """
            select 
                cg.id as cg_id, cg.name as cg_name, cg.code as cg_code, 
                cg.speciality_id as cg_specialityId, cg.remark as cg_remark,
                s.id as s_id, s.name as s_name, s.grade as s_grade, s.code as s_code, s.college_id as s_collegeId, s.remark as s_remark,
                c.name as c_name, c.remark as c_remark, c.id as c_id
            from classgroup cg
            left join speciality s on cg.speciality_id = s.id
            left join college c on s.college_id = c.id
        """;

        // 使用自定义的BeanPropertyRowMapperWithPrefix为列名添加前缀
        BeanPropertyRowMapper<Classgroup> rowMapper1 = new BeanPropertyRowMapperWithPrefix<>("cg_");
        rowMapper1.setMappedClass(Classgroup.class);
        BeanPropertyRowMapper<Speciality> rowMapper2 = new BeanPropertyRowMapperWithPrefix<>("s_");
        rowMapper2.setMappedClass(Speciality.class);
        BeanPropertyRowMapper<College> rowMapper3 = new BeanPropertyRowMapperWithPrefix<>("c_");
        rowMapper3.setMappedClass(College.class);

        // 自定义RowMapper，用于将查询结果映射为ClassgroupAndSpecialityAndCollege对象
        RowMapper<ClassgroupAndSpecialityAndCollege> rowMapper = new RowMapper<ClassgroupAndSpecialityAndCollege>() {
            public ClassgroupAndSpecialityAndCollege mapRow(ResultSet rs, int rowNum) throws SQLException {
                Classgroup classgroup = rowMapper1.mapRow(rs, rowNum);
                Speciality speciality = rowMapper2.mapRow(rs, rowNum);
                College college = rowMapper3.mapRow(rs, rowNum);

                ClassgroupAndSpecialityAndCollege result = new ClassgroupAndSpecialityAndCollege();
                result.setClassgroup(classgroup);
                result.setSpeciality(speciality);
                result.setCollege(college);

                return result;
            }
        };

        List<ClassgroupAndSpecialityAndCollege> items = jdbcClient.sql(sql)
                .query(rowMapper)
                .list();

        return AjaxResult.success().selectResult(items.size(), items);
    }

    @GetMapping("/classgroups/{id}")
    public AjaxResult getById(@PathVariable("id") int expectedId) {
        String sql = """
            select 
                cg.id as cg_id, cg.name as cg_name, cg.code as cg_code, 
                cg.speciality_id as cg_specialityId, cg.remark as cg_remark,
                s.id as s_id, s.name as s_name, s.grade as s_grade, s.code as s_code, s.college_id as s_collegeId, s.remark as s_remark,
                c.name as c_name, c.remark as c_remark, c.id as c_id
            from classgroup cg
            left join speciality s on cg.speciality_id = s.id
            left join college c on s.college_id = c.id
            where cg.id = :id
        """;

        BeanPropertyRowMapper<Classgroup> rowMapper1 = new BeanPropertyRowMapperWithPrefix<>("cg_");
        rowMapper1.setMappedClass(Classgroup.class);
        BeanPropertyRowMapper<Speciality> rowMapper2 = new BeanPropertyRowMapperWithPrefix<>("s_");
        rowMapper2.setMappedClass(Speciality.class);
        BeanPropertyRowMapper<College> rowMapper3 = new BeanPropertyRowMapperWithPrefix<>("c_");
        rowMapper3.setMappedClass(College.class);

        RowMapper<ClassgroupAndSpecialityAndCollege> rowMapper = new RowMapper<ClassgroupAndSpecialityAndCollege>() {
            public ClassgroupAndSpecialityAndCollege mapRow(ResultSet rs, int rowNum) throws SQLException {
                Classgroup classgroup = rowMapper1.mapRow(rs, rowNum);
                Speciality speciality = rowMapper2.mapRow(rs, rowNum);
                College college = rowMapper3.mapRow(rs, rowNum);

                ClassgroupAndSpecialityAndCollege result = new ClassgroupAndSpecialityAndCollege();
                result.setClassgroup(classgroup);
                result.setSpeciality(speciality);
                result.setCollege(college);

                return result;
            }
        };

        List<ClassgroupAndSpecialityAndCollege> items = jdbcClient.sql(sql)
                .param("id", expectedId)
                .query(rowMapper)
                .list();

        return AjaxResult.success().selectResult(items.size(), items);
    }

    @GetMapping("/classgroups/search")
    public AjaxResult searchClassgroups(@RequestParam("keyword") String keyword) {
        // 根据输入的关键词进行模糊搜索
        String sql = """
            select 
                cg.id as cg_id, cg.name as cg_name, cg.code as cg_code, 
                cg.speciality_id as cg_specialityId, cg.remark as cg_remark,
                s.id as s_id, s.name as s_name, s.grade as s_grade, s.code as s_code, s.college_id as s_collegeId, s.remark as s_remark,
                c.name as c_name, c.remark as c_remark, c.id as c_id
            from classgroup cg
            left join speciality s on cg.speciality_id = s.id
            left join college c on s.college_id = c.id
            where cg.name like :keyword or cg.code like :keyword or s.name like :keyword or c.name like :keyword
        """;

        BeanPropertyRowMapper<Classgroup> rowMapper1 = new BeanPropertyRowMapperWithPrefix<>("cg_");
        rowMapper1.setMappedClass(Classgroup.class);
        BeanPropertyRowMapper<Speciality> rowMapper2 = new BeanPropertyRowMapperWithPrefix<>("s_");
        rowMapper2.setMappedClass(Speciality.class);
        BeanPropertyRowMapper<College> rowMapper3 = new BeanPropertyRowMapperWithPrefix<>("c_");
        rowMapper3.setMappedClass(College.class);

        RowMapper<ClassgroupAndSpecialityAndCollege> rowMapper = new RowMapper<ClassgroupAndSpecialityAndCollege>() {
            public ClassgroupAndSpecialityAndCollege mapRow(ResultSet rs, int rowNum) throws SQLException {
                Classgroup classgroup = rowMapper1.mapRow(rs, rowNum);
                Speciality speciality = rowMapper2.mapRow(rs, rowNum);
                College college = rowMapper3.mapRow(rs, rowNum);

                ClassgroupAndSpecialityAndCollege result = new ClassgroupAndSpecialityAndCollege();
                result.setClassgroup(classgroup);
                result.setSpeciality(speciality);
                result.setCollege(college);

                return result;
            }
        };

        List<ClassgroupAndSpecialityAndCollege> items = jdbcClient.sql(sql)
                .param("keyword", "%" + keyword + "%")
                .query(rowMapper)
                .list();

        return AjaxResult.success().selectResult(items.size(), items);
    }

    @PutMapping("/classgroups")
    public AjaxResult update(@RequestBody Classgroup classgroup) {
        // 更新classgroup表
        String sql = """
            update classgroup set 
                name = :name, 
                code = :code, 
                speciality_id = :specialityId, 
                remark = :remark 
            where id = :id
        """;

        int n = jdbcClient.sql(sql)
                .paramSource(classgroup)
                .update();

        return AjaxResult.success().updateResult(n);
    }

    @DeleteMapping("/classgroups/{id}")
    public AjaxResult deleteById(@PathVariable("id") int expectedId) {
        // 删除classgroup表中的记录
        String sql = "delete from classgroup where id = :id";

        int n = jdbcClient.sql(sql)
                .param("id", expectedId)
                .update();

        return AjaxResult.success().deleteResult(n);
    }

    @PostMapping("/classgroups")
    public AjaxResult insert(@RequestBody Classgroup classgroup) {
        // 插入新的classgroup记录
        String sql = """
            insert into classgroup
                (name, code, speciality_id, remark) 
            values
                (:name, :code, :specialityId, :remark)
        """;

        int n = jdbcClient.sql(sql)
                .paramSource(classgroup)
                .update();

        return AjaxResult.success().insertResult(n);
    }
}