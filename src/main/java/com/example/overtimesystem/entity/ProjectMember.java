package com.example.overtimesystem.entity;

import com.example.overtimesystem.dto.ProjectMemberDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_member")
public class ProjectMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "is_lead", nullable = false)
    private boolean isLead=false;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false)
    @JsonBackReference(value = "users")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", referencedColumnName = "id",nullable = false)
    @JsonBackReference(value = "project")
    private Project project;

    public ProjectMember(ProjectMemberDto projectMemberDto) {
        this.id = projectMemberDto.getId();
        this.user = projectMemberDto.getUser();
        this.project = projectMemberDto.getProject();
        this.isLead = projectMemberDto.isLead();
    }
}
