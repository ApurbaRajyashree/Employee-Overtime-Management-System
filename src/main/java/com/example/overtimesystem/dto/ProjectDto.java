package com.example.overtimesystem.dto;

import com.example.overtimesystem.entity.Department;
import com.example.overtimesystem.entity.Project;
import com.example.overtimesystem.entity.ProjectMember;
import com.example.overtimesystem.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
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
    }


    public boolean isLead() {
        List<ProjectMemberDto> projectMembers=this.getProjectMembers();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            for (ProjectMemberDto eachMember:projectMembers){
                if(eachMember.isLead()){
                    if(eachMember.getUser().getEmail().equals(userDetails.getUsername()))
                        return true;
                }
            }
        }
        return false;
    }
}
