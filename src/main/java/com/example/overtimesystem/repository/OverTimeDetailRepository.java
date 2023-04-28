package com.example.overtimesystem.repository;

import com.example.overtimesystem.entity.OverTimeDetail;
import com.example.overtimesystem.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OverTimeDetailRepository extends JpaRepository<OverTimeDetail, Integer> {

    @Query(value = "select * from over_time_detail where over_time_master_id=?1",nativeQuery = true)
    List<OverTimeDetail> findAllByOverTimeMaster(int id);

    @Query(value = "select project_name from project inner join over_time_detail otd on project.id = otd.project_id " +
            "where over_time_master_id=?1 group by project_name\n",nativeQuery = true)
    List<String> findAllProjectInOverTimeDetail(int id);
}


