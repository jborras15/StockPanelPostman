package com.jb.springdata.controller;


import com.jb.springdata.entity.Authority;
import com.jb.springdata.entity.User;
import com.jb.springdata.event.RegistrationCompleteEvent;
import com.jb.springdata.repository.AuthorityRepository;
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
import java.util.Map;

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

    @Autowired
    private AuthorityRepository authorityRepository;



    @GetMapping
    public String index(
            @RequestParam Map<String, Object> params,
            @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            Model model
    ) {
        String searchTerm = (String) params.get("search");

        Page<User> users;

        if (searchTerm != null) {
            users = userRepository.findUserByFirstNameContains(searchTerm,PageRequest.of(pageNumber, size));
        } else {
            users = userRepository.findAll(PageRequest.of(pageNumber, size));
        }

        model.addAttribute("users", users.getContent());
        model.addAttribute("pages", new int[users.getTotalPages()]);
        model.addAttribute("currentPage", pageNumber  );
        model.addAttribute("previous", pageNumber -1  );
        model.addAttribute("next", pageNumber + 1);
        model.addAttribute("last", (users.getTotalPages() -1) );


        model.addAttribute("newUser", new User());
        return "users";
    }


    @PostMapping
    public String createUser(@ModelAttribute User user, final HttpServletRequest request) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        user.setUsername(user.getEmail());
        userService.save(user);

        Authority defaultRole = new Authority();
        defaultRole.setUsername(user.getEmail());
        defaultRole.setAuthority("ROLE_USER");

        authorityRepository.save(defaultRole);

        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "redirect:users";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }


}

