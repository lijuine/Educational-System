package com.mw.controller;

import com.mw.domain.AjaxResult;
import com.mw.domain.Admin;
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
public class AdminController {
    @Autowired
    private JdbcClient jdbcClient;

    @GetMapping("/admins")
    public AjaxResult getAll() {
        String sql = """
            select id, name, password, code, remark
            from admin
        """;
        List<Admin> items = jdbcClient.sql(sql)
                .query(Admin.class)
                .list();

        return AjaxResult.success().selectResult(items.size(), items);
    }

    @GetMapping("/admins/{id}")
    public AjaxResult getById(@PathVariable("id") int expectedId) {
        String sql = """
            select id, name, password, code, remark
            from admin
            where id = :id
        """;

        List<Admin> items = jdbcClient.sql(sql)
                .param("id", expectedId)
                .query(Admin.class)
                .list();

        return AjaxResult.success().selectResult(items.size(), items);
    }

    @PutMapping("/admins")
    public AjaxResult update(@RequestBody Admin admin) {
        String sql = """
            update admin set 
                name = :name,
                password = :password,
                code = :code,
                remark = :remark 
            where id = :id
        """;

        int n = jdbcClient.sql(sql)
                .paramSource(admin)
                .update();

        return AjaxResult.success().updateResult(n);
    }

    @DeleteMapping("/admins/{id}")
    public AjaxResult deleteById(@PathVariable("id") int expectedId) {
        String sql = "delete from admin where id = :id";

        int n = jdbcClient.sql(sql)
                .param("id", expectedId)
                .update();

        return AjaxResult.success().deleteResult(n);
    }

    @PostMapping("/admins")
    public AjaxResult insert(@RequestBody Admin admin) {
        String sql = """
            insert into admin
                (name, password, code, remark) 
            values
                (:name, :password, :code, :remark)
        """;

        int n = jdbcClient.sql(sql)
                .paramSource(admin)
                .update();

        return AjaxResult.success().insertResult(n);
    }

    @GetMapping("/admins/search")
    public AjaxResult search(@RequestParam("keyword") String keyword) {
        String sql = """
            select id, name, password, code, remark
            from admin
            where name like :keyword
        """;

        List<Admin> items = jdbcClient.sql(sql)
                .param("keyword", "%" + keyword + "%")
                .query(Admin.class)
                .list();

        return AjaxResult.success().selectResult(items.size(), items);
    }

    @PostMapping("/admins/login")
    public AjaxResult login(@RequestBody Admin admin) {
        String sql = """
            select id, name, code, password
            from admin
            where name = :name and password = :password
        """;

        List<Admin> users = jdbcClient.sql(sql)
                .param("name", admin.getName())
                .param("code", admin.getCode())
                .param("password", admin.getPassword())
                .query(Admin.class)
                .list();

        if (users.isEmpty()) {
            return AjaxResult.bad().msg("用户名/工号或密码错误");
        } else {
            // 登录成功，可以设置一些登录后的逻辑，比如生成token等
            return AjaxResult.success().data(users.get(0));
        }
    }

    @PostMapping("/admins/register")
    public AjaxResult register(@RequestBody Admin admin) {
        String sql = """
            insert into aadmin (username, code, password)
            values (:name, :code, :password)
        """;

        int n = jdbcClient.sql(sql)
                .paramSource(admin)
                .update();

        if (n > 0) {
            return AjaxResult.success().msg("注册成功");
        } else {
            return AjaxResult.error().msg("注册失败");
        }
    }

}