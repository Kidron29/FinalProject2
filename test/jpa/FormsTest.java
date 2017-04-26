/**
 * 
 */
/**
 * @author kidro
 *
 */
package jpa;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;

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

	@Inject
	private UserService userService;
	
    @Test
    public void testDuplicateUserName () {
    	User t = new User();
        t.setName("contents");
        t.setPassword("password");
        userService.save(t);
        
        User user2 = new User();
        user2.setName("contents");
        user2.setPassword("validPass");
        assertFalse("Can't duplicate", userService.save(user2));
    }
    
    @Test
    public void testGoodUsername () {
    	User t = new User();
    	t.setName("JJ");
    	t.setPassword("password");
    	assertFalse("User must have a name longer then 3 characters", userService.save(t));
    	}
    
    @Test
    public void testlengthofpassword () {
    	User t = new User();
    	t.setName("Username");
    	t.setPassword("passw");
    	assertFalse("Password must be longer then 6 characters", userService.save(t));
    	}
    @Test
    public void nonulltests () {
    	User t = new User();
    	t.setName(null);
    	t.setPassword(null);
    	assertFalse("User and Password fields cannot be null", userService.save(t));
    	}
    @Test
    public void nullusername () {
    	User t = new User();
    	t.setName(null);
    	t.setPassword("password");
    	assertFalse("User cannot be null", userService.save(t));
    	}
    @Test
    public void nullpassword () {
    	User t = new User();
    	t.setName("Username");
    	t.setPassword("null");
    	assertFalse("Password cannot be null", userService.save(t));
    	}
    @Test
    public void specialcharacterpassword () {
    	User t = new User();
    	t.setName("Username");
    	t.setPassword("?????????????");
    	assertFalse("User cannot be null", userService.save(t));
    	}
    @Test
    public void saveuser () {
    	User t = new User();
    	t.setName("ValidUser");
    	t.setPassword("myPass12");
    	assertTrue("User saved successfully to database", userService.save(t));
    	
    }
 }