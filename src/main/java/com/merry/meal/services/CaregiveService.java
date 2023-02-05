package com.merry.meal.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.merry.meal.config.SessionResponse;
import com.merry.meal.payload.CareMemberDto;
import com.merry.meal.payload.CaregiveDto;
import com.merry.meal.payload.SessionDto;

public interface CaregiveService {
	SessionDto addSession(SessionDto sessionDto, HttpServletRequest request);

	SessionDto createSession(SessionDto sessionDto, Long userId, HttpServletRequest request);

	SessionDto getoneSession(Long sessionId);

	List<SessionDto> getsessionByUser(Long userId,HttpServletRequest request);

	void deleteSession(Long sessionId, HttpServletRequest request);

	SessionDto updateSession(SessionDto sessionDto, Long sessionId);



	SessionResponse getSession(Integer pageNumber, Integer pazeSize, String sortBy);

	List<CareMemberDto> getAllCareMember(HttpServletRequest request);

	SessionDto changeStatus(Long sessionId, String status);


}
