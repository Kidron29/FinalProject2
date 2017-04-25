/**
https://github.com/SocialFinance/userLogin_HelloWorld --he basically created this entire class
 */

package controllers;

import jpa.UserForm;
import javax.inject.Inject;
import javax.inject.Named;
import models.User;
import models.enums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import play.data.Form;
import play.mvc.Result;
import services.UserService;
import views.html.addUser;
import views.html.index;
import views.html.login;
import views.html.userContent;

@Named
public class CrudController extends play.mvc.Controller {

    private static final Logger log = LoggerFactory.getLogger(CrudController.class);

    @Inject private UserService userService;

    public Result addUser() {
        log.debug("addUser() called. Endpoint request.");

        Form<UserForm> form = Form.form(UserForm.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(addUser.render("User", form));
        }
        
        UserForm userForm = form.get();
        User user = new User(userForm.getName(), userForm.getPassword()); 					//create an instance of User w/ parameters of the typed getName and getPassword
        try {
            if (userService.save(user)) {    												//if userService.save returns true- it passed all the tests in the save method
                log.debug("addUser had a good request.");
                return ok(login.render("Added you as a new User, please log in", form));
            } else {																		//if userService.save returns false- it failed one of the tests in the save method
                log.debug("addUser had a bad request. Attempt to duplicate users or put null.");
                form.reject("Username Not Available.");
                return badRequest(addUser.render("Username Not Available", form));
            }
        } catch (DataIntegrityViolationException ex) {
            log.debug("addUser had a bad request. DataIntegrityViolationException caught.");
            return badRequest(addUser.render("Fail", form));
        }
    }

    public Result login() {

        log.debug("login() called. Endpoint requested.");

        Form<UserForm> form = Form.form(UserForm.class).bindFromRequest();
        if (form.hasErrors() || form.hasGlobalErrors()) {
            return badRequest(login.render("Form Error", form));
        } else {
            UserForm userForm = form.get();
            User user = new User(userForm.getName(), userForm.getPassword());
            try {
                enums.LOGIN_CODE loginAttempt = userService.login(user);
                switch (loginAttempt) {
                    case NAME_FAIL:
                        log.debug("login() had a bad request. User entered invalid username.");
                        // Name doesn't exist in database, prompt user to create account
                        return ok(addUser.render("Invalid Username", form));

                    case PASS_FAIL:
                        log.debug("login() had a bad request. User entered invalid password.");
                        // Incorrect password, prompt user to try again
                        return ok(login.render("Invalid Password", form));

                    case SUCCESS:
                        log.debug("login() had a good request.");
                        // successful authentication
                        return ok(userContent.render("Login Successful!", form));

                    default:
                        log.debug("login() failed. Fell through to default in switch-case statement.");
                        form.reject("It's Kaiser's fault.");
                        return badRequest(index.render("Login Failed", form));
                }
            } catch (DataIntegrityViolationException ex) {
                log.warn("login() exception: DataIntegrityViolationException caught! /n" + ex.getMessage());
                return badRequest(index.render("Login Error", form));
            }
        }
    }
}