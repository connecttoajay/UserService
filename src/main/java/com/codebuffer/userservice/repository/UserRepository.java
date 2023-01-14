package com.codebuffer.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.codebuffer.userservice.Entity.UserData;

@Repository
public interface UserRepository extends JpaRepository<UserData, Long> {
    UserData findByUserId(Long id);
}
