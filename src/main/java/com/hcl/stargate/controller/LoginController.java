package com.hcl.stargate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.stargate.bean.Message;
import com.hcl.stargate.bean.StargateUser;
import com.hcl.stargate.repository.StargateUserRepository;

@CrossOrigin
@RestController
public class LoginController {
	 @Autowired
	    private StargateUserRepository repo;
	
	  @PostMapping("/login")
	  public ResponseEntity<String> login(@RequestBody StargateUser stargateuser) {
		  
		  System.out.println("Into login");
		  String userName =stargateuser.getUsername();
		  String userPassword=stargateuser.getPassword();
		  System.out.println("user :::"+userName +"::::password:::"+userPassword);
		  StargateUser user=repo.getById(userName);
		  String jsonString="";
		  if (user!=null&&userName.equalsIgnoreCase(user.getUsername())&&userPassword.equalsIgnoreCase(user.getPassword()))
		  { System.out.println("Into login  ::"+user.getPassword());
		  Message msg=new Message();
		  ObjectMapper mapper = new ObjectMapper();
		  mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		  msg.setMsg("Success");
	      //Converting the Object to JSONString
	      
		try {
			jsonString = mapper.writeValueAsString(msg);
		} catch (JsonProcessingException e) {
		
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(jsonString);
		  }else {
		  return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error");
		  }
	  }
	  }


