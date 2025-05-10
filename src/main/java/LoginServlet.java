import constant.AttributeEnum;
import exception.UserTooManyTriesException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.UserService;
import validation.LoginValidationService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;
    private LoginValidationService loginValidationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        userService = (UserService) servletContext.getAttribute(UserService.class.getName());
        loginValidationService = (LoginValidationService) servletContext.getAttribute(LoginValidationService.class.getName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            resp.sendRedirect("/product");
        }
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            loginValidationService.checkIfLoginIsAllowed(req);
            String login = req.getParameter("login");
            CharSequence password = req.getParameter("password");
            var userOpt = userService.getByCredentials(login, password);
            userOpt.ifPresent(user -> {
                req.getSession().setAttribute(AttributeEnum.USER.name(), user);
                getUserList().add(user.getName());
            });
            resp.sendRedirect("/");
        } catch (UserTooManyTriesException e) {
            req.getSession().setAttribute(AttributeEnum.USER_ERROR_MESSAGE.name(), "Too many tries");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    private List<String> getUserList() {
        return (List<String>) getServletContext().getAttribute(AttributeEnum.USER_LIST.name());
    }
}
