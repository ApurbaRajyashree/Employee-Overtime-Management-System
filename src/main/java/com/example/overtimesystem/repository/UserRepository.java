package com.example.overtimesystem.repository;

import com.example.overtimesystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User findByFullName(String name);

    List<User> findAllByDepartment_DepartmentName(String name);

    @Query("select u from User u where u.email= :email")
    public User getUserByUserName(@Param("email") String email);

    List<User> findAllByDepartmentId(int id);


}
