package repository.hibernate;

import entity.hibernate.Role;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository {
    Set<Role> readAll();

    Role readByName(String name);

    Role create(Role role);

    void deleteById(Long id);

    void updateById(Long id, Role role);
}
