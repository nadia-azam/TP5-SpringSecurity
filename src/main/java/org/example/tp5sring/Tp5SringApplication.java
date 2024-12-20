package org.example.tp5sring;

import entities.Role;
import entities.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import repositories.RoleRepository;
import repositories.UserRepository;


import java.util.Set;

@SpringBootApplication
public class Tp5SringApplication {

	public static void main(String[] args) {
		SpringApplication.run(Tp5SringApplication.class, args);
	}



	/*CommandLineRunner commandLineRunner(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
		return args -> {
			// Créer les rôles
			Role adminRole = new Role();
			adminRole.setName("ROLE_ADMIN");
			roleRepository.save(adminRole);

			Role userRole = new Role();
			userRole.setName("ROLE_USER");
			roleRepository.save(userRole);

			// Créer un utilisateur admin
			User admin = new User();
			admin.setEmail("admin");
			admin.setPassword(passwordEncoder.encode("admin123"));
			admin.setEmail("admin@example.com");
			admin.setRoles(Set.of(adminRole));
			userRepository.save(admin);

			// Créer un utilisateur standard
			User user = new User();
			user.setUsername("user");
			user.setPassword(passwordEncoder.encode("user123"));
			user.setEmail("user@example.com");
			user.setRoles(Set.of(userRole));
			userRepository.save(user);

			System.out.println("Données initialisées avec succès !");
		};
	}*/

}
