package vn.hcmute.webpr330479.services.impl;

import java.util.List;

import vn.hcmute.webpr330479.dao.ProductDao;
import vn.hcmute.webpr330479.dao.impl.ProductDaoImpl;
import vn.hcmute.webpr330479.models.Product;
import vn.hcmute.webpr330479.services.ProductService;

public class ProductServiceImpl
        implements ProductService {

    private final ProductDao productDao =
            new ProductDaoImpl();

    @Override
    public List<Product> getAll() {

        return productDao.getAll();
    }

    @Override
    public List<Product> getLatest(
            int limit) {

        return productDao
                .getLatest(
                        limit);
    }

    @Override
    public List<Product> getPage(
            int page,
            int pageSize) {

        return productDao
                .getPage(
                        page,
                        pageSize);
    }

    @Override
    public long count() {

        return productDao.count();
    }

    @Override
    public Product getById(
            int id) {

        return productDao
                .getById(
                        id);
    }

    @Override
    public void insert(
            Product product) {

        productDao.insert(
                product);
    }

    @Override
    public void update(
            Product product) {

        productDao.update(
                product);
    }

    @Override
    public void delete(
            int id) {

        productDao.delete(
                id);
    }
}