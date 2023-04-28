package com.example.overtimesystem.controller;

import com.example.overtimesystem.entity.OverTimeDetail;
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

    @GetMapping("/master/export-detail/{detail_id}")
    public String exportDetailReport(@PathVariable("detail_id") int id, Model model, Principal principal,
                                     RedirectAttributes redirectAttributes, HttpServletResponse response) throws IOException {
        OverTimeMaster overTimeMaster=overTimeMasterRepository.findById(id).orElseThrow(
                ()->new RuntimeException("OverTime Master does not exist!")
        );
        ExportOverTimeDetail exportOverTimeDetail1=new ExportOverTimeDetail(overTimeMaster.getOverTimeDetails(),overTimeMaster);

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        exportOverTimeDetail1.OtdSheet();
        redirectAttributes.addFlashAttribute("msg","Excel sheet exported.");
        return "redirect:/master?success";
    }
}
