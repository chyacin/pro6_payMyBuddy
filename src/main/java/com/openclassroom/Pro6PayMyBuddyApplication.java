package com.openclassroom;

import com.openclassroom.model.ProBuddyRole;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.repositories.RoleRepository;
import com.openclassroom.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;


@SpringBootApplication
public class Pro6PayMyBuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(Pro6PayMyBuddyApplication.class, args);

	}

/*
	@Bean
	CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository) {
		return args -> {

			ProBuddyRole adminRole = new ProBuddyRole("admin");
			ProBuddyRole userRole = new ProBuddyRole("user");
			roleRepository.save(adminRole);
			roleRepository.save(userRole);

			ProBuddyRole findAdminRole = roleRepository.findByName("admin");
			ProBuddyRole findUserRole = roleRepository.findByName("user");

			Set<ProBuddyRole> roleList = new HashSet<>();
			roleList.add(findAdminRole);
			roleList.add(findUserRole);

			//Admin
			Set<ProBuddyRole> proAdminRole = new HashSet<>();
			proAdminRole.add(findAdminRole);

			String password = new BCryptPasswordEncoder().encode("csmithy234");
			userRepository.save(new ProBuddyUser("Cali", "Smith", "60 Second Street", "casm@pb.com", 30,
					"45457474", "78745799", password, "accountNo1", proAdminRole));

			password =  new BCryptPasswordEncoder().encode("krazy8");
			userRepository.save(new ProBuddyUser("Jeffrey", "David", "10 rue street", "jd@pmb.com", 34,
					"+1868-688-8473", "tt-1986-06-06-jd-1",password, "accountNo2", proAdminRole));

			//User
			Set<ProBuddyRole> proUserRole = new HashSet<>();
			proUserRole.add(findUserRole);

			password =  new BCryptPasswordEncoder().encode("jsmithy234");
			userRepository.save(new ProBuddyUser("John", "Smith", "60 Second Street", "josm@pb.com", 45,
					"45457475", "78745100",password , "accountNo3", proUserRole));

			password =  new BCryptPasswordEncoder().encode("seamate3");
			userRepository.save(new ProBuddyUser("Anthony", "Wall", "11 rue street", "aw@pmb.com", 32,
					"+1868-454-2020", "tt-1988-05-09-aw-1",password , "accountNo4", proUserRole));

			password =  new BCryptPasswordEncoder().encode("mm1981set");
			userRepository.save(new ProBuddyUser("Mase", "Macon", "12 rue street", "mm@pmb.com", 39,
					"+1868-456-1212", "tt-1981-09-08-mm-1",password , "accountNo5", proUserRole));

			password =  new BCryptPasswordEncoder().encode("barbiequeen1998");
			userRepository.save(new ProBuddyUser("Lisa", "David", "10 rue street", "ld@pmb.com", 22,
					"+1868-765-7878", "tt-1998-07-05-mm-1",password , "accountNo6", proUserRole));

			password =  new BCryptPasswordEncoder().encode("johnboy23");
			userRepository.save(new ProBuddyUser("Kurtis", "John", "13 second street", "kj@pmb.com", 35,
					"+1868-345-6767", "tt-1985-07-07-kj",password , "accountNo7", proUserRole));

			password =  new BCryptPasswordEncoder().encode("bigmankeron");
			userRepository.save(new ProBuddyUser("Keron", "Thompson", "1 second street", "kt@pmb.com", 40,
					"+1868-788-6666", "tt-1980-09-12-kt-1",password , "accountNo8", proUserRole));

			password =  new BCryptPasswordEncoder().encode("maggie25");
			userRepository.save(new ProBuddyUser("Craig", "Margaret", "34 third street", "cm@pmb.com", 25,
					"+1868-765-9999", "tt-1995-08-98-cm-1",password , "accountNo9", proUserRole));

			password =  new BCryptPasswordEncoder().encode("cthomp1998");
			userRepository.save(new ProBuddyUser("Corrine", "Thompson", "1 second street", "ct@pmb.com", 22,
					"+1868-455-7744", "tt-1998-01-04-ct-1",password , "accountNo10", proUserRole));
		};*/

}


