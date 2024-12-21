package org.example.tp5sring;

import org.example.tp5sring.entities.Role;
import org.example.tp5sring.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.example.tp5sring.repositories.RoleRepository;
import org.example.tp5sring.repositories.UserRepository;

@SpringBootApplication
public class Tp5SringApplication {

	public static void main(String[] args) {
		SpringApplication.run(Tp5SringApplication.class, args);
	}


	@Component
	public class DataLoader implements CommandLineRunner {

		@Autowired
		private UserRepository userRepository;

		@Autowired
		private RoleRepository roleRepository;

		@Override
		public void run(String... args) throws Exception {
			Role adminRole = new Role();
			adminRole.setName("ROLE_ADMIN");
			roleRepository.save(adminRole);

			Role userRole = new Role();
			userRole.setName("ROLE_USER");
			roleRepository.save(userRole);

			User admin = new User();
			admin.setUsername("admin");
			admin.setPassword(new BCryptPasswordEncoder().encode("admin123"));
			admin.setEmail("admin@example.com");
			admin.getRoles().add(adminRole);
			userRepository.save(admin);

			User user = new User();
			user.setUsername("user");
			user.setPassword(new BCryptPasswordEncoder().encode("user123"));
			user.setEmail("user@example.com");
			user.getRoles().add(userRole);
			userRepository.save(user);
		}
	}



}
