package com.merry.meal.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.merry.meal.config.AppSessionConstatnts;
import com.merry.meal.config.SessionResponse;
import com.merry.meal.data.Account;
import com.merry.meal.data.Session;
import com.merry.meal.data.User;
import com.merry.meal.payload.ApiResponse;

import com.merry.meal.payload.SessionDto;

import com.merry.meal.repo.AccountRepo;
import com.merry.meal.services.CaregiveService;
import com.merry.meal.utils.JwtUtils;


@RestController
public class CaregiveController {
@Autowired
	private CaregiveService caregiveService;
@Autowired
private JwtUtils jwtUtils;
@Autowired
private AccountRepo accountRepo;
@Autowired
private ModelMapper modelmapper;
@PostMapping("/caregivepost")
public ResponseEntity<SessionDto>newSession(@RequestBody SessionDto sessionDto,HttpServletRequest request){
	System.out.println("fgfdgghfdhfdghdfgdfgdfggetdgdgdsgsdgds/////////////////////////////////////");
	SessionDto newSessionDto=this.caregiveService.addSession(sessionDto, request);
	return new ResponseEntity<SessionDto>(newSessionDto,HttpStatus.CREATED);
	
}


@PostMapping("/caregivepost/{userId}")
public ResponseEntity<SessionDto>newCreateSession(@RequestBody SessionDto sessionDto,HttpServletRequest request, Long userId){
	System.out.println("ffhfghfgdgdfgdgd");
	SessionDto newSessionDto=this.caregiveService.createSession(sessionDto, userId, request);
	return new ResponseEntity<SessionDto>(newSessionDto,HttpStatus.CREATED);
	
}

@GetMapping("/caregivepost/{sessionId}")
public ResponseEntity<SessionDto>getpost(@PathVariable Long sessionId){
	System.out.println("This controller for get session as ");
	SessionDto getSession=this.caregiveService.getoneSession(sessionId);
	System.out.println(getSession);
	return new ResponseEntity<SessionDto>(getSession,HttpStatus.OK);
	
}

@GetMapping("/{userId}/caregivepost")
public ResponseEntity<List<SessionDto>>getSessionasUser(@PathVariable Long userId,HttpServletRequest request){
	List<SessionDto>sessionDtos=this.caregiveService.getsessionByUser(userId,request);
	return new ResponseEntity<List<SessionDto>>(sessionDtos,HttpStatus.OK);
	
}


@DeleteMapping("/{sessionId}/caregivepost")
public ApiResponse deleteSession(@PathVariable Long sessionId,HttpServletRequest request) {
	this.caregiveService.deleteSession(sessionId,request);
	return new ApiResponse("Session is successfully deleted",true);
	
}
//this code for update session
@PutMapping("/{sessionId}/caregivepost")
public ResponseEntity<SessionDto>updateSession(@RequestBody SessionDto sessionDto,@PathVariable Long sessionId){
	SessionDto updateSession=this.caregiveService.updateSession(sessionDto,sessionId);
	return new ResponseEntity<SessionDto>(updateSession,HttpStatus.OK);
	
}

@GetMapping("/caregives/sessions")
public ResponseEntity<SessionResponse> getAllSession(	
		@RequestParam(value = "pageNumber", defaultValue = AppSessionConstatnts.PAZE_NUMBER, required = false) Integer pageNumber,
		@RequestParam(value = "pazeSize", defaultValue = AppSessionConstatnts.PAGE_SIZE, required = false) Integer pazeSize,
		@RequestParam(value = "sortBy", defaultValue = AppSessionConstatnts.SORT_BY, required = false) String sortBy
		){
	System.out.println("------------------This is for GetPageof---------------------");
	SessionResponse allSession=this.caregiveService.getSession(pageNumber,pazeSize,sortBy);
	
	System.out.println("All session to get"+allSession);
	return new ResponseEntity<SessionResponse>(allSession,HttpStatus.OK);
	
}



}
