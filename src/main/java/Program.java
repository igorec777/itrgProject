import beansConfig.Config;
import connectorService.FlywayService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repository.hibernate.PersonRepository;
import repository.hibernate.RecordRepository;
import repository.hibernate.RoleRepository;
import repository.hibernate.ServiceRepository;


public class Program {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        FlywayService flywayService = context.getBean("flywayService", FlywayService.class);
        flywayService.migrate();
        PersonRepository personRepository = context.getBean("personRepositoryImpl", PersonRepository.class);
        RoleRepository roleRepository = context.getBean("roleRepositoryImpl", RoleRepository.class);
        RecordRepository recordRepository = context.getBean("recordRepositoryImpl", RecordRepository.class);
        ServiceRepository serviceRepository = context.getBean("serviceRepositoryImpl", ServiceRepository.class);

        personRepository.readAll();
        roleRepository.readAll();
        recordRepository.readAll();
        serviceRepository.readAll();

        flywayService.clean();
    }
}
