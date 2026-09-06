package vn.hcmute.webpr330479.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import vn.hcmute.webpr330479.config.JPAConfig;
import vn.hcmute.webpr330479.dao.UserDao;
import vn.hcmute.webpr330479.models.User;

public class UserDaoImpl
        implements UserDao {

    @Override
    public void insert(User user) {

        EntityManager entityManager =
                JPAConfig.getEntityManager();

        EntityTransaction transaction =
                entityManager.getTransaction();

        try {

            transaction.begin();

            entityManager.persist(user);

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
    public void update(User user) {

        EntityManager entityManager =
                JPAConfig.getEntityManager();

        EntityTransaction transaction =
                entityManager.getTransaction();

        try {

            transaction.begin();

            entityManager.merge(user);

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
    public User getById(int id) {

        EntityManager entityManager =
                JPAConfig.getEntityManager();

        try {

            return entityManager.find(
                    User.class,
                    id);

        } finally {

            entityManager.close();
        }
    }

    @Override
    public User getByUsername(
            String username) {

        EntityManager entityManager =
                JPAConfig.getEntityManager();

        try {

            return entityManager
                    .createQuery(
                            "SELECT u FROM User u "
                            + "WHERE u.username = :username",
                            User.class)
                    .setParameter(
                            "username",
                            username)
                    .getSingleResult();

        } catch (NoResultException exception) {

            return null;

        } finally {

            entityManager.close();
        }
    }

    @Override
    public User getByEmail(
            String email) {

        EntityManager entityManager =
                JPAConfig.getEntityManager();

        try {

            return entityManager
                    .createQuery(
                            "SELECT u FROM User u "
                            + "WHERE u.email = :email",
                            User.class)
                    .setParameter(
                            "email",
                            email)
                    .getSingleResult();

        } catch (NoResultException exception) {

            return null;

        } finally {

            entityManager.close();
        }
    }

    @Override
    public boolean existsByUsername(
            String username) {

        EntityManager entityManager =
                JPAConfig.getEntityManager();

        try {

            Long count =
                    entityManager
                            .createQuery(
                                    "SELECT COUNT(u) "
                                    + "FROM User u "
                                    + "WHERE u.username = :username",
                                    Long.class)
                            .setParameter(
                                    "username",
                                    username)
                            .getSingleResult();

            return count > 0;

        } finally {

            entityManager.close();
        }
    }

    @Override
    public boolean existsByEmail(
            String email) {

        EntityManager entityManager =
                JPAConfig.getEntityManager();

        try {

            Long count =
                    entityManager
                            .createQuery(
                                    "SELECT COUNT(u) "
                                    + "FROM User u "
                                    + "WHERE u.email = :email",
                                    Long.class)
                            .setParameter(
                                    "email",
                                    email)
                            .getSingleResult();

            return count > 0;

        } finally {

            entityManager.close();
        }
    }

    @Override
    public boolean existsByPhone(
            String phone) {

        EntityManager entityManager =
                JPAConfig.getEntityManager();

        try {

            Long count =
                    entityManager
                            .createQuery(
                                    "SELECT COUNT(u) "
                                    + "FROM User u "
                                    + "WHERE u.phone = :phone",
                                    Long.class)
                            .setParameter(
                                    "phone",
                                    phone)
                            .getSingleResult();

            return count > 0;

        } finally {

            entityManager.close();
        }
    }
}