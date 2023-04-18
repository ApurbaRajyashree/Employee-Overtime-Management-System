package com.example.overtimesystem.entity;

import com.example.overtimesystem.dto.OverTimeDetailDto;
import com.example.overtimesystem.dto.OverTimeMasterDto;
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
@Entity
@Table(name = "over_time_master")
public class OverTimeMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private Month month;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference(value = "users")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "overTimeMaster")
    @JsonManagedReference(value = "over_time_detail")
    private List<OverTimeDetail> overTimeDetails;

    public OverTimeMaster(OverTimeMasterDto overTimeMasterDto) {
        this.id = overTimeMasterDto.getId();
        this.month = overTimeMasterDto.getMonth();
        this.user = overTimeMasterDto.getUser();
        List<OverTimeDetail> overTimeDetailList = new ArrayList<>();
        for (OverTimeDetailDto overTimeDetailDto : overTimeMasterDto.getOverTimeDetails()) {
            OverTimeDetail overTimeDetail = new OverTimeDetail(overTimeDetailDto);
            overTimeDetailList.add(overTimeDetail);
        }
        this.overTimeDetails = overTimeDetailList;
    }

}
