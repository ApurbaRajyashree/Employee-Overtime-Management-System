package com.example.overtimesystem.dto;

import com.example.overtimesystem.entity.OverTimeDetail;
import com.example.overtimesystem.entity.OverTimeMaster;
import com.example.overtimesystem.entity.Project;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OverTimeDetailDto {
    private int id;
    private LocalDate date;
    private String logs;
    private LocalTime startTime;
    private LocalTime endTime;
    private Project project;
    private OverTimeMaster overTimeMaster;

    public OverTimeDetailDto (OverTimeDetail overTimeDetail){
        this.id=overTimeDetail.getId();
        this.project=overTimeDetail.getProject();
        this.logs=overTimeDetail.getLogs();
        this.date=overTimeDetail.getDate();
        this.startTime=overTimeDetail.getStartTime();
        this.endTime=overTimeDetail.getEndTime();
        this.overTimeMaster=overTimeDetail.getOverTimeMaster();
    }
}
