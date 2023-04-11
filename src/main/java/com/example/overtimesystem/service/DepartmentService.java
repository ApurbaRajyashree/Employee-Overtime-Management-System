package com.example.overtimesystem.service;

import com.example.overtimesystem.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    DepartmentDto createDepartment(DepartmentDto departmentDto);

    DepartmentDto getDepartmentByName(String name);

    String deleteDepartment(int id);

    List<DepartmentDto> getAllDepartment();
}
