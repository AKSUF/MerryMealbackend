package com.merry.meal.payload;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.merry.meal.data.User;
import com.merry.meal.status.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FunDto {
	private int fund_id;
	private int amount;

	private String status;
	@JsonIgnore
	private UserDto user;

}
