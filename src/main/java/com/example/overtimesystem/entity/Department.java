package com.example.overtimesystem.entity;

import com.example.overtimesystem.dto.DepartmentDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "department")
@SQLDelete(sql = "UPDATE Department d SET d.isActive=false where d.id=?")
@Where(clause = "is_active=true")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "department_name", nullable = false, length = 20,unique = true)
    private String departmentName;

    @Column(name = "is_active")
    private boolean isActive = true;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "department")
    @JsonManagedReference(value = "project")
    private List<Project> projectList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "department")
    @JsonManagedReference(value = "users")
    private List<User> users;

    public Department(DepartmentDto departmentDto) {
        this.id = departmentDto.getId();
        this.departmentName = departmentDto.getDepartmentName();
        this.isActive = departmentDto.isActive();
    }
}
