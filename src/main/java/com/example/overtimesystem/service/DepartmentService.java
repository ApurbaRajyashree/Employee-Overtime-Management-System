package com.example.overtimesystem.service;

import com.example.overtimesystem.dto.DepartmentDto;
import com.example.overtimesystem.dto.UserDto;
import com.example.overtimesystem.entity.Department;

import java.util.List;

public interface DepartmentService {
    DepartmentDto createDepartment(DepartmentDto departmentDto);

    DepartmentDto getDepartmentByName(String name);

    String deleteDepartment(int id);

    List<DepartmentDto> getAllDepartment();

    List<UserDto> getUserByDepartmentName(String departmentName);
}
