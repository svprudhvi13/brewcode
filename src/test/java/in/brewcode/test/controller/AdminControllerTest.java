package in.brewcode.test.controller;

import in.brewcode.api.config.PersistenceConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.RequestBuilder.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={
PersistenceConfig.class
}, loader= AnnotationConfigContextLoader.class )
@WebAppConfiguration
public class AdminControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext ctx;
	
	@Before
	public void mockMvcSetup(){
		this.mockMvc=MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	public void createAuthorTest(){
		//mockMvc.perform();
	}
}
