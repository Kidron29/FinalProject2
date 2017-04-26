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
        userService.save(user2);
        assertFalse("Can't duplicate", userService.save(user2));
    }
}