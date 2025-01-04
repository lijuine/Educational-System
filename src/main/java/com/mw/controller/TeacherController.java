package com.mw.controller;

import com.mw.domain.AjaxResult;
import com.mw.domain.College;
import com.mw.domain.Teacher;
import com.mw.domain.TeacherAndCollege;
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
public class TeacherController {
    @Autowired
    private JdbcClient jdbcClient;

    @GetMapping("/teachers")
    public AjaxResult getAll() {
        /*
         * teacher表和college表联查
         * teacher表列添加前缀t_
         * college表列添加前缀c_，注意college_id列
         */
        String sql = """
            select 
                t.id as t_id, t.name as t_name, t.password as t_password,
                    t.code as t_code, t.college_id as t_collegeId, t.remark as t_remark,
                c.name as c_name, c.remark as c_remark, c.id as c_id
            from teacher t
            left join college c
            on t.college_id = c.id
       """;

        /*
         * 借助于自定义的，支持列名添加前缀的BeanPropertyRowMapper
         * 随后可重用这些mapper
         */
        BeanPropertyRowMapper<Teacher> rowMapper1 = new BeanPropertyRowMapperWithPrefix<Teacher>("t_");
        rowMapper1.setMappedClass(Teacher.class);
        BeanPropertyRowMapper<College> rowMapper2 = new BeanPropertyRowMapperWithPrefix<College>("c_");
        rowMapper2.setMappedClass(College.class);

        /* 自定义RowMapper */
        RowMapper<TeacherAndCollege> rowMapper = new RowMapper<TeacherAndCollege>() {
            public TeacherAndCollege mapRow(ResultSet rs, int rowNum) throws SQLException {
                /* 从ResultSet创建多个对象 */
                Teacher teacher = rowMapper1.mapRow(rs, rowNum);
                College college = rowMapper2.mapRow(rs, rowNum);

                /* 手动组装一个TeacherAndCollege对象 */
                TeacherAndCollege teacherAndCollege = new TeacherAndCollege();
                teacherAndCollege.setTeacher(teacher);
                teacherAndCollege.setCollege(college);

                return teacherAndCollege;
            }
        };
        List<TeacherAndCollege> items =jdbcClient.sql(sql)
                .query(rowMapper)
                .list();

        return AjaxResult.success().selectResult(items.size(), items);
    }

    @GetMapping("/teachers/{id}")
    public AjaxResult getById(@PathVariable("id") int expectedId) {
        String sql = """
            select 
                t.id as t_id, t.name as t_name, t.password as t_password, 
                    t.code as t_code, t.college_id as t_collegeId, t.remark as t_remark,
                c.name as c_name, c.remark as c_remark, c.id as c_id
            from teacher t
            left join college c
            on t.college_id = c.id
            where t.id = :id
         """;

        BeanPropertyRowMapper<Teacher> rowMapper1 = new BeanPropertyRowMapperWithPrefix<Teacher>("t_");
        rowMapper1.setMappedClass(Teacher.class);
        BeanPropertyRowMapper<College> rowMapper2 = new BeanPropertyRowMapperWithPrefix<College>("c_");
        rowMapper2.setMappedClass(College.class);

        RowMapper<TeacherAndCollege> rowMapper = new RowMapper<TeacherAndCollege>() {
            public TeacherAndCollege mapRow(ResultSet rs, int rowNum) throws SQLException {
                Teacher teacher = rowMapper1.mapRow(rs, rowNum);
                College college = rowMapper2.mapRow(rs, rowNum);

                TeacherAndCollege teacherAndCollege = new TeacherAndCollege();
                teacherAndCollege.setTeacher(teacher);
                teacherAndCollege.setCollege(college);

                return teacherAndCollege;
            }
        };
        List<TeacherAndCollege> items =jdbcClient.sql(sql)
                .param("id", expectedId)
                .query(rowMapper)
                .list();

        return AjaxResult.success().selectResult(items.size(), items);
    }

    @GetMapping("/teachers/search")
    public AjaxResult searchTeachers(@RequestParam("keyword") String keyword) {
        // 根据输入的关键词进行模糊搜索
        String sql = """
            select 
                t.id as t_id, t.name as t_name, t.password as t_password,
                    t.code as t_code, t.college_id as t_collegeId, t.remark as t_remark,
                c.name as c_name, c.remark as c_remark, c.id as c_id
            from teacher t
            left join college c
            on t.college_id = c.id
            where t.name like :keyword or t.code like :keyword or c.name like :keyword or c.remark like :keyword
        """;

        BeanPropertyRowMapper<Teacher> rowMapper1 = new BeanPropertyRowMapperWithPrefix<Teacher>("t_");
        rowMapper1.setMappedClass(Teacher.class);
        BeanPropertyRowMapper<College> rowMapper2 = new BeanPropertyRowMapperWithPrefix<College>("c_");
        rowMapper2.setMappedClass(College.class);

        RowMapper<TeacherAndCollege> rowMapper = new RowMapper<TeacherAndCollege>() {
            public TeacherAndCollege mapRow(ResultSet rs, int rowNum) throws SQLException {
                Teacher teacher = rowMapper1.mapRow(rs, rowNum);
                College college = rowMapper2.mapRow(rs, rowNum);

                TeacherAndCollege teacherAndCollege = new TeacherAndCollege();
                teacherAndCollege.setTeacher(teacher);
                teacherAndCollege.setCollege(college);

                return teacherAndCollege;
            }
        };
        List<TeacherAndCollege> items = jdbcClient.sql(sql)
                .param("keyword", "%" + keyword + "%")
                .query(rowMapper)
                .list();

        return AjaxResult.success().selectResult(items.size(), items);
    }

    @PutMapping("/teachers")
    public AjaxResult update(@RequestBody Teacher teacher) {
        /* 注意college_id列，对应collegeId属性 */
        String sql = """
            update teacher set 
                name = :name, 
                code = :code, 
                password = :password, 
                college_id = :collegeId, 
                remark = :remark 
            where id = :id
        """;

        int n = jdbcClient.sql(sql)
                .paramSource(teacher)
                .update();

        return AjaxResult.success().updateResult(n);
    }

    @DeleteMapping("/teachers/{id}")
    public AjaxResult deleteById(@PathVariable("id") int expectedId) {
        String sql = "delete from teacher where id = :id";

        int n = jdbcClient.sql(sql)
                .param("id", expectedId)
                .update();

        return AjaxResult.success().deleteResult(n);
    }

    @PostMapping("/teachers")
    public AjaxResult insert(@RequestBody Teacher teacher) {
        /* 注意college_id列，对应collegeId属性 */
        String sql = """
            insert into teacher
                (name, code, password, remark, college_id) 
            values
                (:name, :code, :password, :remark, :collegeId)
        """;

        int n = jdbcClient.sql(sql)
                .paramSource(teacher)
                .update();

        return AjaxResult.success().insertResult(n);
    }
}