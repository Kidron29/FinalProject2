package jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controllers.CrudController;
import play.data.validation.Constraints.Required;
import play.data.validation.Constraints.Pattern;
// Basically the same class Nate's TaskForm except we added some more fields. https://github.com/YogoGit     https://github.com/SocialFinance/userLogin_HelloWorld 
public class UserForm  {
	
	private static final Logger log = LoggerFactory.getLogger(UserForm.class);
	
    @Required
    @Pattern(value="^[a-zA-Z0-9._-]{3,}$", message="Name must be at least 3 characters in length, and can only contain [a-zA-Z0-9._-].")
    private String name;

    @Required
    @Pattern(value="^[A-Za-z0-9!@#$%^&*()_]{6,20}$", message="Password must be 6-20 characters in length, and only contain [A-Za-z0-9!@#$%^&*()_].")
    private String password;

    public UserForm() { }

    public UserForm(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String validateOff() {

        if (!name.matches("^[a-zA-Z0-9._-]{3,}$")) {
            return "Name must be at least 3 characters in length, and can only contain [a-zA-Z0-9._-].";
        }
        if (!password.matches("^[A-Za-z0-9!@#$%^&*()_]{6,20}$")) {
            return "Password must be 6-20 characters in length, and only contain [A-Za-z0-9!@#$%^&*()_].";
        }
        return null;
    }

    @Override
    public String toString() {
        return "User{name=" + name + ", password=" + password + "}";
    }
}