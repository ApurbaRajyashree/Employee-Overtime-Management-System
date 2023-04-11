package com.example.overtimesystem.service.serviceImpl;

import com.example.overtimesystem.dto.ProjectDto;
import com.example.overtimesystem.entity.Project;
import com.example.overtimesystem.repository.ProjectRepository;
import com.example.overtimesystem.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public ProjectDto createProject(ProjectDto projectDto) {
        Project project = new Project(projectDto);
        projectRepository.save(project);
        return new ProjectDto(project);
    }

    @Override
    public ProjectDto updateProject(int id, ProjectDto projectDto) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new RuntimeException("invalid id!!")
        );
        project.setProjectName(projectDto.getProjectName());
        project.setProjectCode(projectDto.getProjectCode());
        project.setEstimatedDueDate(projectDto.getEstimatedDueDate());
        Project updatedProject = projectRepository.save(project);
        return new ProjectDto(updatedProject);
    }

    @Override
    public ProjectDto getProjectByProjectCode(String code) {
        Project project = projectRepository.findByProjectCode(code);
        if (project != null) {
            return new ProjectDto(project);
        } else {
            throw new RuntimeException("Invalid code");
        }
    }

    @Override
    public List<ProjectDto> getAllProjects() {
        List<Project> projectList = projectRepository.findAll();
        return projectList.stream().map(x -> new ProjectDto(x)).collect(Collectors.toList());
    }

    @Override
    public String deleteProject(int id) {
        projectRepository.deleteById(id);
        return "Project deleted successfully";
    }
}
