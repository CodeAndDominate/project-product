package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import exception.UserAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import model.Product;
import model.User;

import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class ProductDao {
    private final String filePath = "path-to-your/products.json";
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public Product save(String name, String imageUrl, int userId) {
        List<Product> products = getAll();
        int generatedId = products.isEmpty() ? 0 : products.get(products.size() - 1).getId() + 1;
        Product product = new Product(generatedId, name, imageUrl, userId);
        products.add(product);
        objectMapper.writeValue(Paths.get(filePath).toFile(), products);
        return product;
    }

    public List<Product> getAllByUserId(int userId) {
        return getAll().stream().filter(product -> product.getUserId() == userId).toList();
    }

    @SneakyThrows
    private List<Product> getAll() {
        return objectMapper.readValue(Paths.get(filePath).toFile(), new TypeReference<>() {});
    }

    @SneakyThrows
    public void removeById(int id) {
        List<Product> products = getAll();
        products.removeIf(product -> product.getId() == id);
        objectMapper.writeValue(Paths.get(filePath).toFile(), products);
    }
}
