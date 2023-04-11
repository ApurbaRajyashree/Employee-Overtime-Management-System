package com.example.overtimesystem.controller;

import com.example.overtimesystem.dto.DepartmentDto;
import com.example.overtimesystem.dto.UserDto;
import com.example.overtimesystem.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @RequestMapping(value = "/department" , method = RequestMethod.GET)
    public String department(Model model, DepartmentDto departmentDto) {
        model.addAttribute("department", new DepartmentDto());
        return "department";
    }

    @RequestMapping(value = "/department/create",method = RequestMethod.POST)
    public String createDepartment(@ModelAttribute("department") DepartmentDto departmentDto, Model model) {
        departmentService.createDepartment(departmentDto);
        return "department";
    }
}
