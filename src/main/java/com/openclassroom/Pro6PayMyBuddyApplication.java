package com.openclassroom;

import com.openclassroom.model.ProBuddyAccount;
import com.openclassroom.model.ProBuddyRole;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.repositories.AccountRepository;
import com.openclassroom.repositories.RoleRepository;
import com.openclassroom.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;




@EnableWebSecurity
@SpringBootApplication
public class Pro6PayMyBuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(Pro6PayMyBuddyApplication.class, args);

	}
}






