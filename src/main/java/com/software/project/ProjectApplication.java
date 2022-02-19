package com.software.project;

import com.software.project.Domain.User;
import com.software.project.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProjectApplication implements CommandLineRunner {
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setUsername("userOne");
		user.setEmail("userOne@gmail.com");
		// user.setId(1);
		user.setRoles("USER");
		user.setPassword(passwordEncoder.encode("password"));
		userRepository.save(user);
		User userOne = new User();
		userOne.setUsername("adminOne");
		userOne.setEmail("adminOne@gmail.com");
		userOne.setRoles("ADMIN");
		// userOne.setId(2);
		userOne.setPassword(passwordEncoder.encode("admin"));
		userRepository.save(userOne);
		User userTwo = new User();
		userTwo.setEmail("adminTwo@gmail.com");
		userTwo.setRoles("ADMIN");
		userTwo.setUsername("adminThree");
		// userTwo.setId(2);
		userTwo.setPassword(passwordEncoder.encode("admin"));
		userRepository.save(userTwo);
		User userthree = new User();
		userthree.setEmail("adminThree@gmail.com");
		userthree.setRoles("ADMIN");
		userthree.setUsername("adminTwo");
		// userthree.setId(2);
		userthree.setPassword(passwordEncoder.encode("admin"));
		userRepository.save(userthree);
		User userFour = new User();
		userFour.setEmail("adminFour@gmail.com");
		userFour.setRoles("ADMIN");
		userFour.setUsername("adminFour");
		// userFour.setId(2);
		userFour.setPassword(passwordEncoder.encode("admin"));
		userRepository.save(userFour);
	
	}

}
