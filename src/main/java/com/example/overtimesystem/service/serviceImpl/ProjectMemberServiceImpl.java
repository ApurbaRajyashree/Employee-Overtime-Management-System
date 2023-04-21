package com.example.overtimesystem.service.serviceImpl;

import com.example.overtimesystem.dto.ProjectMemberDto;
import com.example.overtimesystem.dto.UserDto;
import com.example.overtimesystem.entity.Project;
import com.example.overtimesystem.entity.ProjectMember;
import com.example.overtimesystem.repository.ProjectMemberRepository;
import com.example.overtimesystem.repository.ProjectRepository;
import com.example.overtimesystem.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;

    private final ProjectRepository projectRepository;

    @Override
    public ProjectMemberDto addUserToProject(ProjectMemberDto projectMemberDto) {
       ProjectMember projectMember=new ProjectMember(projectMemberDto);
       this.projectMemberRepository.save(projectMember);
       return new ProjectMemberDto(projectMember);
    }

    @Override
    public List<ProjectMemberDto> getAllProjectMembers() {
        List<ProjectMember> projectMembers=this.projectMemberRepository.findAll();
        return projectMembers.stream().map(x->new ProjectMemberDto(x)).collect(Collectors.toList());
    }

    @Override
    public List<ProjectMemberDto> getAllProjectMemberByProjectId(int id) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Project doesnot exist!")
        );
        List<ProjectMember> projectMemberList = projectMemberRepository.findAllProjectMemberByProjectId(id);
        return projectMemberList.stream().map(x->new ProjectMemberDto(x)).collect(Collectors.toList());
    }
}
