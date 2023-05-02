package com.example.overtimesystem.controller;


import com.example.overtimesystem.dto.ProjectMemberDto;
import com.example.overtimesystem.dto.ProjectMemberRequestDto;
import com.example.overtimesystem.entity.*;
import com.example.overtimesystem.helper.LoggedInUser;
import com.example.overtimesystem.repository.OverTimeMasterRepository;
import com.example.overtimesystem.repository.ProjectMemberRepository;
import com.example.overtimesystem.repository.ProjectRepository;
import com.example.overtimesystem.repository.UserRepository;
import com.example.overtimesystem.service.OverTimeDetailService;
import com.example.overtimesystem.service.ProjectMemberService;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProjectMemberController {

    private final UserRepository userRepository;
    private final ProjectMemberService projectMemberService;

    private final ProjectMemberRepository projectMemberRepository;

    private final ProjectRepository projectRepository;

    private  final OverTimeMasterRepository overTimeMasterRepository;

    private final LoggedInUser loggedInUser;

    private final OverTimeDetailService overTimeDetailService;

    @RequestMapping(value = "/project/project-member/{id}", method = RequestMethod.GET)
    public String projectMember(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes
            , Principal principal) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            Project presentProject = project.get();
            model.addAttribute("project", presentProject);
            model.addAttribute("lead",projectMemberService.Lead(id));
            model.addAttribute("projectMembers", projectMemberService.getAllProjectMemberByProjectId(id));
            model.addAttribute("user", userRepository.findByEmail(principal.getName()));

            return "/project/project-member";
        }
        redirectAttributes.addFlashAttribute("error", "Something went wrong");
        model.addAttribute("user", userRepository.findByEmail(principal.getName()));

        return "redirect:/project/project-member?fail";
    }

    @RequestMapping(value = "/project/assign-member/add", method = RequestMethod.POST)
    public String processAssignMember(@ModelAttribute("projectMember") ProjectMemberRequestDto projectMemberDto,
                                      BindingResult result, Model model, Principal principal,
                                      RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("projectMembers", projectMemberDto);
            model.addAttribute("user", userRepository.findByEmail(principal.getName()));

            return "/project/assign-member";
        }

        try {
            List<User> userList=projectMemberDto.getUsers();
            for(User eachUser:userList){
                ProjectMember testProjectMember = projectMemberRepository.findByProjectAndUser(projectMemberDto.getProject().getId(),
                        eachUser.getId());
                if (testProjectMember != null) {
                    throw new RuntimeException(eachUser.getFullName() + " already assigned to project " +
                            projectMemberDto.getProject().getProjectName());

                }
            }
            projectMemberService.addUserToProject(projectMemberDto);

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            model.addAttribute("user", userRepository.findByEmail(principal.getName()));

            return "redirect:/project/assign-member/" + projectMemberDto.getProject().getId() + "?fail";
        }
        model.addAttribute("user", userRepository.findByEmail(principal.getName()));
        return "redirect:/project/assign-member/" + projectMemberDto.getProject().getId() + "?success";
    }


    @RequestMapping(value = "/project/project-member/{project_id}/remove-member/{id}", method = RequestMethod.GET)
    public String deleteProject(@ModelAttribute("projectMember") ProjectMemberDto projectMemberDtoDto,
                                @PathVariable("project_id") int projectId, Model model, Principal principal,
                                @PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        String msg = projectMemberService.removeProjectMember(id);
        redirectAttributes.addFlashAttribute("msg", msg);
        model.addAttribute("user", userRepository.findByEmail(principal.getName()));
        return "redirect:/project/project-member/" + projectId + "?success";
    }


    @RequestMapping(value = "/project/project-member/{project_id}/is-lead/{id}", method = RequestMethod.GET)
    public String assignLead(@ModelAttribute("projectMember") ProjectMemberDto projectMemberDtoDto,
                             @PathVariable("project_id") int projectId, Principal principal, Model model,
                             @PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        String msg = projectMemberService.assignLead(id);
        redirectAttributes.addFlashAttribute("msg", msg);
        model.addAttribute("user", userRepository.findByEmail(principal.getName()));

        return "redirect:/project/project-member/" + projectId + "?success";
    }


    @RequestMapping(value = "/project/project-member/{project_id}/view-otd/{id}", method = RequestMethod.GET)
    public String memberOTD(@ModelAttribute("projectMember") ProjectMemberDto projectMemberDtoDto,
                             @PathVariable("project_id") int projectId, Principal principal, Model model,
                             @PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        ProjectMember projectMember=projectMemberRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("ProjectMember doesnot exist")
        );
        try {

            OverTimeMaster overTimeMaster = overTimeMasterRepository.findByUserYearAndMonth(LocalDate.now().getYear(),
                    Month.valueOfMonthNumber(LocalDate.now().getMonthValue()).toString(), projectMember.getUser().getId());
            if (overTimeMaster == null) {
                redirectAttributes.addFlashAttribute("error", projectMember.getUser().getFullName() + "'s " +
                        "OverTime does not exist");
                return "redirect:/project/project-member/" + projectId + "?fail";

            }
            model.addAttribute("master", overTimeMaster);
            model.addAttribute("details", overTimeDetailService.getAllByOverTimeMaster(overTimeMaster.getId()));
            model.addAttribute("user", loggedInUser.getCurrentUser());

            return "/project/member-otd";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/project/project-member/" + projectId + "?fail";
        }

    }


}
