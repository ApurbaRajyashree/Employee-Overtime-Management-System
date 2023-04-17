package com.example.overtimesystem.controller;

import com.example.overtimesystem.dto.DepartmentDto;
import com.example.overtimesystem.dto.ProjectDto;
import com.example.overtimesystem.entity.Project;
import com.example.overtimesystem.service.DepartmentService;
import com.example.overtimesystem.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    private final DepartmentService departmentService;

    @RequestMapping(value = "/project" , method = RequestMethod.GET)
    public String project(Model model, ProjectDto projectDto) {
        model.addAttribute("project", new ProjectDto());
        model.addAttribute("departments", departmentService.getAllDepartment());
        List<ProjectDto> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "project";
    }

    @RequestMapping(value = "/project/create",method = RequestMethod.POST)
    public String createProject(@Valid @ModelAttribute("project") ProjectDto projectDto, BindingResult result, Model model,
                                   RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("project", projectDto);
            return "/project";
        }
        try {
            projectService.createProject(projectDto);

        }catch (RuntimeException e){
            redirectAttributes.addFlashAttribute("error",e.getMessage());
            return "redirect:/project?fail";
        }
        return "redirect:/project?success";
    }

}
