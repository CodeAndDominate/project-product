import constant.AttributeEnum;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;
import model.User;
import service.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = "/products")
public class ProductServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        productService = (ProductService) servletContext.getAttribute(ProductService.class.getName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(AttributeEnum.USER.name());
        List<Product> userProducts = productService.getAllByUserId(user.getId());
        req.getSession().setAttribute(AttributeEnum.USER_PRODUCTS.name(), userProducts);

        req.getRequestDispatcher("/products.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productId = req.getParameter("productId");
        if (productId != null) {
            productService.removeById(Integer.parseInt(productId));
            resp.sendRedirect("/products");
            return;
        }

        String productName = req.getParameter("name");
        String imageUrl = req.getParameter("imageUrl");
        User user = (User) req.getSession().getAttribute(AttributeEnum.USER.name());
        productService.create(productName, imageUrl, user.getId());
        resp.sendRedirect("/products");
    }
}
