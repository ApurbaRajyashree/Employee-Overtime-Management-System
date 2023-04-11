package com.example.overtimesystem.dto;

import com.example.overtimesystem.entity.Department;
import com.example.overtimesystem.entity.OverTimeMaster;
import com.example.overtimesystem.entity.ProjectMember;
import com.example.overtimesystem.entity.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private int id;

    private String email;

    private String fullName;

    private String mobileNumber;

    private String designation;

    private String password;

    private boolean isActive = true;

    private Department department;

    private List<OverTimeMaster> overTimeMasterList;

    private List<ProjectMember> projectMemberList;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.designation = user.getDesignation();
        this.mobileNumber = user.getMobileNumber();
        this.department = user.getDepartment();
        this.password = user.getPassword();
        this.isActive = user.isActive();
    }
}
