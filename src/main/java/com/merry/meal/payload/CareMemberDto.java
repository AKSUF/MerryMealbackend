package com.merry.meal.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.merry.meal.data.Caregiver;
import com.merry.meal.data.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CareMemberDto {
	private Long carememberId;
	private UserDto user;
	private UserDto caregiver;
	
}
