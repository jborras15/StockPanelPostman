package com.jb.springdata.controlador;

import com.jb.springdata.Entity.User;
import com.jb.springdata.event.RegistrationCompleteEvent;
import com.jb.springdata.model.UserDTO;
import com.jb.springdata.servicio.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


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
    @Autowired
    private ApplicationEventPublisher publisher;


    @PostMapping
    public  String registerUser(@ModelAttribute UserDTO userDTO, final HttpServletRequest request){
        User user =userService.registerUser(userDTO);
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





}

