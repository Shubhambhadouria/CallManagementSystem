package com.kapturecx.service;

import java.sql.Date;

import com.kapturecx.entity.CallDetails;
import com.kapturecx.exceptions.CallException;

public interface CallDetailsService {

	CallDetails saveCall(CallDetails callDetails);
	
	String longestCallVolumeByHour(Date startTime) throws CallException;

	String highestCallByHour(Date startTime) throws CallException;
	
	String longestCallVolumeByWeek(Date startTime) throws CallException;
	
	String highestCallVolumeByWeek(Date startTime) throws CallException;
	
}
