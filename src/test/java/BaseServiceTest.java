import connectorService.FlywayService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;


public class BaseServiceTest extends ApplicationContextTest{

    private FlywayService flywayService;

    @Autowired
    public void setFlywayService(FlywayService flywayService) {
        this.flywayService = flywayService;
    }

    @BeforeEach
    public void initDB() {
        flywayService.migrate();
    }

    @AfterEach
    public void cleanDB() {
        flywayService.clean();
    }
}
