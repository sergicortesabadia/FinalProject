package com.Ironhack.FinalProject.repositories;

import com.Ironhack.FinalProject.accountmodels.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Long> {
}
