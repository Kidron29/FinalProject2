/**
 * File:    UserServiceImpl.java
 * Author:  rmoon
 * Date:    1/26/17
 */

package services.impl;

import models.User;
import models.enums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import services.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @PersistenceContext private EntityManager em;

    @Override
    @Transactional
    public Boolean save(User user) {
        try {
            log.debug("User save() was called.");
            if (user.getName() != null && user.getPassword() != null) {
                User userFromDB = em.find(User.class, user.getName());
                if (userFromDB != null && userFromDB.getName().equals(user.getName())) {
                    // user exists in database
                    log.debug("User exists in the database, save failed.");
                    return false;
                } else {
                    // Add new user to the database
                    log.debug("User added to database, save succeeded.");
                    em.persist(user);
                    return true;
                }
            } else {
                log.debug("Form values were null, save failed.");
                return false;
            }
        } catch(Exception e) {
            log.debug("Exception caught in save(), save failed.");
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
}