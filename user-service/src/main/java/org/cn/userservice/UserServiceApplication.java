package org.cn.userservice;

import org.aspectj.bridge.ICommand;
import org.cn.userservice.configuration.RSAConfig;
import org.cn.userservice.dto.AdminDTO;
import org.cn.userservice.service.AdminService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(RSAConfig.class)
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    };

    @Bean
    @Profile("!test")
    CommandLineRunner commandLineRunner(AdminService adminService) {
        return args -> {
            AdminDTO admin = new AdminDTO();
            admin.setId(1L);
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword("admin");
            admin.setRole("ADMIN");
            admin.setLabel("label");
            admin = adminService.save(admin);
            System.out.println("admin : "+admin);

        };
    }

}
