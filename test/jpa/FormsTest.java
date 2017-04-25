/**
 * 
 */
/**
 * @author kidro
 *
 */
package jpa;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import conf.AppConfig;
import conf.TestDataConfig;
import models.User;
import services.UserService;

@ContextConfiguration(classes = {AppConfig.class, TestDataConfig.class})
public class FormsTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private UserService userService;
	
	
    @Test
    public void testDuplicateUserName () {
    	User user = new User();
    	user.setName("User w/o password");
    	user.setPassword("ValidPass");
    	userService.save(user);
    	
    	User user2 = new User();
    	user2.setName("User w/o password");
    	user2.setPassword("ValidPass2");
    	try{
    		userService.save(user2);
    		fail();
    	} catch (javax.persistence.PersistenceException ignored) {
    		
    	}
    }

}