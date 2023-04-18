package com.example.overtimesystem.service;

import com.example.overtimesystem.dto.ProjectMemberDto;
import com.example.overtimesystem.dto.UserDto;
import com.example.overtimesystem.entity.ProjectMember;

import java.util.List;

public interface ProjectMemberService {
    ProjectMemberDto addUserToProject(ProjectMemberDto projectMemberDto);
}
