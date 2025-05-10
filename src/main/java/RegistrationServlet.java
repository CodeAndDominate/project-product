import constant.AttributeEnum;
import exception.UserAlreadyExistsException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "RegistrationServlet", urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        userService = (UserService) servletContext.getAttribute(UserService.class.getName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/registration.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String login = req.getParameter("login");
            CharSequence password = req.getParameter("password");
            User user = userService.create(login, password);
            req.getSession().setAttribute(AttributeEnum.USER.name(), user);
            getUserList().add(user.getName());
            resp.sendRedirect("/products");
        } catch (UserAlreadyExistsException ex) {
            req.getSession().setAttribute(AttributeEnum.USER_ERROR_MESSAGE.name(), "User already exists");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    private List<String> getUserList() {
        return (List<String>) getServletContext().getAttribute(AttributeEnum.USER_LIST.name());
    }
}
