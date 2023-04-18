package com.example.overtimesystem.entity;


import com.example.overtimesystem.dto.OverTimeDetailDto;
import com.example.overtimesystem.dto.OverTimeMasterDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

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
    private Date date;

    @Column(name = "logs", nullable = false, length = 100)
    private String logs;

    @Column(name = "start_time", nullable = false)
    private Time startTime;

    @Column(name = "end_time", nullable = false)
    private Time endTime;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference(value = "project")
    private Project project;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "over_time_master_id", referencedColumnName = "id", nullable = false)
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
