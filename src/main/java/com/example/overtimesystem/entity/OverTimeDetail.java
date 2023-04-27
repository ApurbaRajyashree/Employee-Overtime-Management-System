package com.example.overtimesystem.entity;


import com.example.overtimesystem.dto.OverTimeDetailDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "over_time_detail")
public class OverTimeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    @Column(name = "logs", nullable = false, length = 100)
    private String logs;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference(value = "project")
    private Project project;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "over_time_master_id", referencedColumnName = "id",nullable = false)
    @JsonBackReference(value = "over_time_master")
    private OverTimeMaster overTimeMaster;

    public OverTimeDetail (OverTimeDetailDto overTimeDetailDto){
        this.id=overTimeDetailDto.getId();
        this.project=overTimeDetailDto.getProject();
        this.logs=overTimeDetailDto.getLogs();
        this.date=overTimeDetailDto.getDate();
        this.startTime=overTimeDetailDto.getStartTime();
        this.endTime=overTimeDetailDto.getEndTime();
        this.overTimeMaster=overTimeDetailDto.getOverTimeMaster();
    }
}
