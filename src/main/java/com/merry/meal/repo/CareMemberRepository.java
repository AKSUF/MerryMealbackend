package com.merry.meal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merry.meal.data.CareMember;

public interface CareMemberRepository extends JpaRepository<CareMember,Long> {

}
