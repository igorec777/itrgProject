package repository;

import entity.Role;

import java.util.List;

public interface RoleRepository {

    Role create(Role role);

    Role readById(int id);

    List<Role> readAll();

    int updateById(int id, Role role);

    int deleteById(int id);

}
