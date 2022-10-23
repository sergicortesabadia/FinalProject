package com.Ironhack.FinalProject;

import com.Ironhack.FinalProject.repositories.AdminRepository;
import com.Ironhack.FinalProject.repositories.RoleRepository;
import com.Ironhack.FinalProject.roles.Role;
import com.Ironhack.FinalProject.roles.RolesEnum;
import com.Ironhack.FinalProject.usermodels.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class FinalProjectApplication implements CommandLineRunner {
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(FinalProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		Admin mainAdmin = new Admin ("admin", "1234");
		adminRepository.save(mainAdmin);
		roleRepository.save(new Role(RolesEnum.ADMIN, mainAdmin));


	}
}

