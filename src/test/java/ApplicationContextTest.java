import app.Application;
import app.connectorService.SessionUtil;
import app.converter.person.PersonConverter;
import app.converter.record.RecordConverter;
import app.converter.role.RoleConverter;
import app.converter.role.RoleConverterImpl;
import app.converter.service.ServiceConverter;
import app.converter.service.ServiceConverterImpl;
import app.service.impl.PersonServiceImpl;
import app.service.impl.RecordServiceImpl;
import app.service.impl.RoleServiceImpl;
import app.service.impl.ServiceManagementServiceImpl;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Application.class)
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
        assertThatBeanExists(uncapitalize(Flyway.class.getSimpleName()), Flyway.class);
        assertThatBeanExists(uncapitalize(SessionFactory.class.getSimpleName()), SessionFactory.class);
        assertThatBeanExists(uncapitalize(SessionUtil.class.getSimpleName()), SessionUtil.class);
    }

    @Test
    void givenImportedConverterBeans_whenGettingEach_shallFindIt() {
        assertThatBeanExists(uncapitalize(PersonConverter.class.getSimpleName()), PersonConverter.class);
        assertThatBeanExists(uncapitalize(RoleConverter.class.getSimpleName()), RoleConverter.class);
        assertThatBeanExists(uncapitalize(ServiceConverter.class.getSimpleName()), ServiceConverter.class);
        assertThatBeanExists(uncapitalize(RecordConverter.class.getSimpleName()), RecordConverter.class);
    }

//    @Test
//    void givenImportedAspectBeans_whenGettingEach_shallFindIt() {
//        assertThatBeanExists(uncapitalize(AspectLogging.class.getSimpleName()), AspectLogging.class);
//    }

    private void assertThatBeanExists(String beanName, Class<?> beanClass) {
        assertTrue(context.containsBean(beanName));
        assertNotNull(context.getBean(beanName));
    }

    private String uncapitalize(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
}
