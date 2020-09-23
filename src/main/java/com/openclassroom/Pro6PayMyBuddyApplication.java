package com.openclassroom;

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

	/*@Bean
	CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository,
	                       RoleUserRepository roleUserRepository) {
		return args -> {

			ProBuddyRole adminRole = new ProBuddyRole("admin");
			ProBuddyRole userRole =  new ProBuddyRole("user");
			roleRepository.save(adminRole);
			roleRepository.save(userRole);

			ProBuddyRole findAdminRole = roleRepository.findByName("admin");
			ProBuddyRole findUserRole = roleRepository.findByName("user");

			List<ProBuddyRole> roleList = new ArrayList<>();
			roleList.add(findAdminRole);
			roleList.add(findUserRole);


			//admin
			List<ProBuddyRole> proAdminRole= new ArrayList<>();
			proAdminRole.add(adminRole);
			ProBuddyUser newAdmin = new ProBuddyUser("Cali", "Smith","60 Second Street","casm@pb.com", 30,
					"45457474","78745799","csmithy234","accountNo1", proAdminRole);
			userRepository.save(newAdmin);

			ProBuddyUser newAdmin1 = new ProBuddyUser("Jeffrey", "David","10 rue street","jd@pmb.com", 34,
					"+1868-688-8473","tt-1986-06-06-jd-1","krazy8","accountNo2", proAdminRole);
			userRepository.save(newAdmin1);

			ProBuddyUser savedAdmin = userRepository.save(newAdmin);
			ProBuddyRoleProBuddyUser proBuddyAdminRole = new ProBuddyRoleProBuddyUser(findAdminRole.getId(),
					savedAdmin.getId());

			ProBuddyUser savedAdmin1 = userRepository.save(newAdmin1);
			ProBuddyRoleProBuddyUser proBuddyAdminRole1 = new ProBuddyRoleProBuddyUser(findAdminRole.getId(),
					savedAdmin1.getId());


			roleUserRepository.save(proBuddyAdminRole);
			roleUserRepository.save(proBuddyAdminRole1);



			//User
			List<ProBuddyRole> proUserRole= new ArrayList<>();
			proUserRole.add(userRole);
			ProBuddyUser newUser = new ProBuddyUser("John", "Smith","60 Second Street","josm@pb.com", 45,
					"45457475","78745100","jsmithy234","accountNo3", proUserRole);
			userRepository.save(newUser);

			ProBuddyUser newUser1 = new ProBuddyUser("Anthony", "Wall","11 rue street","aw@pmb.com", 32,
					"+1868-454-2020","tt-1988-05-09-aw-1","seamate3","accountNo4", proUserRole);
			userRepository.save(newUser1);

			ProBuddyUser newUser2 = new ProBuddyUser("Mase", "Macon","12 rue street","mm@pmb.com", 39,
					"+1868-456-1212","tt-1981-09-08-mm-1","mm1981set","accountNo5", proUserRole);
			userRepository.save(newUser2);

			ProBuddyUser newUser3 = new ProBuddyUser("Lisa", "David","10 rue street","ld@pmb.com", 22,
					"+1868-765-7878","tt-1998-07-05-mm-1","barbiequeen1998","accountNo6", proUserRole);
			userRepository.save(newUser3);

			ProBuddyUser newUser4 = new ProBuddyUser("Kurtis", "John","13 second street","kj@pmb.com", 35,
					"+1868-345-6767","tt-1985-07-07-kj","johnboy23","accountNo7", proUserRole);
			userRepository.save(newUser4);

			ProBuddyUser newUser5 = new ProBuddyUser("Keron", "Thompson","1 second street","kt@pmb.com", 40,
					"+1868-788-6666","tt-1980-09-12-kt-1","bigmankeron","accountNo8", proUserRole);
			userRepository.save(newUser5);

			ProBuddyUser newUser6 = new ProBuddyUser("Craig", "Margaret","34 third street","cm@pmb.com", 25,
					"+1868-765-9999","tt-1995-08-98-cm-1","maggie25","accountNo9", proUserRole);
			userRepository.save(newUser6);

			ProBuddyUser newUser7 = new ProBuddyUser("Corrine", "Thompson","1 second street","ct@pmb.com", 22,
					"+1868-455-7744","tt-1998-01-04-ct-1","'cthomp1998","accountNo10", proUserRole);
			userRepository.save(newUser7);


			ProBuddyUser savedUser = userRepository.save(newUser);
			ProBuddyRoleProBuddyUser proBuddyUserRole = new ProBuddyRoleProBuddyUser(findAdminRole.getId(),
					savedUser.getId());

			ProBuddyUser savedUser1 = userRepository.save(newUser1);
			ProBuddyRoleProBuddyUser proBuddyUserRole1 = new ProBuddyRoleProBuddyUser(findAdminRole.getId(),
					savedUser1.getId());

			ProBuddyUser savedUser2 = userRepository.save(newUser2);
			ProBuddyRoleProBuddyUser proBuddyUserRole2 = new ProBuddyRoleProBuddyUser(findAdminRole.getId(),
					savedUser2.getId());

			ProBuddyUser savedUser3 = userRepository.save(newUser3);
			ProBuddyRoleProBuddyUser proBuddyUserRole3 = new ProBuddyRoleProBuddyUser(findAdminRole.getId(),
					savedUser3.getId());

			ProBuddyUser savedUser4 = userRepository.save(newUser4);
			ProBuddyRoleProBuddyUser proBuddyUserRole4 = new ProBuddyRoleProBuddyUser(findAdminRole.getId(),
					savedUser4.getId());

			ProBuddyUser savedUser5 = userRepository.save(newUser5);
			ProBuddyRoleProBuddyUser proBuddyUserRole5 = new ProBuddyRoleProBuddyUser(findAdminRole.getId(),
					savedUser5.getId());

			ProBuddyUser savedUser6 = userRepository.save(newUser6);
			ProBuddyRoleProBuddyUser proBuddyUserRole6 = new ProBuddyRoleProBuddyUser(findAdminRole.getId(),
					savedUser6.getId());

			ProBuddyUser savedUser7 = userRepository.save(newUser7);
			ProBuddyRoleProBuddyUser proBuddyUserRole7 = new ProBuddyRoleProBuddyUser(findAdminRole.getId(),
					savedUser7.getId());

			roleUserRepository.save(proBuddyUserRole);
			roleUserRepository.save(proBuddyUserRole1);
			roleUserRepository.save(proBuddyUserRole2);
			roleUserRepository.save(proBuddyUserRole3);
			roleUserRepository.save(proBuddyUserRole4);
			roleUserRepository.save(proBuddyUserRole5);
			roleUserRepository.save(proBuddyUserRole6);
			roleUserRepository.save(proBuddyUserRole7);*/

};


