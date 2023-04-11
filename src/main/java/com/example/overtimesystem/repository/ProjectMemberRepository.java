package com.example.overtimesystem.repository;

import com.example.overtimesystem.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectMemberRepository  extends JpaRepository<ProjectMember,Integer> {
}
