package com.Ironhack.FinalProject.repositories;

import com.Ironhack.FinalProject.accountmodels.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long> {
}
