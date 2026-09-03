package vn.hcmute.webpr330479.dao;

import vn.hcmute.webpr330479.models.User;

public interface UserDao {

    void insert(User user);

    void update(User user);

    User getById(int id);

    User getByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}