package com.jb.springdata.event.listener;

import com.jb.springdata.event.RegistrationCompleteEvent;
import com.jb.springdata.entity.User;
import com.jb.springdata.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import java.util.Properties;
import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements
        ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event){
        //Crear el token de verificación para el usuario con enlace
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token,user);
        //enviar Mail a usuario

        String url =
                event.getApplicationUrl()
                        + "/verifyRegistration?token="
                        + token;


        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("jborras15@gmail.com");
        mailSender.setPassword("wbwvfihyjzcecieh");

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.starttls.required", "true");

        mailSender.setJavaMailProperties(properties);
        MimeMessagePreparator message = mimeMessage -> {
            mimeMessage.setFrom("jborras15@gmail.com");
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            mimeMessage.setText( url);
        };

        try {
            mailSender.send(message);
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
                log.info("Click the link to verify your account: {}",
                url);

    }

}
