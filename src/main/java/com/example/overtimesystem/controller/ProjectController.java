package com.example.overtimesystem.controller;

import com.example.overtimesystem.dto.ProjectDto;
import com.example.overtimesystem.entity.Project;
import com.example.overtimesystem.repository.DepartmentRepository;
import com.example.overtimesystem.repository.ProjectRepository;
import com.example.overtimesystem.service.DepartmentService;
import com.example.overtimesystem.service.ProjectService;
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

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    private final DepartmentService departmentService;

    private final ProjectRepository projectRepository;

    private final DepartmentRepository departmentRepository;

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public String project(Model model, ProjectDto projectDto) {
        model.addAttribute("project", new ProjectDto());
        model.addAttribute("departments", departmentService.getAllDepartment());
        List<ProjectDto> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "project/project";
    }

    @RequestMapping(value = "/project/create", method = RequestMethod.POST)
    public String createProject(@Valid @ModelAttribute("project") ProjectDto projectDto, BindingResult result, Model model,
                                RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("project", projectDto);
            return "redirect:/project";
        }
        try {
            projectService.createProject(projectDto);

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/project?fail";
        }
        return "redirect:/project?success";
    }

    @RequestMapping(value = "/project/delete/{id}", method = RequestMethod.GET)
    public String deleteProject(@ModelAttribute("project") ProjectDto projectDto,
                                @PathVariable int id, Model model) {
        projectService.deleteProject(id);
        return "redirect:/project";
    }

    @RequestMapping(value = "/project/update/{id}", method = RequestMethod.GET)
    public String updateProject(@PathVariable("id") int id, Model model,
                                RedirectAttributes redirectAttributes) {
        Optional<Project> project=projectRepository.findById(id);
        if(project.isPresent()){
            Project presentProject=project.get();
            model.addAttribute("project", new ProjectDto(presentProject));
            model.addAttribute("department",presentProject.getDepartment());
            return "/project/update-project";
        }
        redirectAttributes.addFlashAttribute("error","Something went wrong");
        return "redirect:/update-project?fail";
    }

    @RequestMapping(value = "/project/update", method = RequestMethod.POST)
    public String processUpdateProject(@ModelAttribute("project")ProjectDto project,
                                       HttpSession session,Model model){

        projectService.updateProject(project.getId(),project);
        session.setAttribute("message","Project updated successfully");
        return "redirect:/project";
    }

}
