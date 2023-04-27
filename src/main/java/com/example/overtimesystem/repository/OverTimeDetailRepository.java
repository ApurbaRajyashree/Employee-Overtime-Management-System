package com.example.overtimesystem.repository;

import com.example.overtimesystem.entity.OverTimeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OverTimeDetailRepository extends JpaRepository<OverTimeDetail, Integer> {

    @Query(value = "select * from over_time_detail where over_time_master_id=?1",nativeQuery = true)
    List<OverTimeDetail> findAllByOverTimeMaster(int id);
}
