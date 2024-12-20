package org.cn.userservice.dao;

import org.cn.userservice.configuration.RSAConfig;
import org.cn.userservice.configuration.SecurityConfig;
import org.cn.userservice.entity.Admin;
import org.cn.userservice.entity.Biologist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BiologistRepositoryTest {

    @MockBean
    private SecurityConfig securityConfig;

    @Autowired
    private BiologistRepository biologistRepository;

    @Autowired
    private AdminRepository adminRepository;

    @MockBean
    private RSAConfig rsaConfig;

    private Admin admin;


    @BeforeEach
    void setUp() {

        admin = new Admin();
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("admin");
        admin.setRole("ADMIN");
        admin.setLabel("label");
        admin.setBiologists(new ArrayList<Biologist>());
        admin = adminRepository.save(admin);

        Biologist biologist1 = new Biologist();
        // biologist1.setId(2L);
        biologist1.setFirstName("John");
        biologist1.setLastName("Doe");
        biologist1.setEmail("john.doe@email.com");
        biologist1.setPassword("password");
        biologist1.setRole("BIOLOGIST");
        biologist1.setLabel("Researcher");
        biologist1.setAdmin(admin);

        Biologist biologist2 = new Biologist();
        // biologist2.setId(3L);
        biologist2.setFirstName("Jane");
        biologist2.setLastName("Smith");
        biologist2.setEmail("jane.smith@email.com");
        biologist2.setPassword("password");
        biologist2.setRole("BIOLOGIST");
        biologist2.setLabel("Scientist");
        biologist2.setAdmin(admin);

        biologist1 = biologistRepository.save(biologist1);
        biologist2 = biologistRepository.save(biologist2);
    }


    @Test
    void findByLabelContainingIgnoreCase() {
        List<Biologist> biologists = biologistRepository.findByLabelContainingIgnoreCase("Researcher");

        assertThat(biologists).isNotEmpty();
        assertThat(biologists).hasSize(1);
        assertThat(biologists.get(0).getEmail()).isEqualTo("john.doe@email.com");
    }

    @Test
    void findByLastNameContainingIgnoreCase() {
        List<Biologist> biologists = biologistRepository.findByLastNameContainingIgnoreCase("Doe");

        assertThat(biologists).isNotEmpty();
        assertThat(biologists).hasSize(1);
        assertThat(biologists.get(0).getEmail()).isEqualTo("john.doe@email.com");
    }

    @Test
    void findByAdmin() {
        List<Biologist> biologists = biologistRepository.findByAdmin(admin);

        assertThat(biologists).isNotEmpty();
        assertThat(biologists).hasSize(2);

    }
}