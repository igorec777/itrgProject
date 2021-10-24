package properties.queries;

public class PersonHasRoleQ {
    public static final String INSERT_PERSON_AND_ROLE = "INSERT INTO person_has_role (personId, roleId) VALUES (?, ?);";
    public static final String DELETE_PERSON_AND_HIS_ROLES = "DELETE FROM person_has_role WHERE personId = ?;";
    public static final String DELETE_ROLE_AND_ITS_PEOPLE = "DELETE FROM person_has_role WHERE roleId = ?;";
}
