package com.example.overtimesystem.dto;

import com.example.overtimesystem.entity.Month;
import com.example.overtimesystem.entity.OverTimeDetail;
import com.example.overtimesystem.entity.OverTimeMaster;
import com.example.overtimesystem.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OverTimeMasterDto {
    private int id;
    private Month month;
    private User user;
    private List<OverTimeDetailDto> overTimeDetails;

    public OverTimeMasterDto(OverTimeMaster overTimeMaster) {
        this.id = overTimeMaster.getId();
        this.month = overTimeMaster.getMonth();
        this.user = overTimeMaster.getUser();
        List<OverTimeDetailDto> overTimeDetailDtoList = new ArrayList<>();
        for (OverTimeDetail overTimeDetail : overTimeMaster.getOverTimeDetails()) {
            OverTimeDetailDto overTimeDetailDto = new OverTimeDetailDto(overTimeDetail);
            overTimeDetailDtoList.add(overTimeDetailDto);
        }
        this.overTimeDetails = overTimeDetailDtoList;
    }
}
