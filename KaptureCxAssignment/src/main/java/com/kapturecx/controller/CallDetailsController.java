package com.kapturecx.controller;

import java.sql.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kapturecx.entity.CallDetails;
import com.kapturecx.exceptions.CallException;
import com.kapturecx.repository.CallDetailsRepo;
import com.kapturecx.service.CallDetailsServiceImpl;

@RestController
@RequestMapping("callDetails")
public class CallDetailsController {

	@Autowired
	CallDetailsRepo callDetailsRepo;

	@Autowired
	CallDetailsServiceImpl callDetailsServiceImpl;
	
	@PostMapping("save")
	ResponseEntity<CallDetails> saveCall(@Valid @RequestBody CallDetails callDetails) {
		CallDetails callDetail = callDetailsServiceImpl.saveCall(callDetails);
		return new ResponseEntity<CallDetails>(callDetail, HttpStatus.CREATED);
	}

	@GetMapping("allCalls")
	ResponseEntity<List<CallDetails>> getAllCalls() {
		List<CallDetails> allCalls = callDetailsRepo.findAll();
		return new ResponseEntity<List<CallDetails>>(allCalls, HttpStatus.OK);
	}

	@GetMapping("CallDetail/{number}")
	ResponseEntity<List<CallDetails>> getCallDetailsByNumber(@PathVariable String number) throws CallException {
		List<CallDetails> getCalls = callDetailsRepo.findByFromNumber(number);
		if(getCalls.size()==0) {
			throw new CallException("Call Details does not exist with number : "+number);
		}
		return new ResponseEntity<List<CallDetails>>(getCalls, HttpStatus.OK);
	}

	@GetMapping("CallDetailById/{id}")
	ResponseEntity<CallDetails> getCallDetailsById(@PathVariable Integer id) throws CallException {
		CallDetails getCalls = callDetailsRepo.findById(id)
				.orElseThrow(() -> new CallException("Call doesn't exist with Id : " + id));
		return new ResponseEntity<CallDetails>(getCalls, HttpStatus.OK);
	}

	@DeleteMapping("deleteCallById/{id}")
	ResponseEntity<String> deleteCallDetailsById(@PathVariable Integer id) throws CallException {
		if (!callDetailsRepo.existsById(id)) {
			throw new CallException("Call doesn't exist with Id : " + id);
		}
		callDetailsRepo.deleteById(id);
		return new ResponseEntity<String>("Deleted", HttpStatus.OK);
	}

	@GetMapping("allCallReq")
	ResponseEntity<String> getAllCallReq() {
		List<Object[]> allCalls = callDetailsRepo.callList();
		Object hour =   allCalls.get(0)[0];
		int hourInt = Integer.parseInt((String) hour);
		String response = "Hour of the day when the call volume is highest is "+hour+"-"+hourInt;
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@GetMapping("/call/{starttime}")
	 public ResponseEntity<String> longestcallbyhour(@PathVariable("starttime") Date starttime) throws CallException{
		 return ResponseEntity.ok(callDetailsServiceImpl.highestCallByHour(starttime));
	 }
	
}
