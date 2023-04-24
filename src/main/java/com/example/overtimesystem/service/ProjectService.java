package com.example.overtimesystem.service;

import com.example.overtimesystem.dto.ProjectDto;

import java.util.List;

public interface ProjectService {

    ProjectDto createProject(ProjectDto projectDto);

    ProjectDto updateProject(int id, ProjectDto projectDto);

    ProjectDto getProjectByProjectId(int id);

    List<ProjectDto> getAllProjects();

    String deleteProject(int id);

    List<ProjectDto> assignedProject();
}
