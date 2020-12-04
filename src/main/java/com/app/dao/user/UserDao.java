package com.app.dao.user;

import com.app.model.user.User;

public interface UserDao {
    void save(User user);

    User getFirst();
}
