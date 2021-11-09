package beansConfig;

import connectorService.HibernateUtil;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;


@PropertySource(value = "classpath:application.properties")
@ComponentScans({@ComponentScan({"connectorService"}),
                @ComponentScan({"dao", "dao.impl", "service", "service.impl"}),
                @ComponentScan({"converter"})})
@Configuration
public class BaseConfig {

    @Bean
    Flyway flyway(@Value("${db.url}") String url,
                  @Value("${db.user}") String user,
                  @Value("${db.password}") String password,
                  @Value("${flyway.migrations_location}") String migrationsLocation) {
        return Flyway.configure()
                .dataSource(url, user, password)
                .locations(migrationsLocation)
                .load();
    }

    @Bean
    SessionFactory sessionFactory() {
        return HibernateUtil.getSessionFactory();
    }

//    @Bean
//    public ModelMapper getMapper() {
//        return new ModelMapper();
//    }
}
