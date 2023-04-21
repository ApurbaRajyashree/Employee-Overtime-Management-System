package com.example.overtimesystem.dto;

import com.example.overtimesystem.entity.Department;
import com.example.overtimesystem.entity.Project;
import com.example.overtimesystem.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Department Name can not be empty")
    private String departmentName;

    private boolean isActive=true;
    private List<Project> projectList;
    private List<User> users;

    public DepartmentDto(Department department) {
        this.id = department.getId();
        this.departmentName = department.getDepartmentName();
        this.isActive = department.isActive();
    }
}
