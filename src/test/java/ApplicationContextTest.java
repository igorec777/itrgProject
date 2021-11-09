import beansConfig.BaseConfig;
import connectorService.FlywayService;
import converter.person.CreateUpdatePersonConverterImpl;
import converter.person.ReadPersonConverterImpl;
import converter.record.CreateUpdateRecordConverterImpl;
import converter.record.ReadRecordConverterImpl;
import converter.role.RoleConverterImpl;
import connectorService.SessionUtil;
import converter.service.ServiceConverterImpl;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import service.impl.PersonServiceImpl;
import service.impl.RecordServiceImpl;
import service.impl.RoleServiceImpl;
import service.impl.ServiceManagementServiceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BaseConfig.class)
public class ApplicationContextTest {
    @Autowired
    private ApplicationContext context;

    @Test
    void givenImportedServiceBeans_whenGettingEach_shallFindIt() {
        assertThatBeanExists(uncapitalize(PersonServiceImpl.class.getSimpleName()), PersonServiceImpl.class);
        assertThatBeanExists(uncapitalize(RoleServiceImpl.class.getSimpleName()), RoleServiceImpl.class);
        assertThatBeanExists(uncapitalize(ServiceManagementServiceImpl.class.getSimpleName()), ServiceManagementServiceImpl.class);
        assertThatBeanExists(uncapitalize(RecordServiceImpl.class.getSimpleName()), RecordServiceImpl.class);
    }

    @Test
    void givenImportedConnectorBeans_whenGettingEach_shallFindIt() {
        assertThatBeanExists(uncapitalize(BaseConfig.class.getSimpleName()), BaseConfig.class);
        assertThatBeanExists(uncapitalize(FlywayService.class.getSimpleName()), FlywayService.class);
        assertThatBeanExists(uncapitalize(Flyway.class.getSimpleName()), Flyway.class);
        assertThatBeanExists(uncapitalize(SessionFactory.class.getSimpleName()), SessionFactory.class);
        assertThatBeanExists(uncapitalize(SessionUtil.class.getSimpleName()), SessionUtil.class);
    }

    @Test
    void givenImportedConverterBeans_whenGettingEach_shallFindIt() {
        assertThatBeanExists(uncapitalize(ReadPersonConverterImpl.class.getSimpleName()), ReadPersonConverterImpl.class);
        assertThatBeanExists(uncapitalize(CreateUpdatePersonConverterImpl.class.getSimpleName()),
                CreateUpdatePersonConverterImpl.class);

        assertThatBeanExists(uncapitalize(RoleConverterImpl.class.getSimpleName()), RoleConverterImpl.class);

        assertThatBeanExists(uncapitalize(ServiceConverterImpl.class.getSimpleName()), ServiceConverterImpl.class);

        assertThatBeanExists(uncapitalize(ReadRecordConverterImpl.class.getSimpleName()), ReadRecordConverterImpl.class);
        assertThatBeanExists(uncapitalize(CreateUpdateRecordConverterImpl.class.getSimpleName()),
                CreateUpdateRecordConverterImpl.class);
    }

    private void assertThatBeanExists(String beanName, Class<?> beanClass) {
        assertTrue(context.containsBean(beanName));
        assertNotNull(context.getBean(beanClass));
    }

    private String uncapitalize(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
}
