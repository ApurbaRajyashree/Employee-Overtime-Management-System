package com.example.overtimesystem.service.serviceImpl;

import com.example.overtimesystem.dto.ProjectMemberDto;
import com.example.overtimesystem.dto.ProjectMemberRequestDto;
import com.example.overtimesystem.entity.Project;
import com.example.overtimesystem.entity.ProjectMember;
import com.example.overtimesystem.entity.User;
import com.example.overtimesystem.repository.ProjectMemberRepository;
import com.example.overtimesystem.repository.ProjectRepository;
import com.example.overtimesystem.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
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
    public List<ProjectMemberDto> addUserToProject(ProjectMemberRequestDto projectMemberDto) {
        List<User> userList=projectMemberDto.getUsers();
        List<ProjectMember> projectMembers=new ArrayList<>();
        for (User eachUser:userList){
            ProjectMember projectMember=new ProjectMember();
            projectMember.setLead(false);
            projectMember.setUser(eachUser);
            projectMember.setProject(projectMemberDto.getProject());
            projectMembers.add(projectMember);
        }
        projectMemberRepository.saveAll(projectMembers);
        return projectMembers.stream().map(x->new ProjectMemberDto(x)).collect(Collectors.toList());
    }

    @Override
    public List<ProjectMemberDto> getAllProjectMemberByProjectId(int id) {

        List<ProjectMember> projectMemberList = projectMemberRepository.findAllProjectMemberByProjectId(id);
        return projectMemberList.stream().map(x -> new ProjectMemberDto(x)).collect(Collectors.toList());
    }

    @Override
    public String removeProjectMember(int id) {
        ProjectMember projectMember=projectMemberRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Project Member does not exist")
        );
        Project project=projectRepository.findById(projectMember.getProject().getId()).orElseThrow(
                ()->new RuntimeException("Project does not exist")
        );
        List<ProjectMember> projectMembers=project.getProjectMembers();
        projectMembers.remove(projectMember);
        projectMember.setProject(null);
        projectMember.setUser(null);
        projectMemberRepository.deleteById(id);
        return "Deleted successfully";

    }

    @Override
    public String assignLead(int id) {
        ProjectMember projectMember=projectMemberRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Project Member does not exist")
        );
        int projectId=projectMember.getProject().getId();
        List<ProjectMember> projectMemberList=projectMemberRepository.findAllByProjectId(projectId);
        for (ProjectMember eachProjectMember:projectMemberList){
            eachProjectMember.setLead(false);
        }
        projectMemberRepository.saveAll(projectMemberList);
        projectMember.setLead(true);
        projectMemberRepository.save(projectMember);
        return projectMember.getUser().getFullName()+" assigned Lead";
    }


    @Override
    public ProjectMember Lead(int id) {
        List<ProjectMember> projectMembers=projectMemberRepository.findAllByProjectId(id);
        for (ProjectMember eachMember:projectMembers){
            if(eachMember.isLead()){
                return eachMember;
            }
        }
        return null;
    }


}
