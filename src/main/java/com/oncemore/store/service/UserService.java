package com.oncemore.store.service;

import com.oncemore.store.model.UserModel;

public interface UserService {
    void save(UserModel user) throws Exception;
}
