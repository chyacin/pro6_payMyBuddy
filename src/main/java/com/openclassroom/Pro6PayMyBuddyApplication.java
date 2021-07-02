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


/*	@Bean
	CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository, AccountRepository accountRepository) {
		return args -> {
			// Check the database table ProBuddyContacts manually


			ProBuddyRole adminRole = new ProBuddyRole("ADMIN");
			ProBuddyRole userRole = new ProBuddyRole("USER");
			roleRepository.save(adminRole);
			roleRepository.save(userRole);

			ProBuddyRole findAdminRole = roleRepository.findByName("ADMIN");
			ProBuddyRole findUserRole = roleRepository.findByName("USER");

			Set<ProBuddyRole> roleList = new HashSet<>();
			roleList.add(findAdminRole);
			roleList.add(findUserRole);

			//Admin
			Set<ProBuddyRole> proAdminRole = new HashSet<>();
			proAdminRole.add(findAdminRole);

			String password = new BCryptPasswordEncoder().encode("csmithy234");
            ProBuddyAccount account1 = new ProBuddyAccount();
            account1.setBalance(1000.00);
            account1.setBankName("Bank of Canada");
            account1.setBankAccountNumber("Account1");
            ProBuddyUser user1 = new ProBuddyUser("Cali", "Smith", "60 Second Street", "casm@pb.com", 30,
                    "45457474", "78745799", password, proAdminRole);
			userRepository.save(user1);
			account1.setUser(user1);
			accountRepository.save(account1);




			password = new BCryptPasswordEncoder().encode("krazy8");
			ProBuddyAccount account2 = new ProBuddyAccount();
			account2.setBalance(5000.00);
			account2.setBankName("Bank of France");
			account2.setBankAccountNumber("Account2");
			ProBuddyUser user2 = new ProBuddyUser("Jeffrey", "David", "10 rue street", "jd@pmb.com", 34,
                    "+1868-688-8473", "tt-1986-06-06-jd-1", password, proAdminRole);
			userRepository.save(user2);
			account2.setUser(user2);
			accountRepository.save(account2);



			//User
			Set<ProBuddyRole> proUserRole = new HashSet<>();
			proUserRole.add(findUserRole);

			password = new BCryptPasswordEncoder().encode("jsmithy234");
			ProBuddyAccount account3 = new ProBuddyAccount();
			account3.setBalance(4515.52);
			account3.setBankName("Bank of England");
			account3.setBankAccountNumber("Account3");
			ProBuddyUser user3 = new ProBuddyUser("John", "Smith", "60 Second Street", "josm@pb.com", 45,
                    "45457475", "78745100", password, proUserRole);
            userRepository.save(user3);
            account3.setUser(user3);
            accountRepository.save(account3);

			password = new BCryptPasswordEncoder().encode("seamate3");
            ProBuddyAccount account4 = new ProBuddyAccount();
            account4.setBalance(9515.00);
            account4.setBankName("Bank of Canada");
            account4.setBankAccountNumber("Account4");
            ProBuddyUser user4 = new ProBuddyUser("Anthony", "Wall", "11 rue street", "aw@pmb.com", 32,
                    "+1868-454-2020", "tt-1988-05-09-aw-1", password,  proUserRole);
            userRepository.save(user4);
            account4.setUser(user4);
            accountRepository.save(account4);
            

			password = new BCryptPasswordEncoder().encode("mm1981set");
			ProBuddyAccount account5 = new ProBuddyAccount();
			account5.setBalance(555.00);
			account5.setBankName("Bank of France");
			account5.setBankAccountNumber("Account5");
			ProBuddyUser user5 = new ProBuddyUser("Mase", "Macon", "12 rue street", "mm@pmb.com", 39,
					"+1868-456-1212", "tt-1981-09-08-mm-1", password,  proUserRole);
			userRepository.save(user5);
			account5.setUser(user5);
			accountRepository.save(account5);


			password = new BCryptPasswordEncoder().encode("barbiequeen1998");
			ProBuddyAccount account6 = new ProBuddyAccount();
			account6.setBalance(7555.00);
			account6.setBankName("Bank of England");
			account6.setBankAccountNumber("Account6");
			ProBuddyUser user6 = new ProBuddyUser("Lisa", "David", "10 rue street", "ld@pmb.com", 22,
					"+1868-765-7878", "tt-1998-07-05-mm-1", password,  proUserRole);
			userRepository.save(user6);
			account6.setUser(user6);
			accountRepository.save(account6);


			password = new BCryptPasswordEncoder().encode("johnboy23");
			ProBuddyAccount account7 = new ProBuddyAccount();
			account7.setBalance(754.52);
			account7.setBankName("Bank of Canada");
			account7.setBankAccountNumber("Account7");
			ProBuddyUser user7 = new ProBuddyUser("Kurtis", "John", "13 second street", "kj@pmb.com", 35,
					"+1868-345-6767", "tt-1985-07-07-kj", password, proUserRole);
			userRepository.save(user7);
			account7.setUser(user7);
			accountRepository.save(account7);

			password = new BCryptPasswordEncoder().encode("bigmankeron");
			ProBuddyAccount account8= new ProBuddyAccount();
			account8.setBalance(9754.75);
			account8.setBankName("Bank of France");
			account8.setBankAccountNumber("Account8");
			ProBuddyUser user8 = new ProBuddyUser("Keron", "Thompson", "1 second street", "kt@pmb.com", 40,
					"+1868-788-6666", "tt-1980-09-12-kt-1", password, proUserRole);
			userRepository.save(user8);
			account8.setUser(user8);
			accountRepository.save(account8);

			password = new BCryptPasswordEncoder().encode("maggie25");
			ProBuddyAccount account9= new ProBuddyAccount();
			account9.setBalance(975.00);
			account9.setBankName("Bank of France");
			account9.setBankAccountNumber("Account9");
			ProBuddyUser user9 = new ProBuddyUser("Craig", "Margaret", "34 third street", "cm@pmb.com", 25,
					"+1868-765-9999", "tt-1995-08-98-cm-1", password,  proUserRole);
			userRepository.save(user9);
			account9.setUser(user9);
			accountRepository.save(account9);

			password = new BCryptPasswordEncoder().encode("cthomp1998");
			ProBuddyAccount account10= new ProBuddyAccount();
			account10.setBalance(4405.00);
			account10.setBankName("Bank of Canada");
			account10.setBankAccountNumber("Account10");
			ProBuddyUser user10 = new ProBuddyUser("Corrine", "Thompson", "1 second street", "ct@pmb.com", 22,
					"+1868-455-7744", "tt-1998-01-04-ct-1", password,  proUserRole);
			userRepository.save(user10);
			account10.setUser(user10);
			accountRepository.save(account10);
		};
	}*/

}






