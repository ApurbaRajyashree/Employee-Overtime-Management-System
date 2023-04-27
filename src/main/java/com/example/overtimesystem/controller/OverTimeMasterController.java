package com.example.overtimesystem.controller;

import com.example.overtimesystem.dto.OverTimeDetailDto;
import com.example.overtimesystem.dto.OverTimeMasterDto;
import com.example.overtimesystem.entity.OverTimeDetail;
import com.example.overtimesystem.entity.OverTimeMaster;
import com.example.overtimesystem.repository.OverTimeMasterRepository;
import com.example.overtimesystem.repository.UserRepository;
import com.example.overtimesystem.service.OverTimeMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class OverTimeMasterController {

    private final OverTimeMasterService overTimeMasterService;
    private final UserRepository userRepository;

    private final OverTimeMasterRepository overTimeMasterRepository;


    @RequestMapping(value = "/master" , method = RequestMethod.GET)
    public String master(Model model, OverTimeMasterDto overTimeMasterDto, Principal principal) {
        model.addAttribute("overTimeMaster", overTimeMasterDto);
        List<OverTimeMasterDto> overTimeDetailDtoList =overTimeMasterService.getAllOverTimeMasterofLogedInUser();
        model.addAttribute("overTimeMasterList", overTimeDetailDtoList);
        model.addAttribute("user",userRepository.findByEmail(principal.getName()));

        return "master";
    }

    @RequestMapping(value = "/master/view-detail/{id}", method = RequestMethod.GET)
    public String viewOverTimeDetail(@PathVariable("id") int id, Model model,Principal principal,
                                   RedirectAttributes redirectAttributes) {
        Optional<OverTimeMaster> overTimeMaster=overTimeMasterRepository.findById(id);
        if(overTimeMaster.isPresent()){
            OverTimeMaster presentMaster=overTimeMaster.get();
            model.addAttribute("overTimeMaster",presentMaster);
            List<OverTimeDetail> overTimeDetailList=presentMaster.getOverTimeDetails();
            List<OverTimeDetailDto> overTimeDetailDtoList=overTimeDetailList.stream().map(x->new OverTimeDetailDto(x)).collect(Collectors.toList());
            List<OverTimeDetailDto> usersOverTimeDetail=overTimeMasterService.getAllOverTimeDetailofLogedInUser(
                    overTimeDetailDtoList
            );
            model.addAttribute("overTimeDetails",presentMaster.getOverTimeDetails());
            model.addAttribute("user",userRepository.findByEmail(principal.getName()));

            return "/over-time-detail";
        }
        redirectAttributes.addFlashAttribute("error","Something went wrong");
        model.addAttribute("user",userRepository.findByEmail(principal.getName()));
        return "redirect:/over-time-detail?fail";
    }




}
