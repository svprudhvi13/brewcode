package in.brewcode.test.controller;

import in.brewcode.api.config.PersistenceConfig;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={
PersistenceConfig.class
}, loader= AnnotationConfigContextLoader.class )
public class AdminControllerTest {
	
	private MockMvc mockMvc;
	
	/*
	@Before
	public void mockMvcSetup(){
		//this.mockMvc=MockMvcBuilders.webAppContextSetup(ctx).build();
	}*/
	
	@Test
	@Ignore
	public void createAuthorTest(){
		//mockMvc.perform();
	}
}
