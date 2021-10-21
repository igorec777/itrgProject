package properties;

public class DataSourceParams {
    public static final String URL = "jdbc:h2:mem:default;DB_CLOSE_DELAY=-1";
    public static final String USER = "root";
    public static final String PASSWORD = "1111";
    public static final String MIGRATIONS_LOCATION = "classpath:/db/migration";
}
