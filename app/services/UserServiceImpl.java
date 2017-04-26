/**
 * File:    UserServiceImpl.java
 * Author:  rmoon
 * Date:    1/26/17
 */

package services;

import models.User;
import models.enums;
import play.data.Form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpa.UserForm;
import services.UserService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @PersistenceContext 
    private EntityManager em;  											//em means we're using hibernate--needed to persist to a database

    @Override
    @Transactional
    public Boolean save(User user) {
        try {      	
            log.debug("User save() was called.");
            if (user.getName() != null && user.getPassword() != null) { 					//as long as name and password aren't null ...
                List<User> userFromDB = getUserForName(user.getName());    
                //create User instance which = the User em finds based on the name of the passed in user instance
                if (userFromDB != null && userFromDB.size() > 0) {	//if that user exists and ... then cannot create
                    // user exists in database
                    log.debug("User exists in the database, save failed.");
                    System.out.println("User exists in the database");//error message
                    return false;
                } else {																	//passed in user does not exist 
                    if (user.getName().matches("^[a-zA-Z0-9._-]{3,}$") && user.getPassword().matches("^[A-Za-z0-9!@#$%^&*()_]{6,20}$")) {
                	// Add new user to the database
                	System.out.println("User is saved now");
                    log.debug("User added to database, save succeeded.");
                    em.persist(user);														//add/persist user to database
                    return true;
                    }
                    else {
                    	return false;
                    }
                }
            } else {
                log.debug("You cannot have null values. Please enter a username and password");							// name & pass were null
                return false;
            }
        } catch(Exception e) {
            log.debug("Exception caught in save(), save failed.");		
            System.out.println("Threw an exception statement");
            return false;
        }
    }

    @Override
    public enums.LOGIN_CODE login(User user) {
        log.debug("User login() was called.");
        User userFromDB = em.find(User.class, user.getName());
        if (userFromDB == null) {
            log.debug("User not found in database, login failed.");
            return enums.LOGIN_CODE.NAME_FAIL;
        } else if (!userFromDB.getPassword().equals(user.getPassword())) {
            log.debug("User password incorrect, login failed.");
            return enums.LOGIN_CODE.PASS_FAIL;
        } else {
            log.debug("User login success!");
            return enums.LOGIN_CODE.SUCCESS;
        }
    }
    
    @Override
    public List<User> fetchAllUsers() {
        return em.createQuery("from User", User.class).getResultList();
    }
    
    private List<User> getUserForName(String userName) {
    	return em.createQuery("FROM User u WHERE u.name = :setName", User.class)
		.setParameter("setName", userName)
		.getResultList();
    }
}