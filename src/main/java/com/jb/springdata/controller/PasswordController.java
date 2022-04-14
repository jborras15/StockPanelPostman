package com.jb.springdata.controller;

import com.jb.springdata.entity.User;
import com.jb.springdata.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping()
public class PasswordController {

    @Autowired
    private UserService userService;


    @GetMapping(value = "/change")
    public String changePassword(Model model) {
        model.addAttribute("user", new User());
        return "change-password";
    }
    @GetMapping(value = "/recover")
    public String recoverPassword(Model model) {
        model.addAttribute("user", new User());
        return "recover-password";
    }







}
