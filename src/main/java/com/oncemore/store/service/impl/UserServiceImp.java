//package com.oncemore.store.service.impl;
//
//import com.oncemore.store.service.UserService;
//import com.oncemore.store.model.User;
//import com.oncemore.store.repository.RoleRepository;
//import com.oncemore.store.repository.User2Repository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.Optional;
//
//@Service
//public class UserServiceImp implements UserService {
//
//    private final User2Repository userRepository;
//    private final RoleRepository roleRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    private static final String USER_ROLE = "ROLE_USER";
//
//    @Autowired
//    public UserServiceImp(User2Repository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public Optional<User> findByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }
//
//    @Override
//    public Optional<User> findByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//    @Override
//    public User saveUser(User user) {
//        // Encode plaintext password
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setActive(1);
//        // Set Role to ROLE_USER
//        user.setRoles(Collections.singletonList(roleRepository.findByRole(USER_ROLE)));
//        return userRepository.saveAndFlush(user);
//    }
//}
