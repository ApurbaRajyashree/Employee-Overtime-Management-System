package com.example.overtimesystem.dto;

import com.example.overtimesystem.entity.Project;
import com.example.overtimesystem.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberRequestDto {

    private int id;
    private boolean isLead=false;
    private User[] users;
    private Project project;
}
