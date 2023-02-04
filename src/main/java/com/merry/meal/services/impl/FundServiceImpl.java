package com.merry.meal.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.merry.meal.config.FundsResponse;
import com.merry.meal.data.Account;
import com.merry.meal.data.Fund;
import com.merry.meal.data.User;
import com.merry.meal.exceptions.ResourceNotFounException;
import com.merry.meal.payload.FunDto;

import com.merry.meal.payload.UserDto;
import com.merry.meal.repo.AccountRepo;
import com.merry.meal.repo.FundRepo;
import com.merry.meal.services.FundService;
import com.merry.meal.status.Status;
import com.merry.meal.utils.JwtUtils;

@Service
public class FundServiceImpl implements FundService {
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private AccountRepo accountRepo;
	@Autowired
	private FundRepo fundRepo;
	@Autowired
	private ModelMapper modelmapper;
	private String getJWTFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}

		return null;
	}
	
	
	
	
	
	@Override
	public FunDto donate(FunDto funDto, HttpServletRequest request) {
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
		User user = account.getUser();

		System.out.println("get user entity" + user);
		Fund fund = this.modelmapper.map(funDto, Fund.class);
		fund.setStatus(Status.Approve.name());
		fund.setUser(user);
		Fund newdonate = this.fundRepo.save(fund);
		return this.modelmapper.map(newdonate, FunDto.class);
	}
	
	
	@Override
	public FunDto donation(FunDto funDto, HttpServletRequest request, Long userId) {
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
		User user = account.getUser();

		Fund fund = this.modelmapper.map(funDto, Fund.class);
		fund.setStatus(Status.Approve.name());
		fund.setUser(account.getUser());
		Fund newdonate = this.fundRepo.save(fund);
		return this.modelmapper.map(newdonate, FunDto.class);
	}
	@Override
	public List<FunDto> getFundsByUser(Integer userId, HttpServletRequest request) {
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
		User user = account.getUser();
		
		List<Fund>funds=this.fundRepo.findByUser(account.getUser());
		System.out.println(funds);
		List<FunDto>fundto=funds.stream().map((fund)->this.modelmapper.map(funds,FunDto.class)).collect(Collectors.toList());
		return fundto;
	}
	@Override
	public List<FunDto> getAllSession(Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void deletefund(Integer fundId) {
		Fund fund=this.fundRepo.findById(fundId).orElseThrow(()->new ResourceNotFounException("Funds","Fund id",fundId));

		this.fundRepo.delete(fund);
		
	}
	@Override
	public FunDto updateFund(FunDto funDto, Integer fundId) {
		Fund fund=this.fundRepo.findById(fundId).orElseThrow(()->new ResourceNotFounException("Funds","Fund id",fundId));
		fund.setAmount(funDto.getAmount());
		fund.setStatus(funDto.getStatus());
		Fund updatepost=fundRepo.save(fund);
		return this.modelmapper.map(updatepost,FunDto.class);
	}
	@Override
	public List<FunDto> getFundAsUser(Long userId, HttpServletRequest request) {
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
		System.out.println("get user entity" + user);
		
		List<Fund>funds=user.getFunds();
		List<FunDto>funDtos=funds.stream().map((fund)->this.modelmapper.map(fund,FunDto.class)).collect(Collectors.toList());
		return funDtos;
	}
	@Override
	public FundsResponse getAllPost(Integer pageNumber, Integer pazeSize, String sortBy, String sortDir) {
		System.out.println(pageNumber+pazeSize+""+sortBy);
		System.out.println("//////////////////////////////////metydgdgfsdfsa");
		
	Pageable page = PageRequest.of(pageNumber, pazeSize,Sort.by(sortBy).descending());
	    Page<Fund> pagePost = this.fundRepo.findAll(page);
	    List<Fund> allPosts = pagePost.getContent();
	    System.out.println("This is fund information /////////////////////////////////////////////////////");
	    System.out.println(allPosts);
	    List<FunDto> postDtos = allPosts.stream()
	        .map(post -> {
	            FunDto dto = this.modelmapper.map(post, FunDto.class);
	            User user = post.getUser();
	            if (user != null) {
	                UserDto userDto = this.modelmapper.map(user, UserDto.class);
	                dto.setUser(userDto);
	            }
	            return dto;
	        })
	        .collect(Collectors.toList());
	    FundsResponse postResponse = new FundsResponse();
	    postResponse.setContent(postDtos);
	    postResponse.setPageNumber(pagePost.getNumber());
	    postResponse.setPageSize(pagePost.getSize());
	    postResponse.setTotalElement(pagePost.getTotalElements());
	    postResponse.setTotalPages(pagePost.getTotalPages());
	    postResponse.setLastPage(pagePost.isLast());
	    return postResponse;
	}

	

	
	
	
	

	
}
