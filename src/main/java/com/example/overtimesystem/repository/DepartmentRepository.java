package com.example.overtimesystem.repository;

import com.example.overtimesystem.dto.UserDto;
import com.example.overtimesystem.entity.Department;
import com.example.overtimesystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Department findByDepartmentName(String name);

    @Query(value = "select full_name from users,department where users.department_id=department.id AND\n" +
            "                                             department_id=?1 ",nativeQuery = true)
    List<User> findAllUserByDepartmentId(int id);
}
