package filter;

import constant.AttributeEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebFilter(urlPatterns = {"/", "/products", "/login", "/logout", "/registration"})
public class AuthenticationFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getRequestURI();
        User user = (User) request.getSession().getAttribute(AttributeEnum.USER.name());
        if (user == null) {
            if (List.of("/", "/login", "/logout", "/products").contains(uri)) {
                req.getRequestDispatcher("/login").forward(req, res);
                return;
            }
        } else {
            if (uri.equals("/logout")) {
                req.getRequestDispatcher("/logout").forward(req, res);
            }
            else {
                req.getRequestDispatcher("/products").forward(req, res);
            }
            return;
        }
        chain.doFilter(req, res);
    }
}
