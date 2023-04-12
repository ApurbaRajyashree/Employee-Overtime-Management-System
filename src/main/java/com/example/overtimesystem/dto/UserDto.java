package com.example.overtimesystem.dto;

import com.example.overtimesystem.entity.*;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    @NotEmpty
    private String fullName;

    @NotEmpty(message = "Mobile Number should not be empty")
    private String mobileNumber;

    @NotEmpty(message = "Designation should not be empty")
    private String designation;

    @NotEmpty
    private String password;

    private Role role;

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
        this.role = user.getRole();
    }
}
