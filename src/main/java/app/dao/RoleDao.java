package app.dao;

import app.entity.Role;
import app.exception.RowNotFoundException;

import java.util.Set;

public interface RoleDao {
    Role create(Role role);

    Role findById(Long id) throws RowNotFoundException;

    Set<Role> findAll();

    void delete(Role role);
}
