package com.example.demo.Service;

import com.example.demo.Models.AuthenticationToken;
import com.example.demo.Models.Otp;
import com.example.demo.Models.RestPasswordForm;
import com.example.demo.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
public class AccountService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    JavaMailSender mailSender;


    public User saveRestPassword(RestPasswordForm restPasswordForm, User user, AuthenticationToken authenticationToken) {
        user.setPassword(restPasswordForm.getNewPassword());
        authenticationToken.setDeleted(true);
       ResponseEntity<Object> user1 = restTemplate.postForEntity("http://localhost:9595/saveUser",user,Object.class);
       ResponseEntity<Object> authenticationToken1 = restTemplate.postForEntity("http://localhost:9595/saveAuthToken",authenticationToken,Object.class);
       if(user1.getStatusCode().equals(200) && authenticationToken1.getStatusCode().equals(200)){
           return user;
       }
        return null;
    }
}
