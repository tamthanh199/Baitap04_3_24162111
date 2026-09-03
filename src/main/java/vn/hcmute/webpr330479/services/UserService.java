package vn.hcmute.webpr330479.services;

import vn.hcmute.webpr330479.models.User;

public interface UserService {

    boolean register(User user);

    User login(String username, String password);

    User getById(int id);

    void update(User user);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}