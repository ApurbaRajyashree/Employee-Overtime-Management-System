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
            projectMemberRepository.save(projectMember);
            return new ProjectMemberDto(projectMember);
    }
//    @Override
//    public List<ProjectMemberDto> addUserToProject(List<ProjectMemberDto> projectMemberDtos) {
//        List<ProjectMember> projectMemberList=projectMemberDtos.stream().map(x->new ProjectMember(x)).collect(Collectors.toList());
//        List<ProjectMember> savedMember=new ArrayList<>();
//        for (ProjectMember eachMember:projectMemberList){
//            savedMember.add(projectMemberRepository.save(eachMember));
//        }
//        return savedMember.stream().map(x->new ProjectMemberDto(x)).collect(Collectors.toList());
//    }


    @Override
    public List<ProjectMemberDto> getAllProjectMemberByProjectId(int id) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Project doesnot exist!")
        );
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
        projectMemberRepository.delete(projectMember);
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
