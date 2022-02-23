package codegym.service;

import codegym.model.Product;

import java.util.ArrayList;

public interface IProductService {
    ArrayList<Product> getAllProducts();

    Product save(Product product);

    void delete(int id);

    Product findById(int id);

    ArrayList<Product> findAllByName(String name);
}
