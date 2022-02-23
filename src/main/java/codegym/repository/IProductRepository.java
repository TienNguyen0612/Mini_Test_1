package codegym.repository;

import codegym.model.Product;

import java.util.ArrayList;

public interface IProductRepository {
    ArrayList<Product> getAllProducts();

    Product saveProduct(Product product);

    void deleteProduct(int id);

    Product findById(int id);

    ArrayList<Product> findAllByName(String name);
    //findAllByNameContaining;
}
