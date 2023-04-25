package com.example.overtimesystem.controller;


import com.example.overtimesystem.dto.ProjectDto;
import com.example.overtimesystem.dto.ProjectMemberDto;
import com.example.overtimesystem.entity.Project;
import com.example.overtimesystem.entity.ProjectMember;
import com.example.overtimesystem.repository.ProjectMemberRepository;
import com.example.overtimesystem.repository.ProjectRepository;
import com.example.overtimesystem.service.DepartmentService;
import com.example.overtimesystem.service.ProjectMemberService;
import com.example.overtimesystem.service.ProjectService;
import com.example.overtimesystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    private final ProjectMemberRepository projectMemberRepository;

    private  final ProjectRepository projectRepository;


    @RequestMapping(value = "/project/project-member/{id}", method = RequestMethod.GET)
    public String projectMember(@PathVariable("id") int id,Model model, RedirectAttributes redirectAttributes) {
        Optional<Project> project=projectRepository.findById(id);
        if(project.isPresent()){
            Project presentProject=project.get();
            model.addAttribute("project",presentProject);
            model.addAttribute("projectMembers", projectMemberService.getAllProjectMemberByProjectId(id));
            return "/project/project-member";
        }
        redirectAttributes.addFlashAttribute("error","Something went wrong");
        return "redirect:/project/project-member?fail";
    }

    @RequestMapping(value = "/project/assign-member/add", method = RequestMethod.POST)
    public String processAssignMember(@ModelAttribute("projectMember") ProjectMemberDto projectMemberDto,
                                     BindingResult result, Model model,
                                      RedirectAttributes redirectAttributes){
        if (result.hasErrors()) {
            model.addAttribute("projectMember", projectMemberDto);
            return "/project/assign-member";
        }

        try {
                projectMemberService.addUserToProject(projectMemberDto);
        }catch (RuntimeException e){
            redirectAttributes.addFlashAttribute("error",e.getMessage());
            return "redirect:/project/assign-member/"+projectMemberDto.getProject().getId()+"?fail";
        }
        return "redirect:/project/assign-member/"+projectMemberDto.getProject().getId()+"?success";
    }


}
