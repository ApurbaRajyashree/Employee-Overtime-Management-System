package com.example.overtimesystem.controller;

import com.example.overtimesystem.entity.OverTimeMaster;
import com.example.overtimesystem.helper.ExportOverTimeDetail;
import com.example.overtimesystem.repository.OverTimeDetailRepository;
import com.example.overtimesystem.repository.OverTimeMasterRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class ExportOverTimeController {

    private final OverTimeMasterRepository overTimeMasterRepository;

    private final OverTimeDetailRepository overTimeDetailRepository;

    @GetMapping("/master/export-detail/{detail_id}")
    public String exportDetailReport(@PathVariable("detail_id") int id, Model model, Principal principal,
                                     RedirectAttributes redirectAttributes, HttpServletResponse response) throws IOException {
        OverTimeMaster overTimeMaster=overTimeMasterRepository.findById(id).orElseThrow(
                ()->new RuntimeException("OverTime Master does not exist!")
        );
        try {
            ExportOverTimeDetail exportOverTimeDetail1=new ExportOverTimeDetail(overTimeMaster.getOverTimeDetails(), overTimeDetailRepository, overTimeMaster);

            exportOverTimeDetail1.OtdSheet();

        }catch (Exception e){
            redirectAttributes.addFlashAttribute("error",e.getMessage());
            return "redirect:/master?fail";
        }
        redirectAttributes.addFlashAttribute("msg","Excel sheet exported.");
        return "redirect:/master?success";
    }
}
