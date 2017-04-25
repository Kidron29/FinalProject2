package controllers;

import services.UserService;
import views.html.index;
import views.html.addUser;
import play.mvc.Controller;
import play.mvc.Result;
import javax.inject.Inject;
import javax.inject.Named;
import views.html.userContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.userContent;

@Named
public class Application extends Controller {

    @Inject
    private UserService taskPersist;
    
    private static final Logger log = LoggerFactory.getLogger(Application.class);


    public static Result index() {
        log.debug("index() endpoint requested.");
        return ok(index.render("Welcome!", play.data.Form.form(jpa.UserForm.class)));
    }
    
    public Result userContent() {
    	log.debug("userContent() endpoint requested");
    	return ok(userContent.render("Hello, World: User Login", play.data.Form.form(jpa.UserForm.class)));
    }
    
    public Result addUser2() {
    	log.debug("addUser() endpoint requested");
    	return ok(addUser.render("User", play.data.Form.form(jpa.UserForm.class)));
    }
}
