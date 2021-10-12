package com.example.demo.Service;

import com.example.demo.Models.Otp;
import com.example.demo.Models.User;
import com.example.demo.repo.OtpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {

    @Autowired
    OtpRepo otpRepo;

    @Autowired
    JavaMailSender mailSender;

    public int sendOtp(User user,String text) {
        SimpleMailMessage simpleMailMessage =new SimpleMailMessage();
        simpleMailMessage.setFrom("vishupal251201@gmail.com");
        simpleMailMessage.setTo(user.getEmail());
        int otp = (100000 + new Random().nextInt(900000));
        Otp savedOtp = new Otp();
        savedOtp.setOtp(otp);
        savedOtp.setUser(user);
        otpRepo.save(savedOtp);
        String message = text + otp;
        simpleMailMessage.setText(message);
        mailSender.send(simpleMailMessage);
        return otp;
    }

    public void deleteOtp(Otp otp1) {
        otpRepo.deleteByOtp(otp1);
    }

    public Otp findByOtp(int otp) { return otpRepo.findByOtp(otp);
    }

    public Otp setIsDeleted(Otp otp1) {
        otp1.setDeleted(true);
        return otpRepo.save(otp1);
    }

    public Otp findByUser(User user) {
        return otpRepo.findByUser(user);
    }


    public Otp findByOtpAndUser(int otp, User user) {
        return otpRepo.findByOtpAndUser(otp, user);
    }

    public void saveOtp(Otp otp1) {
        otpRepo.save(otp1);
    }
}
