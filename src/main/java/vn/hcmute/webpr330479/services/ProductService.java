package vn.hcmute.webpr330479.services;

import java.util.List;

import vn.hcmute.webpr330479.models.Product;

public interface ProductService {

    List<Product> getAll();

    List<Product> getLatest(
            int limit);

    List<Product> getPage(
            int page,
            int pageSize);

    long count();

    Product getById(
            int id);

    void insert(
            Product product);

    void update(
            Product product);

    void delete(
            int id);
}