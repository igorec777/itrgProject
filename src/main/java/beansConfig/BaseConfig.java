package beansConfig;

import connectorService.HibernateUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import javax.annotation.PostConstruct;


@EnableAspectJAutoProxy
@PropertySource(value = "classpath:properties/application.properties")
@ComponentScans({@ComponentScan({"connectorService"}),
        @ComponentScan({"dao", "dao.impl"}),
        @ComponentScan({"service", "service.impl"}),
        @ComponentScan({"converter"}),
        @ComponentScan({"aspect"})})
@Configuration
public class BaseConfig {

    @Value("${spring.profiles.active}")
    private String activeProfile;
    
    @PostConstruct
    void initLogLevel() {
        if (activeProfile.equals("dev")) {
            Configurator.setRootLevel(Level.valueOf("WARN"));
        }
        else if (activeProfile.equals("prod")) {
            Configurator.setRootLevel(Level.valueOf("ERROR"));
        }
    }

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
}
