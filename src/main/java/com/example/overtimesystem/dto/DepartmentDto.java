package com.example.overtimesystem.dto;

import com.example.overtimesystem.entity.Project;
import com.example.overtimesystem.entity.User;
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
public class DepartmentDto {
    private int id;
    private String departmentName;
    private List<Project> projectList;
    private List<User> users;
}
