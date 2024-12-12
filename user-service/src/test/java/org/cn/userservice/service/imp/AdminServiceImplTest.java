package org.cn.userservice.service.imp;

import org.cn.userservice.dao.AdminRepository;
import org.cn.userservice.dto.AdminDTO;
import org.cn.userservice.entity.Admin;
import org.cn.userservice.mapper.AdminMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private AdminMapper adminMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminServiceImpl adminService;

    AdminDTO adminDTO;
    Admin admin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        adminDTO = new AdminDTO();
        adminDTO = new AdminDTO();
        adminDTO.setId(1L);
        adminDTO.setFirstName("John");
        adminDTO.setLastName("Doe");
        adminDTO.setEmail("john.doe@example.com");
        adminDTO.setPassword("password");
        adminDTO.setRole("ADMIN");
        adminDTO.setLabel("SuperAdmin");

        admin = new Admin();
        admin.setId(1L);
        admin.setFirstName("John");
        admin.setLastName("Doe");
        admin.setEmail("john.doe@example.com");
        admin.setPassword("password");
        admin.setRole("ADMIN");
        admin.setLabel("SuperAdmin");
    }

    @Test
    void save() {


        when(passwordEncoder.encode("password")).thenReturn("password");
        when(adminMapper.toAdmin(adminDTO)).thenReturn(admin);
        when(adminRepository.save(admin)).thenReturn(admin);
        when(adminMapper.fromAdmin(admin)).thenReturn(adminDTO);


        AdminDTO result = adminService.save(adminDTO);


        assertNotNull(result);
        assertEquals(adminDTO, result);
        verify(passwordEncoder).encode("password");
        verify(adminRepository).save(admin);
        verify(adminMapper).fromAdmin(admin);
    }

    @Test
    void findOne() {

        adminRepository.save(admin);

        Long id = 1L;

        when(adminRepository.findById(id)).thenReturn(Optional.of(admin));
        when(adminMapper.fromAdmin(admin)).thenReturn(adminDTO);


        AdminDTO result = adminService.findOne(id);

        assertNotNull(result);
        assertEquals(adminDTO, result);
        verify(adminRepository).findById(id);
        verify(adminMapper).fromAdmin(admin);
    }

    @Test
    void findAll() {

        Admin admin1 = new Admin();
        admin1.setId(2L);
        admin1.setFirstName("Nouha");
        admin1.setLastName("chkhonty");
        admin1.setEmail("nouha@example.com");
        admin1.setPassword("password");
        admin1.setRole("ADMIN");
        admin1.setLabel("SuperAdmin");
        List<Admin> admins = List.of(admin, admin1);

        AdminDTO adminDTO1 = new AdminDTO();
        adminDTO1.setId(2L);
        adminDTO1.setFirstName("Nouha");
        adminDTO1.setLastName("chkhonty");
        adminDTO1.setEmail("nouha@example.com");
        adminDTO1.setPassword("password");
        adminDTO1.setRole("ADMIN");
        adminDTO1.setLabel("SuperAdmin");
        List<AdminDTO> adminDTOs = List.of(adminDTO, adminDTO1);

        when(adminRepository.findAll()).thenReturn(admins);
        when(adminMapper.fromAdmin(admin)).thenReturn(adminDTO);
        when(adminMapper.fromAdmin(admin1)).thenReturn(adminDTO1);

        List<AdminDTO> result = adminService.findAll();

        assertNotNull(result);
        assertEquals(adminDTOs.size(), result.size());
        verify(adminRepository).findAll();
        verify(adminMapper, times(2)).fromAdmin(any(Admin.class));
    }

    @Test
    void delete() {

        Long id = 1L;

        when(adminRepository.existsById(id)).thenReturn(true);

        adminService.delete(id);

        verify(adminRepository).existsById(id);
        verify(adminRepository).deleteById(id);

    }
}