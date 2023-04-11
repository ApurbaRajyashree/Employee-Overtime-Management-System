package com.example.overtimesystem.dto;

import com.example.overtimesystem.entity.Department;
import com.example.overtimesystem.entity.OverTimeMaster;
import com.example.overtimesystem.entity.ProjectMember;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalIdCache;

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

    private Department department;

    private List<OverTimeMaster> overTimeMasterList;

    private List<ProjectMember> projectMemberList;
}
