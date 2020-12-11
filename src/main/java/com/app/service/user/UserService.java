package com.app.service.user;


import com.app.model.user.User;

public interface UserService {
    User getFirst();
    User getByEmail(String email);
    User addNewUser(String email, String password) throws Exception;
}
