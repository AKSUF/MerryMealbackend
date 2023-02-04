package com.merry.meal.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.merry.meal.config.AppFundConstant;
import com.merry.meal.config.FundsResponse;
import com.merry.meal.payload.ApiResponse;

import com.merry.meal.payload.FunDto;
import com.merry.meal.payload.SessionDto;
import com.merry.meal.services.FundService;



@RestController
public class FundController {
@Autowired
	private FundService fundService;

@PostMapping("/carepost")
public ResponseEntity<FunDto>donate(@RequestBody FunDto funDto,HttpServletRequest request){
	FunDto newDonate=this.fundService.donate(funDto, request);
	return new ResponseEntity<FunDto>(newDonate,HttpStatus.CREATED);
	
}
//ppostasuserId
@PostMapping("/carepost/{userId}")
public ResponseEntity<FunDto>donation(@RequestBody FunDto funDto,HttpServletRequest request,@PathVariable Long userId){
	FunDto donation=this.fundService.donation(funDto,request,userId);
	
	return new ResponseEntity<FunDto>(donation,HttpStatus.CREATED);
	
}


//@GetMapping("/carepost/{userId}")
//public ResponseEntity<List<FunDto>>getfundByUser(@PathVariable Integer userId,HttpServletRequest request){
//	List<FunDto>funds=this.fundService.getFundsByUser(userId,request);
//	System.out.println(funds);
//	return new ResponseEntity<List<FunDto>>(funds,HttpStatus.OK);
//	}
//
////
//@GetMapping("/{userId}/caregivepost")
//public ResponseEntity<List<SessionDto>>getSessionasUser(@PathVariable Long userId,HttpServletRequest request){
//	List<SessionDto>sessionDtos=this.caregiveService.getsessionByUser(userId,request);
//	return new ResponseEntity<List<SessionDto>>(sessionDtos,HttpStatus.OK);
//	
//}

@GetMapping("/carepost/{userId}")
public ResponseEntity<List<FunDto>>getfundAsUser(@PathVariable Long userId,HttpServletRequest request){
	List<FunDto>funds=this.fundService.getFundsByUser(userId, request);
	return new ResponseEntity<List<FunDto>>(funds,HttpStatus.OK);
	
}


@GetMapping("/page/cares")
public ResponseEntity<List<FunDto>>getAllFund(
		@RequestParam(value="pazeNumber",defaultValue = "1",required = false)Integer pageNumber,
		@RequestParam (value="pazeSize",defaultValue = "10",required = false)Integer pageSize){
	System.out.println("This controller for pazination");
	List<FunDto>allFund=this.fundService.getAllSession(pageNumber,pageSize);
	System.out.println(allFund);
			return new ResponseEntity<List<FunDto>>(allFund,HttpStatus.OK);
	
}


@DeleteMapping("/cares/{fundId}")
public ApiResponse deletePost(@PathVariable Integer fundId) {
	this.fundService.deletefund(fundId);
	return new ApiResponse("Fund is deleted successfully",true);
	
}
@PutMapping("/cares/{fundId}")
public ResponseEntity<FunDto>updateFund(@RequestBody FunDto funDto,@PathVariable Integer fundId){
	FunDto updateFund=this.fundService.updateFund(funDto,fundId);
	return new ResponseEntity<FunDto>(updateFund,HttpStatus.OK);
	
}

@GetMapping("/{userId}/cares")
public ResponseEntity<List<FunDto>>getFund(@PathVariable Long userId,HttpServletRequest request){
	List<FunDto>funDtos=this.fundService.getFundAsUser(userId,request);
	return new ResponseEntity<List<FunDto>>(funDtos,HttpStatus.OK);
	
}
@GetMapping("/posts")
public ResponseEntity<FundsResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppFundConstant.PAZE_NUMBER, required = false) Integer pageNumber,
		@RequestParam(value = "pazeSize", defaultValue = AppFundConstant.PAGE_SIZE, required = false) Integer pazeSize,
		@RequestParam(value = "sortBy", defaultValue = AppFundConstant.SORT_BY, required = false) String sortBy,
		@RequestParam(value = "sortDir", defaultValue = AppFundConstant.SORT_DIR, required = false) String sortDir){
	FundsResponse postResponse = this.fundService.getAllPost(pageNumber, pazeSize,sortBy,sortDir);


	return new ResponseEntity<FundsResponse>(postResponse, HttpStatus.OK);

	
}




}
