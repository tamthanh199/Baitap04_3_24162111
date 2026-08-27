package vn.hcmute.webpr330479.dao;

import vn.hcmute.webpr330479.models.User;

public interface UserDao {

    void insert(User user);

    User getByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
