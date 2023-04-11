package com.example.overtimesystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email",nullable = false,length = 50,unique = true)
    private String email;

    @Column(name = "full_name",nullable = false,length = 50)
    private String fullName;

    @Column(name = "mobile_number",nullable = false, length = 13, unique = true)
    private String mobileNumber;

    @Column(name = "designation",nullable = false, length = 20)
    private String designation;

    @Column(name = "password",nullable = false, length = 20)
    private String password;

    @ManyToOne(cascade=CascadeType.ALL)
    @JsonBackReference(value = "department")
    @JoinColumn(name = "department_id",referencedColumnName = "id",nullable = false)
    private Department department;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "user")
    @JsonManagedReference(value = "over_time_master")
    private List<OverTimeMaster> overTimeMasterList;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "user")
    @JsonManagedReference(value = "project_member")
    private List<ProjectMember> projectMemberList;


}
