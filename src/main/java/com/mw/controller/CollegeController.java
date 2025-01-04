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
public class CollegeController {
    @Autowired
    private JdbcClient jdbcClient;

    @GetMapping("/colleges")
    public AjaxResult getAll() {
        String sql = """
            select id, name, remark
            from college
        """;
        List<College> items = jdbcClient.sql(sql)
                .query(College.class)
                .list();

        return AjaxResult.success().selectResult(items.size(), items);
    }

    @GetMapping("/colleges/{id}")
    public AjaxResult getById(@PathVariable("id") int expectedId) {
        String sql = """
            select id, name, remark
            from college
            where id = :id
        """;

        List<College> items  =jdbcClient.sql(sql)
                .param("id", expectedId)
                .query(College.class)
                .list();

        return AjaxResult.success().selectResult(items.size(), items);
    }

    @PutMapping("/colleges")
    public AjaxResult update(@RequestBody College college) {
        String sql = """
            update college set 
                name = :name,
                remark = :remark 
            where id = :id
        """;

        int n = jdbcClient.sql(sql)
                .paramSource(college)
                .update();

        return AjaxResult.success().updateResult(n);
    }

    @DeleteMapping("/colleges/{id}")
    public AjaxResult deleteById(@PathVariable("id") int expectedId) {
        String sql = "delete from college where id = :id";

        int n = jdbcClient.sql(sql)
                .param("id", expectedId)
                .update();

        return AjaxResult.success().deleteResult(n);
    }

    @PostMapping("/colleges")
    public AjaxResult insert(@RequestBody College college) {
        String sql = """
            insert into college
                (name, remark) 
            values
                (:name, :remark)
        """;

        int n = jdbcClient.sql(sql)
                .paramSource(college)
                .update();

        return AjaxResult.success().insertResult(n);
    }

    @GetMapping("/colleges/search")
    public AjaxResult search(@RequestParam("keyword") String keyword) {
        String sql = """
            select id, name, remark
            from college
            where name like :keyword
        """;

        List<College> items = jdbcClient.sql(sql)
                .param("keyword", "%" + keyword + "%")
                .query(College.class)
                .list();

        return AjaxResult.success().selectResult(items.size(), items);
    }
}