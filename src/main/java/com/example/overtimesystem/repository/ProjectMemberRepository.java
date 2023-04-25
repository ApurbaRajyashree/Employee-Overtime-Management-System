package com.example.overtimesystem.repository;

import com.example.overtimesystem.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Integer> {

    @Query(value = "select pm.*\n" +
            "from project_member pm inner join project p on pm.project_id = p.id\n" +
            "where p.id=?1 and p.is_active=true order by pm.id",nativeQuery = true)
    List<ProjectMember> findAllProjectMemberByProjectId(int id);

    List<ProjectMember> findAllByProjectId(int id);

    @Query(value = "select *\n" +
            "from project_member where project_id=?1 and user_id=?2",nativeQuery = true)
    ProjectMember findByProjectAndUser(int projectId,int userId);
}
