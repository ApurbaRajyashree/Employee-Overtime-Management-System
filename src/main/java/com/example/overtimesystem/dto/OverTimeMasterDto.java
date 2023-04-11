package com.example.overtimesystem.dto;

import com.example.overtimesystem.entity.Month;
import com.example.overtimesystem.entity.OverTimeDetail;
import com.example.overtimesystem.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OverTimeMasterDto {
    private int id;
    private Month month;
    private User user;
    private List<OverTimeDetail> overTimeDetails;
}
