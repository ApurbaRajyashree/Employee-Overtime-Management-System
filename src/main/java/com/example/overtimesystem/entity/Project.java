package com.example.overtimesystem.entity;

import com.example.overtimesystem.dto.ProjectDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project")
@SQLDelete(sql = "UPDATE Project p SET p.isActive=false where p.id=?")
@Where(clause = "is_active=true")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(name = "project_code", nullable = false, length = 10)
    private String projectCode;

    @Column(name = "estimated_due_date", nullable = false, length = 10)
    private Date estimatedDueDate;

    @Column(name = "is_active")
    private boolean isActive = true;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "project")
    @JsonManagedReference(value = "over_time_detail")
    private List<OverTimeDetail> overTimeDetailList;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference(value = "department")
    private Department department;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "project")
    @JsonManagedReference(value = "project_member")
    private List<ProjectMember> projectMembers;

    public Project(ProjectDto projectDto) {
        this.id = projectDto.getId();
        this.department = projectDto.getDepartment();
        this.projectCode = projectDto.getProjectCode();
        this.projectName = projectDto.getProjectName();
        this.estimatedDueDate = projectDto.getEstimatedDueDate();
        this.isActive = projectDto.isActive();

    }
}
