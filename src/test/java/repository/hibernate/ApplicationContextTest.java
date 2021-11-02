package repository.hibernate;

import beansConfig.Config;
import connectorService.FlywayService;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
public class ApplicationContextTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void givenImportedBeans_whenGettingEach_shallFindIt() {
        assertThatBeanExists("config", Config.class);
        assertThatBeanExists("flywayService", FlywayService.class);
        assertThatBeanExists("flyway", Flyway.class);
        assertThatBeanExists("sessionFactory", Flyway.class);

        assertThatBeanExists("personRepositoryImpl", Flyway.class);
        assertThatBeanExists("roleRepositoryImpl", Flyway.class);
        assertThatBeanExists("recordRepositoryImpl", Flyway.class);
        assertThatBeanExists("serviceRepositoryImpl", Flyway.class);

    }

    private void assertThatBeanExists(String beanName, Class<?> beanClass) {
        assertTrue(context.containsBean(beanName));
        assertNotNull(context.getBean(beanClass));
    }

}
