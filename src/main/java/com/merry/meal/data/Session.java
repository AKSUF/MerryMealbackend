package com.merry.meal.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.merry.meal.status.CareStatus;
import com.merry.meal.status.Status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Session {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long fundId;
	private String session;
	private String date;

	private String status;
	
	@JsonIgnore
	@ManyToOne	
	private User user;
//	@Enumerated(EnumType.STRING)
//	private Status status;
//	
//	public void setStatus(String name) {
//	this.status=status;
//		
//	}
	
	@OneToMany(mappedBy = "session", cascade = CascadeType.MERGE)
	private List<CareMember>caremembercaregiver;
	
	public void setCareStatus(String name) {
	this.status=name;
		
	}

	


}
