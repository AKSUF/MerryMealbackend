package com.merry.meal.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.merry.meal.config.FundsResponse;
import com.merry.meal.payload.FunDto;

public interface FundService {

	FunDto donate(FunDto funDto, HttpServletRequest request);

	FunDto donation(FunDto funDto, HttpServletRequest request, Long userId);

	List<FunDto> getFundsByUser(Integer userId, HttpServletRequest request);

	List<FunDto> getAllSession(Integer pageNumber, Integer pageSize);

	void deletefund(Integer fundId);

	FunDto updateFund(FunDto funDto, Integer fundId);

	List<FunDto> getFundAsUser(Long userId, HttpServletRequest request);

	FundsResponse getAllPost(Integer pageNumber, Integer pazeSize, String sortBy, String sortDir);



}
