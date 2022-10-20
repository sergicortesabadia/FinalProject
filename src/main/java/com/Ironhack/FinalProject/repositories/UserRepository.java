package com.Ironhack.FinalProject.repositories;

import com.Ironhack.FinalProject.usermodels.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
