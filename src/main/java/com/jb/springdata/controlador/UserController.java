package com.jb.springdata.controlador;

import com.jb.springdata.modelo.User;
import com.jb.springdata.servicio.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public String index(@RequestParam(value = "page", defaultValue = "0") Integer page, Model model) {

        Page<User> userPages = userService.findAll(PageRequest.of(page, 10));

        model.addAttribute("users", userPages.getContent());
        model.addAttribute("totalPages", userPages.getTotalPages());

        model.addAttribute("newUser", new User());

        return "users";
    }

    @PostMapping
    public String createUser(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:users";
    }


}

