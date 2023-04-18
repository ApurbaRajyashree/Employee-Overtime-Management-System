package com.example.overtimesystem.controller;


import com.example.overtimesystem.dto.DepartmentDto;
import com.example.overtimesystem.dto.ProjectDto;
import com.example.overtimesystem.dto.ProjectMemberDto;
import com.example.overtimesystem.dto.UserDto;
import com.example.overtimesystem.entity.Project;
import com.example.overtimesystem.entity.ProjectMember;
import com.example.overtimesystem.entity.User;
import com.example.overtimesystem.service.DepartmentService;
import com.example.overtimesystem.service.ProjectMemberService;
import com.example.overtimesystem.service.ProjectService;
import com.example.overtimesystem.service.UserService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    private final UserService userService;

    private final ProjectService projectService;
    private final DepartmentService departmentService;

    @RequestMapping(value = "/project-member", method = RequestMethod.GET)
    public String projectMember(Model model, ProjectMemberDto projectMemberDto/*, ProjectDto projectDto*/) {
        model.addAttribute("projectMember", new ProjectMemberDto());

        model.addAttribute("projects",projectService.getAllProjects());

        model.addAttribute("users",userService.getAllUser());
        model.addAttribute("projectMembers",projectMemberService.getAllProjectMembers());
//
//        List<ProjectMemberDto> projectMemberDtos = projectMemberService.getAllProjectMember(projectDto.getProjectName());
//        model.addAttribute("projectMembers", projectMemberDtos);
//        Project project=new Project(projectMemberDto.getProject());
//
//        List<UserDto> users = departmentService.getUserByDepartmentName(project.getDepartment().getDepartmentName());
//        model.addAttribute("users", users);
        return "project-member";
    }

    @RequestMapping(value = "/project-member/add", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("projectMember") ProjectMemberDto projectMemberDto,
                           BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("projectMember", projectMemberDto);
            return "/project-member";
        }
        try {
            projectMemberService.addUserToProject(projectMemberDto);

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/project-member?fail";
        }
        redirectAttributes.addAttribute("projectId",projectMemberDto.getProject().getId());
        return "redirect:/project-member?success";
    }

    @RequestMapping(value = "/project-member/remove", method = RequestMethod.DELETE)
    public String removeMember(Model model, ProjectMemberDto projectMemberDto) {
        return "project-member";
    }


}
