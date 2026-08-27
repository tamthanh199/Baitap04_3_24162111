package vn.hcmute.webpr330479.services.impl;

import java.util.List;

import vn.hcmute.webpr330479.dao.CategoryDao;
import vn.hcmute.webpr330479.dao.impl.CategoryDaoImpl;
import vn.hcmute.webpr330479.models.Category;
import vn.hcmute.webpr330479.services.CategoryService;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> getAll() {
        return categoryDao.getAll();
    }

    @Override
    public Category getById(int id) {
        return categoryDao.getById(id);
    }

    @Override
    public void insert(Category category) {
        categoryDao.insert(category);
    }

    @Override
    public void update(Category category) {
        categoryDao.update(category);
    }

    @Override
    public void delete(int id) {
        categoryDao.delete(id);
    }
}