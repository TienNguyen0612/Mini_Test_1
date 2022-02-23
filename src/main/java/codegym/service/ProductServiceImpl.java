package codegym.service;

import codegym.model.Product;
import codegym.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public ArrayList<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public Product save(Product product) {
        return productRepository.saveProduct(product);
    }

    @Override
    public void delete(int id) {
        productRepository.deleteProduct(id);
    }

    @Override
    public Product findById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public ArrayList<Product> findAllByName(String name) {
        return productRepository.findAllByName(name);
    }
}
