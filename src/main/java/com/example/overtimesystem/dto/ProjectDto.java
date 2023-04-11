package com.example.overtimesystem.dto;

import com.example.overtimesystem.entity.Department;
import com.example.overtimesystem.entity.OverTimeDetail;
import com.example.overtimesystem.entity.Project;
import com.example.overtimesystem.entity.ProjectMember;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    private int id;
    private String projectName;
    private String projectCode;
    private Date estimatedDueDate;
    private boolean isActive = true;
    private List<OverTimeDetail> overTimeDetailList;
    private Department department;
    private List<ProjectMember> projectMembers;

    public ProjectDto(Project project) {
        this.id = project.getId();
        this.department = project.getDepartment();
        this.projectCode = project.getProjectCode();
        this.projectName = project.getProjectName();
        this.estimatedDueDate = project.getEstimatedDueDate();
        this.isActive = project.isActive();
    }
}
