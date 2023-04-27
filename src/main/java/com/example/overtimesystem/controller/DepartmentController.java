package com.example.overtimesystem.controller;

import com.example.overtimesystem.dto.DepartmentDto;
import com.example.overtimesystem.dto.ProjectDto;
import com.example.overtimesystem.dto.UserDto;
import com.example.overtimesystem.repository.DepartmentRepository;
import com.example.overtimesystem.repository.UserRepository;
import com.example.overtimesystem.service.DepartmentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    private final UserRepository userRepository;

    @RequestMapping(value = "/department" , method = RequestMethod.GET)
    public String department(Model model, DepartmentDto departmentDto, Principal principal) {
        model.addAttribute("department", new DepartmentDto());
        List<DepartmentDto> departments = departmentService.getAllDepartment();
        model.addAttribute("departments", departments);
        model.addAttribute("user",userRepository.findByEmail(principal.getName()));
        return "department";
    }

    @RequestMapping(value = "/department/create",method = RequestMethod.POST)
    public String createDepartment(@Valid @ModelAttribute("department") DepartmentDto departmentDto, BindingResult result, Model model,
                                   RedirectAttributes redirectAttributes,Principal principal) {

        if (result.hasErrors()) {
            model.addAttribute("department", departmentDto);
            model.addAttribute("user",userRepository.findByEmail(principal.getName()));
            return "/department";
        }
        try {
            departmentService.createDepartment(departmentDto);
        }catch (RuntimeException e){
            redirectAttributes.addFlashAttribute("error",e.getMessage());
            model.addAttribute("user",userRepository.findByEmail(principal.getName()));

            return "redirect:/department?fail";
        }
        return "redirect:/department?success";
    }

}
