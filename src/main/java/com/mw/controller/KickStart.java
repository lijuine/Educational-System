package com.mw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class KickStart {
    @GetMapping(path = {"/", "/index"})
    @ResponseBody
    public Map testJson() {
        Map map = new HashMap();
        map.put("time", LocalDateTime.now());
        return map;
    }
}
