package com.merry.meal.services.impl;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.merry.meal.config.SessionResponse;
import com.merry.meal.data.Account;
import com.merry.meal.data.CareMember;
import com.merry.meal.data.Caregiver;
import com.merry.meal.data.Fund;
import com.merry.meal.data.Session;
import com.merry.meal.data.User;
import com.merry.meal.exceptions.ResourceNotFounException;
import com.merry.meal.exceptions.ResourceNotFoundException;
import com.merry.meal.payload.CareMemberDto;
import com.merry.meal.payload.CaregiveDto;
import com.merry.meal.payload.SessionDto;
import com.merry.meal.repo.AccountRepo;
import com.merry.meal.repo.CareMemberRepository;
import com.merry.meal.repo.FundRepo;
import com.merry.meal.repo.SessionRepository;
import com.merry.meal.services.CaregiveService;
import com.merry.meal.status.CareStatus;
import com.merry.meal.utils.JwtUtils;

@Service
public class CaregiveServiceImpl implements CaregiveService {

	@Autowired
	private ModelMapper modelmapper;

	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private AccountRepo accountRepo;
	@Autowired
	private SessionRepository sessionRepo;
@Autowired
	private CareMemberRepository careMemberRepository;
	private String getJWTFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}

		return null;
	}
//only caregiver can add their session
	@Override
	public SessionDto addSession(SessionDto sessionDto, HttpServletRequest request) {
		String token = getJWTFromRequest(request);
		String email = jwtUtils.getUserNameFromToken(token);
		Account account = accountRepo.findByEmail(email).get();
		System.out.println("////////////////////////////////////");
		System.out.println(account);
		System.out.println("////////////////////////////////////");
		if (account.getUser() != null) {
			User user = account.getUser();
			System.out.println();
			user.getUser_id();
		}
		// User user = account.getUser();
		CareMember careMember = new CareMember();
		User user = careMember.getUser();

		System.out.println("get user entity" + user);
		Session session = this.modelmapper.map(sessionDto, Session.class);
		session.setCareStatus(CareStatus.Available.name());
		session.setUser(user);
		Session saveSession = this.sessionRepo.save(session);
		return this.modelmapper.map(saveSession, SessionDto.class);
	}
//only care caregiver can add session in the databaseas user id
	@Override
	public SessionDto createSession(SessionDto sessionDto, Long userId, HttpServletRequest request) {
		String token = getJWTFromRequest(request);
		String email = jwtUtils.getUserNameFromToken(token);
		Account account = accountRepo.findByEmail(email).get();
		System.out.println("it will get account details of logged in user" + account);
		if (account.getUser() != null) {
			User user = account.getUser();
			System.out.println("wiil get the user details" + user);
			user.getUser_id();
		}

		CareMember careMember = new CareMember();
		User user = careMember.getUser();
		System.out.println("get user entity" + user);
		Session session = this.modelmapper.map(sessionDto, Session.class);
		session.setCareStatus(CareStatus.Available.name());
	
		Session saveSession = this.sessionRepo.save(session);
		return this.modelmapper.map(saveSession, SessionDto.class);

	}
//User and can member both can see the session 
	@Override
	public SessionDto getoneSession(Long sessionId) {

		Session session = this.sessionRepo.findById(sessionId)
				.orElseThrow(() -> new ResourceNotFounException("Session", "SessionId", sessionId));
		System.out.println(session);
		return this.modelmapper.map(session, SessionDto.class);
	}

//Member will get caregiver session 
	@Override
	public List<SessionDto> getsessionByUser(Long userId, HttpServletRequest request) {
		String token = getJWTFromRequest(request);
		String email = jwtUtils.getUserNameFromToken(token);
		Account account = accountRepo.findByEmail(email).get();
		System.out.println("////////////////////////////////////");
		System.out.println(account);
		System.out.println("////////////////////////////////////");
		if (account.getUser() != null) {
			User user = account.getUser();
			System.out.println();
			user.getUser_id();
		}

		CareMember careMember = new CareMember();
		User user = careMember.getUser();
		System.out.println("get user entity" + user);
		List<Session> sessions = this.accountRepo.findByUser(user);
		List<SessionDto> sessionDtos = sessions.stream()
				.map((session) -> this.modelmapper.map(session, SessionDto.class)).collect(Collectors.toList());
		return sessionDtos;
	}

//Only Caregiver able to delete session
	@Override
	public void deleteSession(Long sessionId, HttpServletRequest request) {
		String token = getJWTFromRequest(request);
		String email = jwtUtils.getUserNameFromToken(token);
		Account account = accountRepo.findByEmail(email).get();
		System.out.println("////////////////////////////////////");
		System.out.println(account);
		System.out.println("////////////////////////////////////");
		if (account.getUser() != null) {
			User user = account.getUser();
			System.out.println();
			user.getUser_id();
		}
		Session session = this.sessionRepo.findById(sessionId)
				.orElseThrow(() -> new ResourceNotFounException("Session", "Session Id", sessionId));
		this.sessionRepo.delete(session);
	}

	// only caregiver update his session
	@Override
	public SessionDto updateSession(SessionDto sessionDto, Long sessionId) {
		// Session session=this.sessionRepo.findById(sessionId).orElseThrow(()->new
		// ResourceNotFounException("Session","Session Id",sessionId));
		System.out.println("////////////////////Session ---------------Id---//////////////////////");
		Session session = this.sessionRepo.findById(sessionId)
				.orElseThrow(() -> new ResourceNotFounException("Session", "Session Id", sessionId));
		System.out.println("////////////////////Session ---------------Id---//////////////////////");
		session.setCareStatus(sessionDto.getStatus());
		session.setDate(sessionDto.getSession());
		session.setSession(sessionDto.getSession());
		Session updateSession = sessionRepo.save(session);
		return this.modelmapper.map(updateSession, SessionDto.class);
	}
//Caregiver and member both
	@Override
	public SessionResponse getSession(Integer pageNumber, Integer pazeSize, String sortBy) {
//		Pageable page = (Pageable) PageRequest.of(pageNumber, pazeSize,Sort.by(sortBy).descending());

		PageRequest page = PageRequest.of(pageNumber, pazeSize, Sort.by(sortBy).descending());

		Page<Session> pagePost = this.sessionRepo.findAll(page);

		List<Session> allPosts = pagePost.getContent();
//			List<SessionDto> postDtos = allPosts.stream().map((post)-> 
//			this.modelMapper.map(post, PostDto.class)).
//					collect(Collectors.toList());
		List<SessionDto> allSession = allPosts.stream().map((post) -> this.modelmapper.map(post, SessionDto.class))
				.collect(Collectors.toList());
		SessionResponse newResponse = new SessionResponse();
		newResponse.setContent(allSession);
		newResponse.setTotalPages(pagePost.getTotalPages());
		newResponse.setTotalElement(pagePost.getTotalElements());
		newResponse.setPageSize(pagePost.getSize());
		newResponse.setLastPage(pagePost.isLast());

		return newResponse;
	}
	@Override
	public List<CareMemberDto> getAllCareMember(HttpServletRequest request) {
		
		String token = getJWTFromRequest(request);
		String email = jwtUtils.getUserNameFromToken(token);
		Account account = accountRepo.findByEmail(email).get();
		System.out.println("////////////////////////////////////");
		System.out.println(account);
		System.out.println("////////////////////////////////////");
		if (account.getUser() != null) {
			User user = account.getUser();
			System.out.println();
			user.getUser_id();
		}

User user=account.getUser();
List<CareMember> carememb= user.getCaremember();

		List<CareMember>allCareMembers=this.careMemberRepository.findAll();
		List<CareMemberDto>caregiveDtos=carememb.stream().map((caremember)->this.modelmapper.map(caremember,CareMemberDto.class)).collect(Collectors.toList());
		return caregiveDtos;
	}
	@Override
	public SessionDto changeStatus(Long sessionId, String status) {
		Session newStatus=this.sessionRepo.findById(sessionId).orElseThrow(()->new ResourceNotFoundException("session","session id",sessionId.toString()));
		newStatus.setStatus(status);
		Session changeSession=this.sessionRepo.save(newStatus);
		return this.modelmapper.map(newStatus,SessionDto.class);
	}


	
	
}
