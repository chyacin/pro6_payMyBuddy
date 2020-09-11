package com.openclassroom.application;

import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Pro6PayMyBuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(Pro6PayMyBuddyApplication.class, args);
	}

	@Bean
	public CommandLineRunner newUsers(UserRepository userRepo) {
		return(args) -> {
			userRepo.save(new ProBuddyUser("John","Stones","22 Fifth Street","Jst@pmb.com",44,
					"+16667567676", "54998","" , "johnnyS", "", "Admin"));
		};
	}

}
