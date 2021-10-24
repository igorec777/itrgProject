package properties.queries;

public class RoleQ {
    public static final String INSERT_ROLE = "INSERT INTO role (name) VALUES (?);";
    public static final String SELECT_BY_ID = "SELECT * FROM role WHERE id = ?;";
    public static final String SELECT_ALL = "SELECT * FROM role;";
    public static final String UPDATE_BY_ID = "UPDATE role SET name = ? WHERE id = ?;";
    public static final String DELETE_BY_ID = "DELETE FROM role WHERE id = ?;";
}
