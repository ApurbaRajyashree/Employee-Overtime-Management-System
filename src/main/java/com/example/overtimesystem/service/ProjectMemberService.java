package com.example.overtimesystem.service;

import com.example.overtimesystem.dto.ProjectMemberDto;

import java.util.List;

public interface ProjectMemberService {
    ProjectMemberDto addUserToProject(ProjectMemberDto projectMemberDto);

    List<ProjectMemberDto> getAllProjectMemberByProjectId(int id);

//    List<ProjectMemberDto> addUserToProject(List<ProjectMemberDto> projectMemberDtos);

    String removeProjectMember(int id);


}


