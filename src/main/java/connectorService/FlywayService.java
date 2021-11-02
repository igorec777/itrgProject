package connectorService;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FlywayService {

    private final Flyway flyway;

    @Autowired
    FlywayService(Flyway flyway) {
        this.flyway = flyway;
    }

    public void migrate() {
        flyway.migrate();
    }

    public void clean() {
        flyway.clean();
    }
}
