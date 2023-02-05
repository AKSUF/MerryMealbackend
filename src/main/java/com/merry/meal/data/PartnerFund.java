package com.merry.meal.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "partner_fund")
@NoArgsConstructor
@Getter
@Setter
public class PartnerFund {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long partner_fund_id;
	
	@OneToMany(mappedBy = "partnerfund", cascade = CascadeType.MERGE)
	private List<Fund>funds;
	
	@ManyToOne
	@JoinColumn(name = "user_id",referencedColumnName = "user_id")
	private User user;
	
}
