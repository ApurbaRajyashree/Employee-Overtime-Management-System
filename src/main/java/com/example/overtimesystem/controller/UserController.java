package com.example.overtimesystem.controller;

import com.example.overtimesystem.dto.UserDto;
import com.example.overtimesystem.repository.UserRepository;
import com.example.overtimesystem.service.DepartmentService;
import com.example.overtimesystem.service.OverTimeMasterService;
import com.example.overtimesystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping()
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;
    private final DepartmentService departmentService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String home(Model model, UserDto userDto, Principal principal) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("departments", departmentService.getAllDepartment());
        model.addAttribute("user",userRepository.findByEmail(principal.getName()));
        return "register";
    }

    @RequestMapping(value = "/register/save", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result,
                           RedirectAttributes redirectAttributes, Model model,Principal principal) {
        if (result.hasErrors()) {
            Map<String, String> requestErrors = validateRequest(result);
            redirectAttributes.addFlashAttribute("requestError", requestErrors);
            model.addAttribute("user",userRepository.findByEmail(principal.getName()));

            return "redirect:/register";
        }
        try {
            userService.createUser(userDto);

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            model.addAttribute("user",userRepository.findByEmail(principal.getName()));

            return "redirect:/register?fail";
        } catch (Exception e) {
            String msg = "";
            if (e.getMessage().contains("uk_mobile_number")) {
                msg += "Sorry Mobile Number already Exist.\n";
            } else if (e.getMessage().contains("uk_email")) {
                msg += "Sorry Email already exists. \n";
            }
            redirectAttributes.addFlashAttribute("error", msg);
            model.addAttribute("user",userRepository.findByEmail(principal.getName()));

            return "redirect:/register?fail";
        }
        model.addAttribute("user",userRepository.findByEmail(principal.getName()));
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model,Principal principal) {
        List<UserDto> users = userService.getAllUser();
        model.addAttribute("users", users);
        model.addAttribute("user",userRepository.findByEmail(principal.getName()));

        return "users";
    }


    public Map<String, String> validateRequest(BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return null;
        }
        Map<String, String> errors = new HashMap<>();
        bindingResult.getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return errors;

    }

}