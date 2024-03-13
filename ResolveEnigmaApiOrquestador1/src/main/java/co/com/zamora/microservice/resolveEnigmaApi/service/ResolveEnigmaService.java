package co.com.zamora.microservice.resolveEnigmaApi.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ResolveEnigmaService {
public String Orquestador(){
		
		RestTemplate restTemplate1=new RestTemplate(); 
		RestTemplate restTemplate2=new RestTemplate();
		RestTemplate restTemplate3=new RestTemplate(); 
		String fooResourceUrl1="http://localhost:8080/v1/getOneEnigma/mensaje-step1";
		String fooResourceUrl2="http://localhost:8081/v1/getOneEnigma/mensaje-step2";
		String fooResourceUrl3="http://localhost:8082/v1/getOneEnigma/mensaje-step3";
		ResponseEntity<String> response1 = restTemplate1.getForEntity(fooResourceUrl1, String.class);
		ResponseEntity<String> response2 = restTemplate2.getForEntity(fooResourceUrl2, String.class);
		ResponseEntity<String> response3 = restTemplate3.getForEntity(fooResourceUrl3, String.class);
		
		String body1 = response1.getBody();
		String body2 = response2.getBody();
		String body3 = response3.getBody();
		
		String concat = body1.concat(body2).concat(body3);
		
		return concat;
	}

}
