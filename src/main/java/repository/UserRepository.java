package repository;

import model.User;

import java.util.List;

public interface UserRepository {

    List<User> readAll();
    User readById(int id);
    User create(User user);
    List<User> createAll(List<User> users);
    int deleteAll();
    int deleteById(int id);
}
