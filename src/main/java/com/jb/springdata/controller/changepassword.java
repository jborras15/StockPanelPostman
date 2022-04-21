package com.jb.springdata.controller;


import com.jb.springdata.entity.User;
import com.jb.springdata.helper.Message;
import com.jb.springdata.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/settings")
public class changepassword {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //open settings handler
    @GetMapping
    public String openSettings() {
        return "settings";
    }

    //change password handler
    @PostMapping
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 Principal principal, HttpSession session) {
        System.out.println("OLD PASSWORD  " + oldPassword);
        System.out.println("NEW PASSWORD  " + newPassword);

        String username = principal.getName();
        User currentUser = userRepository.getUserByUserName(username);
        System.out.println(currentUser.getPassword());

        if (passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            //change the password
            currentUser.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(currentUser);
            session.setAttribute("message",new Message(
                    "Your password is successfuly changed.. ", "success"));
        } else {
            //error...
            session.setAttribute("message", new Message(
                    "please Enter corrent old password!!.. ", "danger"));
            return "redirect:/settings";
        }
        return "redirect:/";
    }


}
