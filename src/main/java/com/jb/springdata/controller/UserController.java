package com.jb.springdata.controller;

import com.jb.springdata.entity.Product;
import com.jb.springdata.entity.User;
import com.jb.springdata.event.RegistrationCompleteEvent;
import com.jb.springdata.repository.UserRepository;
import com.jb.springdata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public String index(@RequestParam(value = "page", defaultValue = "0") Integer page, Model model) {
        Page<User> userPages = userService.findAll(PageRequest.of(page, 10));

        model.addAttribute("users", userPages.getContent());
        model.addAttribute("totalPages", userPages.getTotalPages());

        model.addAttribute("newUser", new User());

        return "users";
    }

    @PostMapping
    public String createUser(@ModelAttribute User user, final HttpServletRequest request) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        publisher.publishEvent(new RegistrationCompleteEvent(user,  applicationUrl(request)));
        return "redirect:users";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }

    @GetMapping("/deleteUser")
    public String delete(User user) {
        userRepository.delete(user);
        return "redirect:/users";
    }
}

