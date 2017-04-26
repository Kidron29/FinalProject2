/**
 * File:    UserService.java
 * Author:  rmoon
 * Date:    1/26/17
 */

package services;

import java.util.List;

import models.User;
import models.enums;

public interface UserService {


    /**
     *
     * @param user
     * @return
     */
    Boolean save(User user);

    /**
     *
     * @param user
     * @return
     */
    enums.LOGIN_CODE login(User user);

    List<User> fetchAllUsers();
    
}