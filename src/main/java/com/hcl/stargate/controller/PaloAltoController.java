package com.hcl.stargate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.hcl.stargate.bean.Response;
import com.hcl.stargate.bean.Message;
import com.hcl.stargate.bean.PaloAltoUser;
import com.hcl.stargate.service.PaloAltoKeyGenService;
@CrossOrigin
@RestController
public class PaloAltoController {

	@Autowired
	PaloAltoKeyGenService keygenService;
	
	
	@Autowired
	private RestTemplate restTemplate;

  @GetMapping("/getPaloAltoUser")
  public String getPaloAltoUser() {
   
	  System.out.println("Into getPaloAltoUser");
	  String uri="https://172.17.10.1/api/?type=config&action=get&xpath=/config/shared/local-user-database/user/entry&key="+keygenService.getPaloAltoKey();
	  System.out.println("Into Controller" +uri);
	  String response = restTemplate.getForObject(uri, String.class);
		//System.out.println("List of user \n"+response);
		String jsonResponse="";
		try {
		XmlMapper xmlMapper = new XmlMapper();
	    xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		Response responseXML = xmlMapper.readValue(response, Response.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		 jsonResponse = mapper.writeValueAsString(responseXML);
	} catch (JsonMappingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		System.out.println("List of user \n"+jsonResponse);
		return jsonResponse;
  }
  @PostMapping("/addPaloAltoUser")
  public String addPaloAltoUser(@RequestBody PaloAltoUser paloAltoUser) {
   
	  System.out.println("Into addPaloAltoUser");
	  String userName =paloAltoUser.getUsername();
	  String userPassword=paloAltoUser.getPassword();
	  System.out.println("user :::"+userName +"::::password:::"+userPassword);
	  
	  
	  
	  String passwordHashUri="https://172.17.10.1/api/?type=op&cmd=<request><password-hash><password>\"" +userPassword +"\"</password></password-hash></request>&key="+keygenService.getPaloAltoKey();
	  System.out.println("Into Controller passwordHashUri" +passwordHashUri);
	  String responsePwdHash = restTemplate.getForObject(passwordHashUri, String.class);
	  System.out.println("responsePwdHash"+responsePwdHash);
	  String pwdHash=deserializaPwdHash(responsePwdHash);
	  System.out.println("pwdHash"+pwdHash);
	  
	  
	  String uri="https://172.17.10.1/api/?type=config&action=set&xpath=/config/shared/local-user-database/user/entry[@name='"+userName+"']&element=<phash>"+pwdHash+"</phash>&key="+keygenService.getPaloAltoKey();
	  System.out.println("Into Controller addPaloAltoUser" +uri);
	  String response = restTemplate.getForObject(uri, String.class);
	  System.out.println("responsePwdHash"+response);
	  return createJSONMsg(response);
  }
  
  @PostMapping("/deletePaloAltoUser")
  public String deletePaloAltoUser(@RequestBody PaloAltoUser paloAltoUser) {
   
	  System.out.println("Into deletePaloAltoUser:::"+paloAltoUser.getUsername());
	String username=paloAltoUser.getUsername();
	  
	  String uri="https://172.17.10.1/api/?type=config&action=delete&xpath=/config/shared/local-user-database/user/entry[@name='"+username+"']&key="+keygenService.getPaloAltoKey();
	  System.out.println("Into Controller deletePaloAltoUser" +uri);
	  String response = restTemplate.getForObject(uri, String.class);
	  System.out.println("responsePwdHash"+response);
	  return createJSONMsg(response);

  }

  
  private String createJSONMsg(String response)
  
  {
		String jsonResponse="";
		  try {
			XmlMapper xmlMapper = new XmlMapper();
		    xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			Message responseXML = xmlMapper.readValue(response, Message.class);
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			 jsonResponse = mapper.writeValueAsString(responseXML);
		  	} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  return jsonResponse;
	  
  }
  private String deserializaPwdHash(String pwdHash)
  {

	    XmlMapper xmlMapper = new XmlMapper();
	    xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	    String pwd="";
		try {
			Response value = xmlMapper.readValue(pwdHash, Response.class);
			System.out.println(value);
			 pwd =value.getResult().getPhash();
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pwd;
	  
  }

  private String deserializaUserResp(String response)
  {

	    XmlMapper xmlMapper = new XmlMapper();
	    xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	    String msg="";
		try {
			Response value = xmlMapper.readValue(response, Response.class);
			System.out.println(value);
			 msg =value.getMsg();
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	  
  }
}