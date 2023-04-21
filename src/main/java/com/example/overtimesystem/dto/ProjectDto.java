package com.example.overtimesystem.dto;

import com.example.overtimesystem.entity.Department;
import com.example.overtimesystem.entity.Project;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Project Name cannot be blank")
    private String projectName;
    private String projectCode;
    private Date estimatedDueDate;
    private boolean isActive = true;
    private List<OverTimeDetailDto> overTimeDetailList;
    private Department department;
    private List<ProjectMemberDto> projectMembers;

    public ProjectDto(Project project) {
        this.id = project.getId();
        this.department = project.getDepartment();
        this.projectCode = project.getProjectCode();
        this.projectName = project.getProjectName();
        this.estimatedDueDate = project.getEstimatedDueDate();
        this.isActive = project.isActive();
//        List<ProjectMemberDto> projectMemberDtoList=new ArrayList<>();
//        for (ProjectMember projectMember:project.getProjectMembers()){
//            ProjectMemberDto projectMemberDto=new ProjectMemberDto(projectMember);
//            projectMemberDtoList.add(projectMemberDto);
//        }
//        this.projectMembers=projectMemberDtoList;
//
//        List<OverTimeDetailDto> overTimeDetailDtos=new ArrayList<>();
//        for (OverTimeDetail overTimeDetail:project.getOverTimeDetailList()){
//            OverTimeDetailDto overTimeDetailDto=new OverTimeDetailDto(overTimeDetail);
//            overTimeDetailDtos.add(overTimeDetailDto);
//        }
//        this.overTimeDetailList=overTimeDetailDtos;
    }
}
