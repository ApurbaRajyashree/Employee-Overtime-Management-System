package com.example.overtimesystem.controller;

import com.example.overtimesystem.dto.OverTimeDetailDto;
import com.example.overtimesystem.dto.ProjectDto;
import com.example.overtimesystem.entity.OverTimeDetail;
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

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OverTimeDetailController {

    private final OverTimeDetailService overTimeDetailService;
    private final ProjectService projectService;

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String project(Model model, OverTimeDetailDto overTimeDetailDto) {
        model.addAttribute("overTimeDetail", new OverTimeDetail());
        model.addAttribute("projects", projectService.getAllProjects());
        List<OverTimeDetailDto> overTimeDetailDtos = overTimeDetailService.getAll();
        model.addAttribute("overTimeDetails", overTimeDetailDtos);
        return "dashboard";
    }

    @RequestMapping(value = "/over-time-detail/add",method = RequestMethod.POST)
    public String createProject(@Valid @ModelAttribute("overTimeDetail") OverTimeDetailDto overTimeDetailDto,
                                BindingResult result, Model model,
                                RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("overTimeDetail", overTimeDetailDto);
            return "/dashboard";
        }
        try {
            overTimeDetailService.createOverTimeDetail(overTimeDetailDto);

        }catch (RuntimeException e){
            redirectAttributes.addFlashAttribute("error",e.getMessage());
            return "redirect:/dashboard?fail";
        }
        return "redirect:/dashboard?success";
    }
}
