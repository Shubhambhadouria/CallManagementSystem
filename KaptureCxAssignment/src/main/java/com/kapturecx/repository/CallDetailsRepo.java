package com.kapturecx.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kapturecx.entity.CallDetails;

@Repository
public interface CallDetailsRepo extends JpaRepository<CallDetails, Integer> {

	List<CallDetails> findByFromNumber(String fromNumber);
	
	@Query(value = "Select Hour(c.startTime) as start,Count(c) "
			+ "as volume From CallDetails c Group by Hour(c.startTime) "
			+ "Order by volume Desc")
	List<Object[]> callList();
	
	@Query("Select Hour(c.startTime) as start,Hour(c.endTime) as end ,Count(c) "
			+ "as volume From CallDetails c where DATE(c.startTime)=date(:starttime) "
			+ "Group by Hour(c.startTime)Order by volume Desc")
	public List<Object[]> longestCallByHour(@Param("starttime") Date starttime);
	
	@Query("Select DAYNAME(c.startTime) as start,Count(c) as volume From CallDetails c "
			+ "Group by DAYNAME(c.startTime)Order by volume Desc")
	public List<Object[]> longestCallVolumeByWeek();
	
//	@Query("Select Hour(c.startTime) as start From CallDetails c where DATE(c.startTime)=date(:starttime)"
//			+ "Order by c.startTime, Duration Desc LIMIT 1")
//	public Object highestCallByHour(@Param("starttime") Date starttime);
	
//	@Query("Select DAYNAME(c.startTime) as start From CallDetails c Order by Duration Desc LIMIT 1")
//	public Object highestCallByWeek();
}
