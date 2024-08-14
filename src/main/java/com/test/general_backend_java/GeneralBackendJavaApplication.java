package com.test.general_backend_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class GeneralBackendJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeneralBackendJavaApplication.class, args);
	}


}
