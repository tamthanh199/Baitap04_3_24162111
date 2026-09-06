package vn.hcmute.webpr330479.services.impl;

import vn.hcmute.webpr330479.dao.UserDao;
import vn.hcmute.webpr330479.dao.impl.UserDaoImpl;
import vn.hcmute.webpr330479.models.User;
import vn.hcmute.webpr330479.services.UserService;

public class UserServiceImpl
        implements UserService {

    private final UserDao userDao =
            new UserDaoImpl();

    @Override
    public boolean register(
            User user) {

        if (existsByUsername(
                user.getUsername())
                || existsByEmail(
                        user.getEmail())
                || existsByPhone(
                        user.getPhone())) {

            return false;
        }

        userDao.insert(user);

        return true;
    }

    @Override
    public User login(
            String username,
            String password) {

        User user =
                userDao.getByUsername(
                        username);

        if (user != null
                && password.equals(
                        user.getPassword())) {

            return user;
        }

        return null;
    }

    @Override
    public User getById(
            int id) {

        return userDao.getById(id);
    }

    @Override
    public User getByUsername(
            String username) {

        return userDao
                .getByUsername(
                        username);
    }

    @Override
    public User getByEmail(
            String email) {

        return userDao
                .getByEmail(
                        email);
    }

    @Override
    public void update(
            User user) {

        userDao.update(user);
    }

    @Override
    public boolean existsByUsername(
            String username) {

        return userDao
                .existsByUsername(
                        username);
    }

    @Override
    public boolean existsByEmail(
            String email) {

        return userDao
                .existsByEmail(
                        email);
    }

    @Override
    public boolean existsByPhone(
            String phone) {

        return userDao
                .existsByPhone(
                        phone);
    }
}