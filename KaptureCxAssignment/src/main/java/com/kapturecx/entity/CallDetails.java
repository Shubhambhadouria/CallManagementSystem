package com.kapturecx.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CallDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer Id;
	
	@Size(min = 10,max = 10,message = "Mobile Number Should be in 10 digits")
	public String fromNumber;
	
	public LocalDateTime startTime;
	public LocalDateTime endTime;
	public Integer duration;
	
	
}
