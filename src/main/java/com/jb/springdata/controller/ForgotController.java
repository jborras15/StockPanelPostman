package com.jb.springdata.controller;

import com.jb.springdata.entity.User;
import com.jb.springdata.repository.UserRepository;
import com.jb.springdata.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Controller
public class ForgotController {

    Random random = new Random(1000);

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


   /* @GetMapping(value = "/change")
    public String changePassword(Model model) {
        model.addAttribute("user", new User());
        return "password_change_form";
    }*/



    //email id from open handler
    @RequestMapping("/forgot")
    public String openEmailForm(){
        return "forgot_email_form";
    }

    @PostMapping("/send-otp")
    public String sendOTP(@RequestParam("email") String email, HttpSession session) {
        System.out.println("EMAIL" + email);

        //generating otp of 4 digit

        int otp =  random.nextInt(999999);

        System.out.println("OTP " + otp);

        //write code for send otp to email ...
        String subject = "OTP from SCM";
        String message = ""
        + "<div style='border:1px solid #e2e2e2; padding:20px'>"
        + "<h1>"
        + "OTP is "
        + otp
        + "</n"
        + "/h1"
        +"</div>";
        String to = email;

        boolean flag = this.emailService.sendEmail(subject, message, to);


        if (flag) {

            session.setAttribute("myotp",otp);
            session.setAttribute("email",email);

            return "verify_otp";
        } else {

            session.setAttribute("message", "check your email id !!");
            return "forgot_email_form";
        }
    }

    //VERIFY OTP

    @PostMapping("/verify-otp")
    public  String verifyOtp(@RequestParam("otp") int otp,HttpSession session)
    {
        int myOtp= (int)session.getAttribute("myotp");
        String email = (String) session.getAttribute("email");
        if (myOtp == otp) {

            //password change form
            User user = userRepository.getUserByUserName(email);

            if (user==null)
            {
                //send error message
                session.setAttribute("message", "User does not exists with this email !!");

                return "forgot_email_form";
            }else{
                //send change password form
            }
            return  "password_change_form";
        }else{
            session.setAttribute("message","you have entered wrong otp !!");
            return "verify_otp";
        }
    }

    //change password
    @PostMapping("/change-password")
    public  String changePassword(@RequestParam("newpassword") String newpassword, HttpSession session){
        String email = (String) session.getAttribute("email");
        User user = userRepository.getUserByUserName(email);
        user.setPassword(passwordEncoder.encode(newpassword));
        userRepository.save(user);
         return "redirect:/login?change=password changed successfully";

    }

   /* @PostMapping("/change")
    public  String change(@RequestParam("newpassword") String newpassword, HttpSession session, @RequestParam("email") String email){
        User user = userRepository.getUserByUserName(email);
        user.setPassword(passwordEncoder.encode(newpassword));
        userRepository.save(user);
        return "redirect:/user";
        //return "redirect:/login?change=password changed successfully";

    }*/
}
