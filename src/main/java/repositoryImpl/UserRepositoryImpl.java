package repositoryImpl;

import model.User;
import repository.UserRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import static properties.Queries.*;


public class UserRepositoryImpl implements UserRepository {


    private final String ID_COLUMN = "Id";
    private final String FIRSTNAME_COLUMN = "firstName";
    private final String LASTNAME_COLUMN = "lastName";
    private final String LOGIN_COLUMN = "login";
    private final String PASSWORD_HASH_COLUMN = "passwordHash";

    public final DataSource dataSource;

    public UserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> readAll() {
        List<User> users = new ArrayList<>();
        try(Connection conn = dataSource.getConnection();
            Statement stm = conn.createStatement()) {

            ResultSet resultSet = stm.executeQuery(SELECT_ALL);

            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt(ID_COLUMN),
                        resultSet.getString(FIRSTNAME_COLUMN),
                        resultSet.getString(LASTNAME_COLUMN),
                        resultSet.getString(LOGIN_COLUMN),
                        resultSet.getString(PASSWORD_HASH_COLUMN)
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    @Override
    public User readById(int id) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stm = conn.prepareStatement(SELECT_BY_ID)) {

            stm.setInt(1, id);
            ResultSet resultSet = stm.executeQuery();

            if (resultSet.next()) {
                return (new User(
                        resultSet.getInt(ID_COLUMN),
                        resultSet.getString(FIRSTNAME_COLUMN),
                        resultSet.getString(LASTNAME_COLUMN),
                        resultSet.getString(LOGIN_COLUMN),
                        resultSet.getString(PASSWORD_HASH_COLUMN)
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public User create(User user) {
        return insert(user);
    }

    @Override
    public List<User> createAll(List<User> users) {
        List<User> result = new ArrayList<>();
        try {
            for (User u : users) {
                result.add(insert(u));
            }
        } catch (NullPointerException ex) {
            System.out.println("users is null");
        }
        return result;
    }

    @Override
    public int deleteAll() {
        try(Connection conn = dataSource.getConnection();
            Statement stm = conn.createStatement()) {
            return stm.executeUpdate(DELETE_ALL);

        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteById(int id) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stm = conn.prepareStatement(DELETE_BY_ID)) {

            stm.setInt(1, id);
            return stm.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }



    private User insert(User user) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stm = conn.prepareStatement(INSERT_ROW, Statement.RETURN_GENERATED_KEYS)) {

            stm.setString(1, user.getFirstName());
            stm.setString(2, user.getLastName());
            stm.setString(3, user.getLogin());
            stm.setString(4, user.getPasswordHash());

            final int affectedRows = stm.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Warning: 0 inserted rows");
            }
            else {
                try(ResultSet generatedKeys = stm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        //FIXME why it doesn't work?
                        //int id = generatedKeys.getInt(ID_COLUMN);
                        int id = generatedKeys.getInt(1);
                        return readById(id);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
