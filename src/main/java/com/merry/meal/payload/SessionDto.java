package com.merry.meal.payload;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.merry.meal.data.User;
import com.merry.meal.status.CareStatus;
import com.merry.meal.status.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor  
public class SessionDto {
	private Long fundId;
	private String session;
	private String date;

	private String status;
	@JsonIgnore
	private User user;
	
}
