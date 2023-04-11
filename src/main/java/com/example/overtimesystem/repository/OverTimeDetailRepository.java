package com.example.overtimesystem.repository;

import com.example.overtimesystem.entity.OverTimeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OverTimeDetailRepository extends JpaRepository<OverTimeDetail,Integer> {
}
