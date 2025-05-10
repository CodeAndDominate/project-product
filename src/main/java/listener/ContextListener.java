package listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import constant.AttributeEnum;
import dao.ProductDao;
import dao.UserDao;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import service.ProductService;
import service.UserService;
import validation.LoginValidationService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        var objectMapper = new ObjectMapper();
        var list = new CopyOnWriteArrayList<String>();

        var servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute(UserService.class.getName(), createRegistrationService(objectMapper));
        servletContext.setAttribute(ProductService.class.getName(), createProductService(objectMapper));
        servletContext.setAttribute(LoginValidationService.class.getName(), createLoginValidationService());
        servletContext.setAttribute(AttributeEnum.USER_LIST.name(), list);
    }

    private UserService createRegistrationService(ObjectMapper objectMapper) {
        var registrationDao = new UserDao(objectMapper);
        return new UserService(registrationDao, new BCryptPasswordEncoder());
    }

    private ProductService createProductService(ObjectMapper objectMapper) {
        var productDao = new ProductDao(objectMapper);
        return new ProductService(productDao);
    }

    private LoginValidationService createLoginValidationService() {
        return new LoginValidationService();
    }
}
