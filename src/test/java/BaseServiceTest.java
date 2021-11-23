import app.Application;
import app.service.impl.PersonServiceImpl;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class})
public class BaseServiceTest {

    @Autowired
    private ApplicationContext context;

    @BeforeEach
    public void initDB() {
        context.getBean(Flyway.class).migrate();
    }

    @AfterEach
    public void cleanDB() {
        context.getBean(Flyway.class).clean();
    }
}
