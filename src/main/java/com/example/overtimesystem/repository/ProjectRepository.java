package com.example.overtimesystem.repository;

import com.example.overtimesystem.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Project findByProjectCode(String code);

    @Modifying
    @Query(value = "UPDATE project set is_active=false where id=?1",nativeQuery = true)
    void deleteById(int id);
}
