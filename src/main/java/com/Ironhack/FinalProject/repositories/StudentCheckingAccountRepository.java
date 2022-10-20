package com.Ironhack.FinalProject.repositories;

import com.Ironhack.FinalProject.accountmodels.StudentCheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCheckingAccountRepository extends JpaRepository<StudentCheckingAccount, Long> {
}
