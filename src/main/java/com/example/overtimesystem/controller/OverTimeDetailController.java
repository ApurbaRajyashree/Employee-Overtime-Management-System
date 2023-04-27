package com.example.overtimesystem.controller;

import com.example.overtimesystem.dto.OverTimeDetailDto;
import com.example.overtimesystem.entity.OverTimeDetail;
import com.example.overtimesystem.repository.UserRepository;
import com.example.overtimesystem.service.OverTimeDetailService;
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

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OverTimeDetailController {

    private final OverTimeDetailService overTimeDetailService;
    private final ProjectService projectService;

    private final UserRepository userRepository;

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String overTimeDetail(Model model, OverTimeDetailDto overTimeDetailDto,Principal principal) {
        model.addAttribute("overTimeDetail", new OverTimeDetail());
        model.addAttribute("projects", projectService.assignedProject());
        List<OverTimeDetailDto> overTimeDetailDtos = overTimeDetailService.getAll();
        model.addAttribute("overTimeDetails", overTimeDetailDtos);
        model.addAttribute("user",userRepository.findByEmail(principal.getName()));
        return "dashboard";
    }

    @RequestMapping(value = "/over-time-detail/add",method = RequestMethod.POST)
    public String createOverTimeDetail(@Valid @ModelAttribute("overTimeDetail") OverTimeDetailDto overTimeDetailDto,
                                BindingResult result, Model model,
                                RedirectAttributes redirectAttributes, Principal principal) {

        if (result.hasErrors()) {
            model.addAttribute("overTimeDetail", overTimeDetailDto);
            model.addAttribute("projects", projectService.getAllProjects());
            model.addAttribute("user",userRepository.findByEmail(principal.getName()));

            return "/dashboard";
        }
        try {
            overTimeDetailService.createOverTimeDetail(overTimeDetailDto,principal.getName());

        }catch (RuntimeException e){
            redirectAttributes.addFlashAttribute("error",e.getMessage());
            model.addAttribute("user",userRepository.findByEmail(principal.getName()));

            return "redirect:/dashboard?fail";
        }
        model.addAttribute("user",userRepository.findByEmail(principal.getName()));
        return "redirect:/dashboard?success";
    }


}
