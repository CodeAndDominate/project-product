import constant.AttributeEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "LogoutServlet", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(AttributeEnum.USER.name());
        getUserList().remove(user.getName());
        req.getSession().invalidate();
        resp.sendRedirect("/");
    }

    private List<String> getUserList() {
        return (List<String>) getServletContext().getAttribute(AttributeEnum.USER_LIST.name());
    }
}
