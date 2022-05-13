package com.hcl.stargate.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.hcl.stargate.bean.Response;

@Service
public class PaloAltoKeyGenService {
	
	@Autowired
	private RestTemplate restTemplate;
			
	public String getPaloAltoKey()
	{
		System.out.println("Into kegen");
		String url = "https://172.17.10.1/api/?type=keygen&user=admin&password=Paloalto@123";
		String response = restTemplate.getForObject(url, String.class);
	    XmlMapper xmlMapper = new XmlMapper();
	    xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	    String key="";
		try {
			Response value = xmlMapper.readValue(response, Response.class);
			System.out.println(value);
			 key =value.getResult().getKey();
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return key;
	}

}
