package com.example.overtimesystem.controller;

import com.example.overtimesystem.dto.DepartmentDto;
import com.example.overtimesystem.dto.OverTimeDetailDto;
import com.example.overtimesystem.dto.OverTimeMasterDto;
import com.example.overtimesystem.dto.ProjectDto;
import com.example.overtimesystem.entity.OverTimeMaster;
import com.example.overtimesystem.entity.Project;
import com.example.overtimesystem.entity.ProjectMember;
import com.example.overtimesystem.repository.OverTimeMasterRepository;
import com.example.overtimesystem.service.OverTimeMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OverTimeMasterController {

    private final OverTimeMasterService overTimeMasterService;

    private final OverTimeMasterRepository overTimeMasterRepository;


    @RequestMapping(value = "/master" , method = RequestMethod.GET)
    public String master(Model model, OverTimeMasterDto overTimeMasterDto) {
        model.addAttribute("overTimeMaster", overTimeMasterDto);
        List<OverTimeMasterDto> overTimeDetailDtoList =overTimeMasterService.getAllOverTimeMasterofLogedInUser();
        model.addAttribute("overTimeMasterList", overTimeDetailDtoList);
        return "master";
    }

    @RequestMapping(value = "/master/view-detail/{id}", method = RequestMethod.GET)
    public String viewOverTimeDetail(@PathVariable("id") int id, Model model,
                                   RedirectAttributes redirectAttributes) {
        Optional<OverTimeMaster> overTimeMaster=overTimeMasterRepository.findById(id);
        if(overTimeMaster.isPresent()){
            OverTimeMaster presentMaster=overTimeMaster.get();
            model.addAttribute("overTimeDetail",presentMaster.getOverTimeDetails());

            return "/over-time-detail";
        }
        redirectAttributes.addFlashAttribute("error","Something went wrong");
        return "redirect:/over-time-detail?fail";
    }
}
