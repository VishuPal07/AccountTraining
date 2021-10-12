package com.example.demo.repo;

import com.example.demo.Models.Otp;
import com.example.demo.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepo extends JpaRepository<Otp, Integer> {
    Otp findByOtp(int otp);

    void deleteByOtp(Otp otp1);

    Otp findByUser(User user);

    Otp findByOtpAndUser(int otp, User user);

}
