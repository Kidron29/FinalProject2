package models;

import play.data.validation.Constraints.Required;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //means we are using Spring to work w/ database
@Table(name = "user")
public class User {

	//fields in our table are name and password--need to be the same as the fields in UserForm
    @Id
    @Column(name = "name", nullable = false, unique = true)
    @Required
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    public User() {

    }

    public User(String name, String password) {
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
}
