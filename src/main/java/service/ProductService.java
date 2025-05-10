package service;

import dao.ProductDao;
import lombok.RequiredArgsConstructor;
import model.Product;
import model.User;

import java.util.List;

@RequiredArgsConstructor
public class ProductService {
    private final ProductDao productDao;

    public Product create(String name, String imageUrl, int userId) {
        return productDao.save(name, imageUrl, userId);
    }

    public List<Product> getAllByUserId(int userId) {
        return productDao.getAllByUserId(userId);
    }

    public void removeById(int id) {
        productDao.removeById(id);
    }
}
