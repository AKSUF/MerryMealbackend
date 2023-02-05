package com.merry.meal.payload;

import com.merry.meal.data.Fund;
import com.merry.meal.data.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PartnerFundDto {
	private Long partner_fund_id;
	private UserDto user;
private FunDto fund;

}
