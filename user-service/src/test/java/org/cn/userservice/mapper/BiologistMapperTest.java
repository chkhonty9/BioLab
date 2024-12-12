package org.cn.userservice.mapper;

import org.assertj.core.api.AssertionsForClassTypes;
import org.cn.userservice.dto.AdminDTO;
import org.cn.userservice.dto.BiologistDTO;
import org.cn.userservice.entity.Admin;
import org.cn.userservice.entity.Biologist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BiologistMapperTest {

    @InjectMocks
    private BiologistMapper biologistMapper;

    @Mock
    private AdminMapper adminMapper;

    private Biologist biologist;
    private BiologistDTO biologistDTO;
    private Admin admin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create an Admin object
        admin = new Admin();
        admin.setId(1L);
        admin.setFirstName("John");
        admin.setLastName("Doe");
        admin.setEmail("john.doe@example.com");
        admin.setPassword("encodedPassword123");
        admin.setRole("ADMIN");
        admin.setLabel("SuperAdmin");

        // Create a Biologist object
        biologist = new Biologist();
        biologist.setId(1L);
        biologist.setFirstName("Jane");
        biologist.setLastName("Smith");
        biologist.setEmail("jane.smith@example.com");
        biologist.setPassword("encodedPassword456");
        biologist.setRole("BIOLOGIST");
        biologist.setLabel("BiologistLabel");
        biologist.setAdmin(admin);

        // Create a BiologistDTO object
        biologistDTO = new BiologistDTO();
        biologistDTO.setId(1L);
        biologistDTO.setFirstName("Jane");
        biologistDTO.setLastName("Smith");
        biologistDTO.setEmail("jane.smith@example.com");
        biologistDTO.setPassword("encodedPassword456");
        biologistDTO.setRole("BIOLOGIST");
        biologistDTO.setLabel("BiologistLabel");
        AdminDTO adminDTO = new AdminDTO();
        BeanUtils.copyProperties(admin, adminDTO);
        biologistDTO.setAdmin(adminDTO);
    }

    @Test
    void fromBiologist(){
        // Arrange
        when(adminMapper.fromAdmin(biologist.getAdmin())).thenReturn(biologistDTO.getAdmin());

        // Act
        BiologistDTO result = biologistMapper.fromBiologist(biologist);

        // Assert
        assertNotNull(result);
        AssertionsForClassTypes.assertThat(biologistDTO).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    void toBiologist() {
        // Arrange
        when(adminMapper.toAdmin(biologistDTO.getAdmin())).thenReturn(admin);

        // Act
        Biologist result = biologistMapper.toBiologist(biologistDTO);

        // Assert
        assertNotNull(result);
        AssertionsForClassTypes.assertThat(biologist).usingRecursiveComparison().isEqualTo(result);
    }
}