package com.example.overtimesystem.service;

import com.example.overtimesystem.dto.ProjectMemberDto;
import com.example.overtimesystem.entity.ProjectMember;

import java.util.List;

public interface ProjectMemberService {
    ProjectMemberDto addUserToProject(ProjectMemberDto projectMemberDto);

    List<ProjectMemberDto> getAllProjectMemberByProjectId(int id);


    String removeProjectMember(int id);

    String assignLead(int id);

    ProjectMember Lead(int id);

}


