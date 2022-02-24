package com.service;


import com.dao.RoleRepository;
import com.dao.UserRepository;
import com.model.Role;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private PasswordEncoder encoder;

    @Lazy
    @Autowired
    public UserServiceImpl (UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public void addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void removeUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void updateUser(User user, long id) {
        User updateUser = getUser(id);
        updateUser.setName(user.getName());
        updateUser.setAge(user.getAge());
        updateUser.setSurname(user.getSurname());
        updateUser.setUsername(user.getUsername());
        if (!user.getPassword().equals("")) {
            updateUser.setPassword(user.getPassword());
        }
        updateUser.setRoles(user.getRoles());
    }

    @Override
    public User getUser(long id){
        User user = null;
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        return user;
    }

    @Override
    public User getUser(String username){
        User user = null;
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }

        return user;
    }

}
