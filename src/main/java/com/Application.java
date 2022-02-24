package com;

import com.dao.RoleRepository;
import com.dao.UserRepository;
import com.model.Role;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public Application(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Role admin = new Role("ROLE_ADMIN");
		Role user = new Role("ROLE_USER");
		roleRepository.save(admin);
		roleRepository.save(user);

		Set<Role> adminRoles = new HashSet<>();
		Set<Role> userRoles = new HashSet<>();
		adminRoles.add(admin);
		adminRoles.add(user);
		userRoles.add(user);

		userRepository.save(new User("user1", 21, "surname1", "user1@mail.ru",
				passwordEncoder.encode( "111"), adminRoles));
		userRepository.save(new User("user2", 21, "surname2", "user2@mail.ru",
				passwordEncoder.encode( "222"), userRoles));
		userRepository.save(new User("user3", 21, "surname3", "user3@mail.ru",
				passwordEncoder.encode( "333"), userRoles));

	}
}
