package vn.hcmute.webpr330479.dao;

import java.util.List;

import vn.hcmute.webpr330479.models.Category;

public interface CategoryDao {

    List<Category> getAll();

    Category getById(int id);

    void insert(Category category);

    void update(Category category);

    void delete(int id);
}