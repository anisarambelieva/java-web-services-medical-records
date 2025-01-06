package com.cscb869_medical_records.data.repo;

import com.cscb869_medical_records.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
