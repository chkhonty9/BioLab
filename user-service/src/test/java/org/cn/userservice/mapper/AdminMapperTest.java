package org.cn.userservice.mapper;

import org.assertj.core.api.AssertionsForClassTypes;
import org.cn.userservice.dto.AdminDTO;
import org.cn.userservice.entity.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminMapperTest {

    private AdminMapper adminMapper;
    private Admin admin;
    private AdminDTO adminDTO;

    @BeforeEach
    void setUp() {
        adminMapper = new AdminMapper();

        // Create an Admin object
        admin = new Admin();
        admin.setId(1L);
        admin.setFirstName("John");
        admin.setLastName("Doe");
        admin.setEmail("john.doe@example.com");
        admin.setPassword("encodedPassword123");
        admin.setRole("ADMIN");
        admin.setLabel("SuperAdmin");

        // Create an AdminDTO object
        adminDTO = new AdminDTO();
        adminDTO.setId(1L);
        adminDTO.setFirstName("John");
        adminDTO.setLastName("Doe");
        adminDTO.setEmail("john.doe@example.com");
        adminDTO.setPassword("encodedPassword123");
        adminDTO.setRole("ADMIN");
        adminDTO.setLabel("SuperAdmin");
    }

    @Test
    void fromAdmin() {

        // Act
        AdminDTO result = adminMapper.fromAdmin(admin);

        assertNotNull(result);
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(adminDTO);
    }

    @Test
    void toAdmin() {

        Admin result = adminMapper.toAdmin(adminDTO);

        assertNotNull(result);
        AssertionsForClassTypes.assertThat(result).usingRecursiveComparison().isEqualTo(admin);


    }
}