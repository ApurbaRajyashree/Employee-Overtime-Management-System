package com.example.overtimesystem.repository;

import com.example.overtimesystem.entity.OverTimeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OverTimeMasterRepository extends JpaRepository<OverTimeMaster, Integer> {

@Query(value = "select * from over_time_master where year=?1 and month=?2 and user_id=?3",nativeQuery = true)
OverTimeMaster findByUserYearAndMonth(Integer year, String month, Integer userId);
}
