package properties.queries;

public class RecordQ {
    public static final String INSERT_RECORD = "INSERT INTO record (date, startTime, endTime, clientId, workerId, serviceId)" +
            "VALUES (?, ?, ?, ?, ?, ?);";
    public static final String SELECT_BY_ID = "SELECT * FROM record WHERE id = ?;";
    public static final String SELECT_ALL = "SELECT * FROM record;";
    public static final String DELETE_BY_ID = "DELETE FROM record WHERE id = ?;";
    public static final String DELETE_BY_PERSON_ID = "DELETE FROM record WHERE clientId = ? OR workerId = ?;";
    public static final String DELETE_BY_SERVICE_ID = "DELETE FROM record WHERE serviceId = ?;";
    public static final String UPDATE_BY_ID = "UPDATE record SET date = ?, startTime = ?, endTime = ?, clientId = ?, " +
            "workerId = ?, serviceId = ? WHERE id = ?;";
}
