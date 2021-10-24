package properties.queries;

public class PersonQ {
    public static final String SELECT_ALL = "SELECT * FROM person;";
    public static final String SELECT_BY_ID = "SELECT * FROM person WHERE id = ?;";
    public static final String INSERT_PERSON = "INSERT INTO person (name, surname, login, password) VALUES (?, ?, ?, ?);";
    public static final String DELETE_BY_ID = "DELETE FROM person WHERE id = ?;";
    public static final String UPDATE_BY_ID = "UPDATE person SET name = ? ,surname = ?, login = ?, password = ? WHERE id = ?;";
}
