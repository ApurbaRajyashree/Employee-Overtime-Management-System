package com.example.overtimesystem.helper;


import com.example.overtimesystem.entity.User;
import com.example.overtimesystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class LoggedInUser {

    private final UserRepository userRepo;

    public User getCurrentUser(){
        //get login users email
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        //find login user based on email address and  return login user object
        return userRepo.findByEmail(username);
    }
}
