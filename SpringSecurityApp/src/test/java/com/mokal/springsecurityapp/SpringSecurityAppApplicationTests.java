package com.mokal.springsecurityapp;

import com.mokal.springsecurityapp.entities.User;
import com.mokal.springsecurityapp.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringSecurityAppApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private JwtService jwtService;

	@Test
	void createToken() {
		User user = new User(4L,"aniket@mokal.com","aniket29");
		String token = jwtService.generateToken(user);
		System.out.println(token);
		Long id = jwtService.getUserIdFromToken(token);
		System.out.println(id);
	;
	}
}
