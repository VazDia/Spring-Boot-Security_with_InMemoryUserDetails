package com.security.security.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping(path = "User")
    public String displayUser(){
        return "Hello User";
    }
    @GetMapping(path = "Admin")
    public String displayAdmin(){
        return "Hello Admn";
    }
}
