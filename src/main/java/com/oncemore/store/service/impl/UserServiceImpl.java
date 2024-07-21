package com.oncemore.store.service.impl;

import com.oncemore.store.entity.User;
import com.oncemore.store.model.UserModel;
import com.oncemore.store.repository.UserRepository;
import com.oncemore.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void save(UserModel userModel) throws Exception {
        boolean check = userRepository.existsUserByUsername(userModel.getUsername());
        if (check) {
            throw new EntityExistsException("Tài khoản đã tồn tại!");
        }
        User user = new User();
        user.setUsername(userModel.getUsername());
        user.setName(userModel.getFullName());
        user.setRole("USER");
        user.setPassword(userModel.getPassword());
        userRepository.save(user);
    }
}
