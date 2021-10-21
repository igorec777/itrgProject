package properties;

public class Queries {
    public static final String SELECT_ALL = "SELECT * FROM user;";
    public static final String SELECT_BY_ID = "SELECT * FROM user WHERE id = ?;";
    public static final String INSERT_ROW = "INSERT INTO user (firstName, lastName, login, passwordHash) VALUES (?, ?, ?, ?);";
    public static final String DELETE_ALL = "DELETE FROM user;";
    public static final String DELETE_BY_ID = "DELETE FROM user WHERE id = ?;";
}
