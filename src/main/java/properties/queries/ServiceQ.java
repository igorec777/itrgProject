package properties.queries;

public class ServiceQ {
    public static final String SELECT_ALL = "SELECT * FROM service;";
    public static final String SELECT_BY_ID = "SELECT * FROM service WHERE id = ?;";
    public static final String INSERT_SERVICE = "INSERT INTO service (name, price) VALUES (?, ?);";
    public static final String DELETE_BY_ID = "DELETE FROM service WHERE id = ?;";
    public static final String UPDATE_BY_ID = "UPDATE service SET name = ?, price = ? WHERE id = ?;";
}
