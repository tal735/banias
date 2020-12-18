package com.app.service.user;


import com.app.model.user.User;

public interface UserService {
    User getByEmail(String email);
    User getById(Long id);
    User addNewUser(String email, String password) throws Exception;
    User saveOrUpdate(User user);
}
