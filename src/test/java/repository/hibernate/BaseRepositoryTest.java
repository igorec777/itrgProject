package repository.hibernate;

import beansConfig.Config;
import connectorService.FlywayService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
public class BaseRepositoryTest {

    @Autowired
    protected PersonRepository personRepository;
    @Autowired
    protected RoleRepository roleRepository;
    @Autowired
    protected RecordRepository recordRepository;
    @Autowired
    protected ServiceRepository serviceRepository;

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
