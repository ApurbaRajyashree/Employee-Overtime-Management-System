package com.example.overtimesystem.service.serviceImpl;

import com.example.overtimesystem.dto.ProjectDto;
import com.example.overtimesystem.entity.Project;
import com.example.overtimesystem.entity.ProjectMember;
import com.example.overtimesystem.repository.DepartmentRepository;
import com.example.overtimesystem.repository.ProjectMemberRepository;
import com.example.overtimesystem.repository.ProjectRepository;
import com.example.overtimesystem.service.ProjectService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private  final ProjectMemberRepository projectMemberRepository;
    private final DepartmentRepository departmentRepository;
    @Override
    public ProjectDto createProject(ProjectDto projectDto) {
        Project project = new Project(projectDto);
        if (project.getDepartment() == null) {
            throw new RuntimeException("Department should be selected");
        }
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
    public ProjectDto getProjectByProjectId(int id) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Project doesnot exist!")
        );
        return new ProjectDto(project);
    }

    @Override
    public List<ProjectDto> getAllProjects() {
        List<Project> projectList = projectRepository.findAll(true);
        return projectList.stream().map(x -> new ProjectDto(x)).collect(Collectors.toList());
    }

    @Override
    public String deleteProject(int id) {
        List<ProjectMember> projectMembers=projectMemberRepository.findAllByProjectId(id);
        for (ProjectMember eachMember: projectMembers){
            eachMember.setUser(null);
            eachMember.setProject(null);
            eachMember.setLead(false);
            projectMemberRepository.delete(eachMember);
        }
        projectRepository.deleteById(id);
        return "Project deleted successfully";
    }

    @Override
    public List<ProjectDto> assignedProject() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Project> assignedProjectList=new ArrayList<>();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            List<Project> allProjects=projectRepository.findAll(true);
            for (Project eachProject:allProjects){
                List<ProjectMember> projectMembers=eachProject.getProjectMembers();
                for (ProjectMember eachProjectMember:projectMembers){
                    if(eachProjectMember.getUser().getEmail().equals(userDetails.getUsername())){
                        assignedProjectList.add(eachProject);
                    }
                }
            }
        }
        return assignedProjectList.stream().map(x-> new ProjectDto(x)).collect(Collectors.toList());
    }
}
