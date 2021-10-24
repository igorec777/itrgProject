package repository.impl;

import entity.Role;
import repository.RoleRepository;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static properties.columnNames.Names.ID_COLUMN;
import static properties.columnNames.Names.NAME_COLUMN;
import static properties.queries.PersonHasRoleQ.DELETE_ROLE_AND_ITS_PEOPLE;
import static properties.queries.RoleQ.*;

public class RoleRepositoryImpl implements RoleRepository {

    DataSource dataSource;

    public RoleRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Role create(Role role) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(INSERT_ROLE, Statement.RETURN_GENERATED_KEYS)) {

            stm.setString(1, role.getName());
            stm.executeUpdate();

            try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return readById(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Role readById(int id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(SELECT_BY_ID)) {

            stm.setInt(1, id);
            ResultSet resultSet = stm.executeQuery();

            if (resultSet.next()) {
                return (new Role(
                        resultSet.getInt(ID_COLUMN),
                        resultSet.getString(NAME_COLUMN)
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Role> readAll() {
        List<Role> roles = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement()) {

            ResultSet resultSet = stm.executeQuery(SELECT_ALL);

            while (resultSet.next()) {
                roles.add(new Role(
                        resultSet.getInt(ID_COLUMN),
                        resultSet.getString(NAME_COLUMN)
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return roles;
    }

    @Override
    public int updateById(int id, Role role) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(UPDATE_BY_ID)) {

            stm.setString(1, role.getName());
            stm.setInt(2, id);
            return stm.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteById(int id) {
        deleteFromPersonHasRoleByRoleId(id);

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(DELETE_BY_ID)) {

            stm.setInt(1, id);
            return stm.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    private void deleteFromPersonHasRoleByRoleId(int id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(DELETE_ROLE_AND_ITS_PEOPLE)) {

            stm.setInt(1, id);
            stm.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
