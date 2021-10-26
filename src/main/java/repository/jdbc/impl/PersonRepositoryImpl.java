package repository.jdbc.impl;

import entity.jdbc.Person;
import entity.jdbc.Role;
import repository.jdbc.PersonRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static properties.columnNames.Names.*;
import static properties.queries.PersonHasRoleQ.DELETE_PERSON_AND_HIS_ROLES;
import static properties.queries.PersonHasRoleQ.INSERT_PERSON_AND_ROLE;
import static properties.queries.PersonQ.*;

public class PersonRepositoryImpl implements PersonRepository {


    private final DataSource dataSource;

    public PersonRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Person> readAll() {
        List<Person> people = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stm = conn.createStatement()) {

            ResultSet resultSet = stm.executeQuery(SELECT_ALL);

            while (resultSet.next()) {
                people.add(new Person(
                        resultSet.getInt(ID_COLUMN),
                        resultSet.getString(NAME_COLUMN),
                        resultSet.getString(SURNAME_COLUMN),
                        resultSet.getString(LOGIN_COLUMN),
                        resultSet.getString(PASSWORD_COLUMN)
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return people;
    }

    @Override
    public Person readById(int id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(SELECT_BY_ID)) {

            stm.setInt(1, id);
            ResultSet resultSet = stm.executeQuery();

            if (resultSet.next()) {
                return (new Person(
                        resultSet.getInt(ID_COLUMN),
                        resultSet.getString(NAME_COLUMN),
                        resultSet.getString(SURNAME_COLUMN),
                        resultSet.getString(LOGIN_COLUMN),
                        resultSet.getString(PASSWORD_COLUMN)
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateById(int id, Person person) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(UPDATE_BY_ID)) {

            fillStm(stm, person);
            stm.setInt(5, id);
            return stm.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public Person create(Person person, List<Role> roles) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(INSERT_PERSON, Statement.RETURN_GENERATED_KEYS)) {

            fillStm(stm, person);
            stm.executeUpdate();

            try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    //FIXME why it doesn't work?
                    //int id = generatedKeys.getInt(ID_COLUMN);
                    int id = generatedKeys.getInt(1);

                    createPersonHasRoleByPersonId(id, roles);
                    return readById(id);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public int deleteById(int id) {

        deleteFromPersonHasRoleByPersonId(id);
        new RecordRepositoryImpl(dataSource).deleteByPersonId(id);

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(DELETE_BY_ID)) {

            stm.setInt(1, id);
            return stm.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    private void fillStm(PreparedStatement stm, Person person) throws SQLException {
        stm.setString(1, person.getName());
        stm.setString(2, person.getSurname());
        stm.setString(3, person.getLogin());
        stm.setString(4, person.getPassword());
    }

    private void createPersonHasRoleByPersonId(int id, List<Role> roles) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(INSERT_PERSON_AND_ROLE)) {

            roles.stream()
                    .flatMap(r -> Stream.of(r.getId()))
                    .forEach(roleId -> {
                        try {
                            stm.setInt(1, id);
                            stm.setInt(2, roleId);
                            stm.executeUpdate();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    });
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteFromPersonHasRoleByPersonId(int id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stm = conn.prepareStatement(DELETE_PERSON_AND_HIS_ROLES)) {

            stm.setInt(1, id);
            stm.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}

