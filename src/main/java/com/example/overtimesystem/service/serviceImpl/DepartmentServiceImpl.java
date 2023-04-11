package com.example.overtimesystem.service.serviceImpl;

import com.example.overtimesystem.dto.DepartmentDto;
import com.example.overtimesystem.entity.Department;
import com.example.overtimesystem.repository.DepartmentRepository;
import com.example.overtimesystem.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        Department department = new Department(departmentDto);
        departmentRepository.save(department);
        return new DepartmentDto(department);
    }

    @Override
    public DepartmentDto getDepartmentByName(String name) {
        Department department = departmentRepository.findByDepartmentName(name);
        if (department != null) {
            return new DepartmentDto(department);
        } else
            throw new RuntimeException("Department does not exist");
    }

    @Override
    public String deleteDepartment(int id) {
        departmentRepository.deleteById(id);
        return "Successfully deleted";
    }

    @Override
    public List<DepartmentDto> getAllDepartment() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(DepartmentDto::new).collect(Collectors.toList());
    }
}
