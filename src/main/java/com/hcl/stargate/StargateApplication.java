package com.hcl.stargate;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class StargateApplication {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/stargate");
		SpringApplication.run(StargateApplication.class, args);
		System.out.println("I am legend");
	}
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	   // Do any additional configuration here
		ignoreCertificates();
	   return builder.build();
	}
    private void ignoreCertificates() {
    	   try
    	    {
    	        // Create a trust manager that does not validate certificate chains
    	        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
    	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
    	                return null;
    	            }
    	            public void checkClientTrusted(X509Certificate[] certs, String authType) {
    	            }
    	            public void checkServerTrusted(X509Certificate[] certs, String authType) {
    	            }
    	        }
    	        };

    	        // Install the all-trusting trust manager
    	        SSLContext sc = SSLContext.getInstance("SSL");
    	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
    	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

    	        // Create all-trusting host name verifier
    	        HostnameVerifier allHostsValid = new HostnameVerifier() {
    	            public boolean verify(String hostname, SSLSession session) {
    	                return true;
    	            }
    	        };

    	        // Install the all-trusting host verifier
    	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    	    } catch (NoSuchAlgorithmException e) {
    	        e.printStackTrace();
    	    } catch (KeyManagementException e) {
    	        e.printStackTrace();
    	    }
    }
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/getPaloAltoUser").allowedOrigins("http://localhost:4200");
			}
		};
	}
}
