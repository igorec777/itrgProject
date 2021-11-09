package dao;

import entity.Role;
import exceptions.RowNotFoundException;

import java.util.Set;

public interface RoleDao {
    Role create(Role role);

    Role findById(Long id) throws RowNotFoundException;

    Set<Role> findAll();

    void update(Role role);

    void delete(Role role);
}
