package com.kapturecx.service;

import java.sql.Date;
import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kapturecx.entity.CallDetails;
import com.kapturecx.exceptions.CallException;
import com.kapturecx.repository.CallDetailsRepo;

@Service
public class CallDetailsServiceImpl implements CallDetailsService {

	@Autowired
	CallDetailsRepo callDetailsRepo;

	@Override
	public CallDetails saveCall(CallDetails callDetails) {

		CallDetails details = new CallDetails();
		details.setStartTime(callDetails.getStartTime());
		details.setEndTime(callDetails.getEndTime());

		Duration duration = Duration.between(callDetails.getStartTime(), callDetails.getEndTime());
		long seconds = duration.getSeconds();

		details.setDuration((int) seconds);

		return callDetailsRepo.save(details);
	}

	@Override
	public String longestCallVolumeByHour(Date startTime) throws CallException {
		List<Object[]> list = callDetailsRepo.longestCallByHour(startTime);

		if (list == null) {
			throw new CallException("data not found");
		}
		Object[] data = list.get(0);
		Integer start = (int) data[0];
		Integer end = (int) data[1];

		if (start == 0 && end >= 0) {
			return "Hour of the day when the calls are longest is 12-" + (end + 1) + " AM";
		} else if (start < 12 && end < 11) {
			return "Hour of the day when the calls are longest is " + start + " - " + (end + 1) + " AM";
		} else if (start <= 11 && end == 11) {
			return "Hour of the day when the calls are longest is  " + start + " AM -" + (end + 1) + " PM";
		} else if (start <= 11 && end <= 12) {
			return "Hour of the day when the calls are longest is  " + start + " AM -" + (end - 11) + " PM";
		} else if (start == 12) {
			return "Hour of the day when the calls are longest is  " + start + " PM -" + (end - 11) + " PM";
		} else if (start <= 23 && end == 23) {
			return "Hour of the day when the calls are longest is  " + (start - 12) + " PM-" + (end - 11) + " AM";
		} else if (start <= 23 && end >= 0) {
			return "Hour of the day when the calls are longest is  " + (start - 12) + " PM -" + (end - 11) + " AM";
		} else {
			return "Hour of the day when the calls are longest is  " + (start - 12) + " - " + (end - 11) + " PM";
		}
	}

	@Override
	public String highestCallByHour(Date startTime) throws CallException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String longestCallVolumeByWeek(Date startTime) throws CallException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String highestCallVolumeByWeek(Date startTime) throws CallException {
		// TODO Auto-generated method stub
		return null;
	}

}
