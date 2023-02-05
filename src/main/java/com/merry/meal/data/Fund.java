package com.merry.meal.data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.merry.meal.status.Status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Fund {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int fundId;
	private int amount;

	private String status;
	@JsonIgnore
	@ManyToOne	
	private User user;
	@ManyToOne	
	private PartnerFund partnerfund;

}
