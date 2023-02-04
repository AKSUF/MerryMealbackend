package com.merry.meal.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merry.meal.data.Fund;
import com.merry.meal.data.User;

public interface FundRepo extends JpaRepository<Fund,Integer> {

	List<Fund> findByUser(User user);

}
