package com.example.demo.Controller;

import com.example.demo.Models.*;
import com.example.demo.Response.ResponseHandler;
import com.example.demo.Service.AccountService;
import com.example.demo.Service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@RestController
public class AccountsController {

    @Autowired
   RestTemplate restTemplate;
    @Autowired
    AccountService accountService;
    @Autowired
    OtpService otpService;

    @PostMapping("/sendcode/changePassword")
    public ResponseEntity<Object> sendCodeChangePassword(@RequestBody ChangePasswordForm changePasswordForm,
                                                         @RequestHeader String token){
        if(changePasswordForm.getEmail().isEmpty())
            return ResponseHandler.response(HttpStatus.BAD_REQUEST, true, "Invalid email");
        if(token.isEmpty())
            return ResponseHandler.response(HttpStatus.NOT_FOUND, true,"invalid token");
        AuthenticationToken authenticationToken = restTemplate.getForObject("http://localhost:9595/authToken?token="+token,AuthenticationToken.class);
        if(Objects.isNull(authenticationToken) || authenticationToken.isDeleted())
            return ResponseHandler.response(HttpStatus.BAD_REQUEST, true, "Invalid Token");
        if(authenticationToken.getUser().getEmail().equals(changePasswordForm.getEmail())){
        int Otp = otpService.sendOtp(authenticationToken.getUser(),"OTP for Change Password -->");
        return ResponseHandler.response(HttpStatus.OK,false, "OTP send Successfully");
        }

        return null;

    }

    @PutMapping("/verifyOtp/changePassword")
    public ResponseEntity<Object> verifyChangePasswordOtp(@RequestParam String token, @RequestParam int otp){
        if(token.isEmpty())
            return ResponseHandler.response(HttpStatus.BAD_REQUEST,true, "Invalid Token");
        AuthenticationToken authenticationToken = restTemplate.getForObject("http://localhost:9595/authToken?token="+token,AuthenticationToken.class);
        if(Objects.isNull(authenticationToken) || authenticationToken.isDeleted())
            return ResponseHandler.response(HttpStatus.BAD_REQUEST, true, "Invalid Token");
        User user = authenticationToken.getUser();
        if(Objects.isNull(user) || !user.isVerified())
        return ResponseHandler.response(HttpStatus.NOT_FOUND, true, "User doesn't exits");
        Otp otp1 = otpService.findByOtpAndUser(otp,user);
        // otpService.findByOtpAndUser(otp, user);
        if(Objects.isNull(otp1) || otp1.isDeleted())
            return ResponseHandler.response(HttpStatus.NOT_FOUND, true, "Invalid OTP");
        if(Objects.nonNull(otp1) && !otp1.isDeleted()){
            otp1.setDeleted(true);
            otpService.saveOtp(otp1);
            return ResponseHandler.response(HttpStatus.OK, false, "OTP verify success");
        }
        return ResponseHandler.response(HttpStatus.BAD_REQUEST,true, "Error");
    }


    @PutMapping("/reset/password")
    public ResponseEntity<Object> resetpassword(@RequestBody RestPasswordForm restPasswordForm){
        if(restPasswordForm.getToken().isEmpty() || restPasswordForm.getEmail().isEmpty() || restPasswordForm.getNewPassword().isEmpty() || restPasswordForm.getConfirmPassword().isEmpty())
            return ResponseHandler.response(HttpStatus.BAD_REQUEST,true, "Invalid Data");
        if(!restPasswordForm.getConfirmPassword().equals(restPasswordForm.getNewPassword()))
            return ResponseHandler.response(HttpStatus.BAD_REQUEST,true, "New Password and Confirm Password are not match");
        AuthenticationToken authenticationToken = restTemplate.getForObject("http://localhost:9595/authToken?token="+restPasswordForm.getToken(),AuthenticationToken.class);
        if(authenticationToken.isDeleted())
            return ResponseHandler.response(HttpStatus.BAD_REQUEST,true, "Invalid User");
        User user = authenticationToken.getUser();
        if(Objects.isNull(user) ||!user.isVerified())
            return ResponseHandler.response(HttpStatus.NOT_FOUND, true, "User not Verified");
        User savedUser = accountService.saveRestPassword(restPasswordForm, user, authenticationToken);

        return ResponseHandler.response(HttpStatus.OK, false, "Password Changed Successful", savedUser);
    }
    @GetMapping("/{userId}")
    public User getUser(@PathVariable String userId){
      User user = restTemplate.getForObject("http://localhost:9595/"+ Integer.parseInt(userId),User.class);
//        User user = WebClient.builder().build().get().uri("http://localhost:9595/1").retrieve().bodyToMono(User.class).block();
       return user;
    }

 @GetMapping("/hello")
    public String getHello(){
        return "Hello";
 }

 @GetMapping("/saveAuth")
    public ResponseEntity<Object> saveAuht(@RequestParam String token){
        Map<String,Object> map = new TreeMap<String,Object>();
     AuthenticationToken authenticationToken = restTemplate.getForObject("http://localhost:9595/authToken?token="+token,AuthenticationToken.class);
     map.put("auth",authenticationToken);

        User user = restTemplate.postForObject("http://localhost:9595/auht", authenticationToken,User.class);
//         System.out.println(user.getBody());
         return ResponseHandler.response(HttpStatus.OK, false, "user value", user);

 }
}
