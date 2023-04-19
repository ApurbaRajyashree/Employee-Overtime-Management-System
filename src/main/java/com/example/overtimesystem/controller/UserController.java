package com.example.overtimesystem.controller;

import com.example.overtimesystem.dto.DepartmentDto;
import com.example.overtimesystem.dto.UserDto;
import com.example.overtimesystem.entity.User;
import com.example.overtimesystem.service.DepartmentService;
import com.example.overtimesystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping()
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final DepartmentService departmentService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String home(Model model, UserDto userDto) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("departments", departmentService.getAllDepartment());
        return "register";
    }

    @RequestMapping(value = "/register/save", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }
        userService.createUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.getAllUser();
        model.addAttribute("users", users);
        return "users";
    }

//    @GetMapping(value = "dashboard")
//    public ModelMap mmDashboard() {
//        return new ModelMap();
//    }

}