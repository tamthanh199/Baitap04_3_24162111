package vn.hcmute.webpr330479.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.hcmute.webpr330479.config.JPAConfig;
import vn.hcmute.webpr330479.dao.ProductDao;
import vn.hcmute.webpr330479.models.Product;

public class ProductDaoImpl
        implements ProductDao {

    @Override
    public List<Product> getAll() {

        EntityManager entityManager =
                JPAConfig.getEntityManager();

        try {

            return entityManager
                    .createQuery(
                            "SELECT p FROM Product p "
                            + "ORDER BY p.createdAt DESC, "
                            + "p.id DESC",
                            Product.class)
                    .getResultList();

        } finally {

            entityManager.close();
        }
    }

    @Override
    public List<Product> getLatest(
            int limit) {

        EntityManager entityManager =
                JPAConfig.getEntityManager();

        try {

            return entityManager
                    .createQuery(
                            "SELECT p FROM Product p "
                            + "ORDER BY p.createdAt DESC, "
                            + "p.id DESC",
                            Product.class)
                    .setMaxResults(limit)
                    .getResultList();

        } finally {

            entityManager.close();
        }
    }

    @Override
    public List<Product> getPage(
            int page,
            int pageSize) {

        EntityManager entityManager =
                JPAConfig.getEntityManager();

        try {

            return entityManager
                    .createQuery(
                            "SELECT p FROM Product p "
                            + "ORDER BY p.createdAt DESC, "
                            + "p.id DESC",
                            Product.class)
                    .setFirstResult(
                            (page - 1)
                                    * pageSize)
                    .setMaxResults(
                            pageSize)
                    .getResultList();

        } finally {

            entityManager.close();
        }
    }

    @Override
    public long count() {

        EntityManager entityManager =
                JPAConfig.getEntityManager();

        try {

            return entityManager
                    .createQuery(
                            "SELECT COUNT(p) "
                            + "FROM Product p",
                            Long.class)
                    .getSingleResult();

        } finally {

            entityManager.close();
        }
    }

    @Override
    public Product getById(
            int id) {

        EntityManager entityManager =
                JPAConfig.getEntityManager();

        try {

            return entityManager.find(
                    Product.class,
                    id);

        } finally {

            entityManager.close();
        }
    }

    @Override
    public void insert(
            Product product) {

        EntityManager entityManager =
                JPAConfig.getEntityManager();

        EntityTransaction transaction =
                entityManager.getTransaction();

        try {

            transaction.begin();

            entityManager.persist(
                    product);

            transaction.commit();

        } catch (Exception exception) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw exception;

        } finally {

            entityManager.close();
        }
    }

    @Override
    public void update(
            Product product) {

        EntityManager entityManager =
                JPAConfig.getEntityManager();

        EntityTransaction transaction =
                entityManager.getTransaction();

        try {

            transaction.begin();

            entityManager.merge(
                    product);

            transaction.commit();

        } catch (Exception exception) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw exception;

        } finally {

            entityManager.close();
        }
    }

    @Override
    public void delete(
            int id) {

        EntityManager entityManager =
                JPAConfig.getEntityManager();

        EntityTransaction transaction =
                entityManager.getTransaction();

        try {

            transaction.begin();

            Product product =
                    entityManager.find(
                            Product.class,
                            id);

            if (product != null) {

                entityManager.remove(
                        product);
            }

            transaction.commit();

        } catch (Exception exception) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw exception;

        } finally {

            entityManager.close();
        }
    }
}