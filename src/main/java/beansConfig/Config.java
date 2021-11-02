package beansConfig;

import connectorService.HibernateService;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static properties.DataSourceParams.*;
import static properties.DataSourceParams.MIGRATIONS_LOCATION;

@Configuration
@ComponentScan({"connectorService", "repository.hibernate", "repository.hibernate.impl"})
public class Config {

    @Bean
    Flyway flyway() {
        return Flyway.configure()
                .dataSource(URL, USER, PASSWORD)
                .locations(MIGRATIONS_LOCATION)
                .load();
    }

    @Bean
    SessionFactory sessionFactory() {
        return HibernateService.getSessionFactory();
    }
}
