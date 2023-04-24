package com.example.overtimesystem.repository;

import com.example.overtimesystem.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Integer> {

    @Query(value = "select u.full_name\n" +
            "from project_member\n" +
            "         inner join users u on project_member.user_id = u.id\n" +
            "         inner join project p on project_member.project_id = p.id\n" +
            "where project_name=?1",nativeQuery = true)
    List<ProjectMember> findAllProjectMemberByProjectId(int id);

    List<ProjectMember> findAllByProjectId(int id);

    @Query(value = "select *\n" +
            "from project_member where project_id=?1 and user_id=?2",nativeQuery = true)
    ProjectMember findByProjectAndUser(int projectId,int userId);
}
