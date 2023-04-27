package com.example.overtimesystem.controller;

import com.example.overtimesystem.dto.ProjectDto;
import com.example.overtimesystem.dto.ProjectMemberDto;
import com.example.overtimesystem.entity.Project;
import com.example.overtimesystem.repository.ProjectMemberRepository;
import com.example.overtimesystem.repository.ProjectRepository;
import com.example.overtimesystem.repository.UserRepository;
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

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    private final ProjectMemberRepository projectMemberRepository;

    private final DepartmentService departmentService;

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    @RequestMapping(value = "/project/project", method = RequestMethod.GET)
    public String project(Model model, ProjectDto projectDto, Principal principal) {
        model.addAttribute("project", new ProjectDto());
        model.addAttribute("departments", departmentService.getAllDepartment());
        List<ProjectDto> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        model.addAttribute("user",userRepository.findByEmail(principal.getName()));
        return "project/project";
    }

    @RequestMapping(value = "/project/create-project", method = RequestMethod.GET)
    public String createProjectPage(Model model, ProjectDto projectDto,Principal principal) {
        model.addAttribute("project", new ProjectDto());
        model.addAttribute("departments", departmentService.getAllDepartment());
        List<ProjectDto> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        model.addAttribute("user",userRepository.findByEmail(principal.getName()));

        return "project/create-project";
    }

    @RequestMapping(value = "/project/create", method = RequestMethod.POST)
    public String createProject(@Valid @ModelAttribute("project") ProjectDto projectDto, BindingResult result, Model model,
                                RedirectAttributes redirectAttributes,Principal principal) {

        if (result.hasErrors()) {
            model.addAttribute("project", projectDto);
            model.addAttribute("user",userRepository.findByEmail(principal.getName()));

            return "redirect:/project/create-project";
        }
        try {
            projectService.createProject(projectDto);

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            model.addAttribute("user",userRepository.findByEmail(principal.getName()));

            return "redirect:/project/create-project?fail";
        }
        model.addAttribute("user",userRepository.findByEmail(principal.getName()));
        return "redirect:/project/create-project?success";
    }

    @RequestMapping(value = "/project/delete/{id}", method = RequestMethod.GET)
    public String deleteProject(@ModelAttribute("project") ProjectDto projectDto,
                                @PathVariable int id, Model model,Principal principal) {
        projectService.deleteProject(id);
        model.addAttribute("user",userRepository.findByEmail(principal.getName()));
        return "redirect:/project/project";
    }

    @RequestMapping(value = "/project/update-project/{id}", method = RequestMethod.GET)
    public String updateProject(@PathVariable("id") int id, Model model,
                                RedirectAttributes redirectAttributes,Principal principal) {
        Optional<Project> project=projectRepository.findById(id);
        if(project.isPresent()){
            Project presentProject=project.get();
            model.addAttribute("project", new ProjectDto(presentProject));
            model.addAttribute("department",presentProject.getDepartment());
            model.addAttribute("user",userRepository.findByEmail(principal.getName()));

            return "/project/update-project";
        }
        redirectAttributes.addFlashAttribute("error","Something went wrong");
        model.addAttribute("user",userRepository.findByEmail(principal.getName()));

        return "redirect:project/update-project/"+id+"?fail";
    }

    @RequestMapping(value = "/project/update-project", method = RequestMethod.POST)
    public String processUpdateProject(@ModelAttribute("project")ProjectDto project,Model model,
                                       HttpSession session,Principal principal){

        projectService.updateProject(project.getId(),project);
        session.setAttribute("message","Project updated successfully");
        model.addAttribute("user",userRepository.findByEmail(principal.getName()));

        return "redirect:/project/update-project/"+project.getId()+"?success";
    }

    @RequestMapping(value = "/project/assign-member/{id}", method = RequestMethod.GET)
    public String assignMemberForm(@PathVariable("id") int id, Model model,Principal principal,
                                RedirectAttributes redirectAttributes) {
        Optional<Project> project=projectRepository.findById(id);
        if(project.isPresent()){
            Project presentProject=project.get();
            model.addAttribute("projectMember",new ProjectMemberDto());
            model.addAttribute("project", new ProjectDto(presentProject));
            model.addAttribute("user",userRepository.findByEmail(principal.getName()));

            model.addAttribute("users",userRepository.findAllByDepartmentId(presentProject.getDepartment().getId()));
            return "/project/assign-member";
        }
        redirectAttributes.addFlashAttribute("error","Something went wrong");
        model.addAttribute("user",userRepository.findByEmail(principal.getName()));

        return "redirect:/project/assign-member?fail";
    }
}
