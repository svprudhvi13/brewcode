package in.brewcode.test.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class ClientRegistrationControllerTest {
	private static String BASE_APP_URI="http://localhost:8080/brewcode";
	private static Logger logger = Logger
			.getLogger(ClientRegistrationControllerTest.class);
	private static String CLIENT_ID = "brewcode";
	private static String CLIENT_SECRET="secret";
	@Test
	@Ignore
	public void whenClientRegisters_NoError() throws JsonProcessingException, URISyntaxException{
		ObjectMapper mapper = new ObjectMapper();
		BaseClientDetails cd = new BaseClientDetails();
		cd.setClientId(CLIENT_ID);
		cd.setClientSecret(CLIENT_SECRET);
		cd.setScope(Lists.newArrayList("ADMIN",
		"USER", 
		"CLIENT"));
		cd.setAuthorizedGrantTypes(Lists.newArrayList("admin_app", "client"));
		String jsonInput = mapper.writeValueAsString(cd);
		
		
		Response response = RestAssured.post(new URI(BASE_APP_URI+"/client/register"));
	}
}
