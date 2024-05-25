package com.spring.rest.webservices.restful_web_services.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    public static int usersCount = 0;

    static {
        users.add(new User(++usersCount, "Michael", LocalDate.now().minusYears(30)));
        users.add(new User(++usersCount, "Dwight", LocalDate.now().minusYears(25)));
        users.add(new User(++usersCount, "Jim", LocalDate.now().minusYears(20)));
    }

    /***
     * Finds all the users
     * @return All users
     */
    public List<User> findAll() {
        return users;
    }

    /***
     * Finds a user with a specific id
     * @param id
     * @return User
     */
    public User findUser(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().get();
    }

    /***
     * Saves a user
     * @param user
     * @return user
     */
    public User save(User user) {
        user.setId(++usersCount);
        users.add(user);
        return user;
    }

}
