package com.example.overtimesystem.entity;

import com.example.overtimesystem.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_mobile_number", columnNames = {"mobile_number"}),
        @UniqueConstraint(name = "uk_email", columnNames = {"email"})
})
@SQLDelete(sql = "UPDATE User u SET u.isActive=false where u.id=?")
@Where(clause = "is_active=true")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Column(name = "mobile_number", nullable = false, length = 13)
    private String mobileNumber;

    @Column(name = "designation", nullable = false, length = 20)
    private String designation;

    @Column(name = "password", nullable = false, length = 200)
    private String password;

    @Column(name = "is_active")
    private boolean isActive = true;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference(value = "department")
    @JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false)
    private Department department;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference(value = "over_time_master")
    private List<OverTimeMaster> overTimeMasterList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference(value = "project_member")
    private List<ProjectMember> projectMemberList;

    public User(UserDto userDto) {
        this.id = userDto.getId();
        this.email = userDto.getEmail();
        this.fullName = userDto.getFullName();
        this.designation = userDto.getDesignation();
        this.mobileNumber = userDto.getMobileNumber();
        this.department = userDto.getDepartment();
        this.password = userDto.getPassword();
        this.isActive = userDto.isActive();
        this.role = userDto.getRole();
    }
}
