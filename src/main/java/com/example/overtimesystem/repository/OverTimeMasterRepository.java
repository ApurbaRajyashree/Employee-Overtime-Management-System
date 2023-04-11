package com.example.overtimesystem.repository;

import com.example.overtimesystem.entity.OverTimeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OverTimeMasterRepository extends JpaRepository<OverTimeMaster,Integer> {
}
