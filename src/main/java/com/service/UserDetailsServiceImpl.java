package com.service;

import com.model.Role;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UserDetailsServiceImpl (UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.getUser(s);
        Set<Role> roles = new HashSet<>();
        roleService.getAllRoles().forEach(role -> {
            if (user.getRoles().contains(role)) {
                roles.add(role);
            }
        });
        user.setRoles(roles);
        return user;
    }
}

