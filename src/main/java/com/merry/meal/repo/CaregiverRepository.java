package com.merry.meal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merry.meal.data.Caregiver;

public interface CaregiverRepository extends JpaRepository<Caregiver,Long> {

}
