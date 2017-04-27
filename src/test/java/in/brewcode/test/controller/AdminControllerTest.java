package in.brewcode.test.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
*/
public class AdminControllerTest {

	
	private static Logger logger = Logger.getLogger(AdminControllerTest.class);
	private static String BASE_APP_URI="http://localhost:8080/brewcode";

/*	@Before
	public void mockMvcSetup() {
		 this.mockMvc=MockMvcBuilders.webAppContextSetup(ctx).build();
	}
*/
	@Test
	public void getUserJson_NoError() throws URISyntaxException {
		Response response = RestAssured.given().accept(ContentType.JSON).get(new URI(BASE_APP_URI+"/user/json"));
		logger.debug("Reponse body "+ response.asString());
		Assert.isTrue(response.getStatusCode()==200);
		
	}
	
	@Test
	public void registerClient_NoError() throws URISyntaxException{
		Response response = RestAssured.get(new URI("http://localhost:8080/brewcode/oauth/token?grant_type=password&username=scholes18&password=password"));
		logger.debug("Reponse body "+ response.asString());
	System.out.println("Reponse body "+ response.asString());
	
	}
}
