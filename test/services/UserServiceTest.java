/**
 * 
 */
/**
 * @author kidro
 *
 */
package services;

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
import models.enums;
import services.UserService;

@ContextConfiguration(classes = {AppConfig.class, TestDataConfig.class})
public class UserServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

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
    public void testLengthUsername () {
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
    public void nulltests () {
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
    public void nullUser() {
    	assertFalse("User cannot be null", userService.save(null));
    }
    @Test 
    public void duplicateUser() {
    	User t = new User();
    	t.setName("Username");
    	t.setPassword("password");
    	assertTrue("First save should work", userService.save(t));
    	assertFalse("Cannot save duplicate user", userService.save(t));
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
    
    @Test
    public void emptyListTest() {
    	assertTrue("List must be empty", userService.fetchAllUsers().size()==0);
    } 
    
    @Test
    public void OneItemListTest() {
    	User t = new User();
    	t.setName("ValidUser");
    	t.setPassword("myPass12");
    	userService.save(t);
    	assertTrue("List should have 1 item in it", userService.fetchAllUsers().size()==1);
    }
    
    @Test 
    public void multipleItemListTest() {
    	User t = new User();
    	t.setName("ValidUser");
    	t.setPassword("myPass12");
    	userService.save(t);
    	User t2 = new User();
    	t2.setName("ValidUser2");
    	t2.setPassword("myPass12");
    	userService.save(t2);
    	assertTrue("List should have more than 1 item in it", userService.fetchAllUsers().size()>1);
    }
    
    @Test 
    public void DuplicateUserListTest() {							//crappy test for the fetch all users method 
    	User t = new User();
    	t.setName("ValidUser");
    	t.setPassword("ValidPass12");
    	assertTrue("First save should work", userService.save(t));
    	assertFalse("This save should return false", userService.save(t)); //this save should fail
    	assertTrue("List should have 1 item in it", userService.fetchAllUsers().size()==1);
    }
    //
    @Test 
    public void loginSuccessTest () {
    	User t = new User();
    	t.setName("ValidUser");
    	t.setPassword("ValidPass12");
    	assertTrue("This save user should work", userService.save(t));
    	assertTrue("Login successfully", userService.login(t)==enums.LOGIN_CODE.SUCCESS);
    }
    
    @Test 
    public void loginFailUsernameTest () {
    	User t = new User();
    	t.setName("ValidUser");
    	t.setPassword("ValidPass12");
    	assertTrue("User doesn't exist --can't login", userService.login(t)==enums.LOGIN_CODE.NAME_FAIL);
    }
    
    @Test 
    public void loginFailNoUsernameTest () { //crappy test
    	User t = new User();
    	t.setName("ValudUser");
    	t.setPassword("ValidPass12");
    	userService.save(t);
    	
    	User user2 = new User();
    	user2.setName("");
    	user2.setPassword("ValidPass12");
    	assertTrue("No username--can't login", userService.login(user2)==enums.LOGIN_CODE.NAME_FAIL);
    }
    
    @Test 
    public void loginFailNullUsernameTest () { //crappy test
    	User t = new User();
    	t.setName("ValudUser");
    	t.setPassword("ValidPass12");
    	userService.save(t);
    	
    	User user2 = new User();
    	user2.setName(null);
    	user2.setPassword("ValidPass12");
    	assertTrue("No username--can't login", userService.login(user2)==enums.LOGIN_CODE.NAME_FAIL);
    }
    
    @Test 
    public void loginFailNoPasswordTest () { //crappy test
    	User t = new User();
    	t.setName("ValidUser");
    	t.setPassword("ValidPass12");
    	userService.save(t);
    	
    	User user2 = new User();
    	user2.setName("ValidUser");
    	user2.setPassword("");
    	assertTrue("No password--can't login", userService.login(user2)==enums.LOGIN_CODE.PASS_FAIL);
    }
    
    @Test 
    public void loginFailNullPasswordTest () { //crappy test
    	User t = new User();
    	t.setName("ValidUser");
    	t.setPassword("ValidPass12");
    	userService.save(t);
    	
    	User user2 = new User();
    	user2.setName("ValidUser");
    	user2.setPassword(null);
    	assertTrue("No password--can't login", userService.login(user2)==enums.LOGIN_CODE.PASS_FAIL);
    }
    
    @Test 
    public void nullLoginTest() {
    	assertTrue("User should fail because passed in null login", userService.login(null)==enums.LOGIN_CODE.NULL_FAIL);
    }
    
    @Test
    public void loginFailPasswordTest() {
    	User t = new User();
    	t.setName("ValidUser");
    	t.setPassword("ValidPass12");
    	userService.save(t);
    	
    	User user2 = new User();
    	user2.setName("ValidUser");
    	user2.setPassword("BadPass");
    	assertTrue("User password isn't correct", userService.login(user2)==enums.LOGIN_CODE.PASS_FAIL);
    }
    
    @Test
    public void getUserForName() {
    	User t = new User();
    	t.setName("ValidUser");
    	t.setPassword("ValidPass12");
    	userService.save(t);
    	assertTrue("User list is 1", userService.getUserForName(t.getName()).size()==1);
    }
    @Test
    public void emptyUserListTest() {
    	User t = new User();
    	t.setName("ValidUser");
    	assertTrue("List must be empty", userService.getUserForName(t.getName()).size()==0);
    } 
    @Test 
    public void multipleItemUserListTest() {
    	User t = new User();
    	t.setName("ValidUser");
    	t.setPassword("myPass12");
    	userService.save(t);
    	User t2 = new User();
    	t2.setName("ValidUser2");
    	t2.setPassword("myPass12");
    	userService.save(t2);
    	assertTrue("List should not have more than 1 item in it", userService.getUserForName(t2.getName()).size()==1);
    }
 }