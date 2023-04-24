package com.example.overtimesystem.controller;


import com.example.overtimesystem.dto.ProjectMemberDto;
import com.example.overtimesystem.entity.Project;
import com.example.overtimesystem.entity.ProjectMember;
import com.example.overtimesystem.repository.ProjectMemberRepository;
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

@Controller
@RequiredArgsConstructor
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    private final ProjectMemberRepository projectMemberRepository;


    @RequestMapping(value = "/project/project-member", method = RequestMethod.GET)
    public String projectMember(Model model, ProjectMemberDto projectMemberDto) {
        model.addAttribute("projectMember", new ProjectMemberDto());
        model.addAttribute("projectMembers", projectMemberService.getAllProjectMembers());

        return "/project/project-member";
    }

    @RequestMapping(value = "/assign-member/add", method = RequestMethod.POST)
    public String processAssignMember(@ModelAttribute("projectMember") ProjectMemberDto projectMemberDto,
                                      BindingResult result, Model model,
                                      RedirectAttributes redirectAttributes){
        if (result.hasErrors()) {
            model.addAttribute("projectMember", projectMemberDto);
            return "redirect:/project/assign-member";
        }

        try {
            ProjectMember projectMember=projectMemberRepository.findByProjectAndUser(projectMemberDto.getProject().getId(),
                    projectMemberDto.getUser().getId());
            if(projectMember==null){
                projectMemberService.addUserToProject(projectMemberDto);
            }else {
                throw new RuntimeException("User already exist in the Project");
            }
        }catch (RuntimeException e){
            redirectAttributes.addFlashAttribute("error",e.getMessage());
            return "redirect:/project/assign-member/"+projectMemberDto.getProject().getId()+"?fail";
        }
        return "redirect:/project/assign-member/"+projectMemberDto.getProject().getId()+"?success";
    }


}
