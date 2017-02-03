package in.brewcode.test.config;

import in.brewcode.api.config.PersistenceConfig;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={
PersistenceConfig.class
}, loader= AnnotationConfigContextLoader.class )
public class PersistenceConfigTest {

	@Test
	@Ignore
	public void testApplicationConfig(){
		
	}
	
}
