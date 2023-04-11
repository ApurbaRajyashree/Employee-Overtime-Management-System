package com.example.overtimesystem.controller;

import com.example.overtimesystem.dto.DepartmentDto;
import com.example.overtimesystem.dto.UserDto;
import com.example.overtimesystem.service.DepartmentService;
import com.example.overtimesystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;

@Controller
@RequestMapping()
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final DepartmentService departmentService;
    @RequestMapping(value = "/register" , method = RequestMethod.GET)
    public String home(Model model, UserDto userDto) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("departments", departmentService.getAllDepartment());
        return "register";
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public String register(@ModelAttribute("user") UserDto userDto, Model model) {
        userService.createUser(userDto);
        return "dashboard";
    }
}
