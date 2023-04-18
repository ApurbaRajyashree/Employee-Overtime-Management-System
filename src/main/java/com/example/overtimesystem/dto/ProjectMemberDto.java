package com.example.overtimesystem.dto;

import com.example.overtimesystem.entity.Project;
import com.example.overtimesystem.entity.ProjectMember;
import com.example.overtimesystem.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMemberDto {
    private int id;
    private boolean isLead;
    private User user;
    private Project project;

    public ProjectMemberDto(ProjectMember projectMember) {
        this.id = projectMember.getId();
        this.user = projectMember.getUser();
        this.project = projectMember.getProject();
        this.isLead = projectMember.isLead();
    }
}
