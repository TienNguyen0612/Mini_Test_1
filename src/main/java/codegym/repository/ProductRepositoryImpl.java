package codegym.repository;

import codegym.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;

@Transactional
public class ProductRepositoryImpl implements IProductRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ArrayList<Product> getAllProducts() {
        TypedQuery<Product> query = entityManager.createQuery("select p from Product p", Product.class);
        return (ArrayList<Product>) query.getResultList();
    }

    @Override
    public Product saveProduct(Product product) {
        if (product.getId() != 0) {
           return entityManager.merge(product);
        } else {
            entityManager.persist(product);
            return product;
        }
    }

    @Override
    public void deleteProduct(int id) {
        Product product = findById(id);
        if (product != null) {
            entityManager.remove(product);
        }
    }

    @Override
    public Product findById(int id) {
        TypedQuery<Product> query = entityManager.createQuery("select p from Product p where p.id = :id", Product.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public ArrayList<Product> findAllByName(String name) {
        String queryStr = "SELECT p FROM Product AS p WHERE p.name LIKE :name";
        TypedQuery<Product> query = entityManager.createQuery(queryStr, Product.class);
        query.setParameter("name", "%" + name + "%");
        return (ArrayList<Product>) query.getResultList();
    }
}
