package org.cn.userservice.web;

import org.cn.userservice.dto.AdminDTO;
import org.cn.userservice.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdminWebTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminWeb adminWeb;

    private AdminDTO adminDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Prepare a sample AdminDTO
        adminDTO = new AdminDTO();
        adminDTO.setId(1L);
        adminDTO.setLastName("admin");
        adminDTO.setFirstName("admin");
        adminDTO.setPassword("password");
        adminDTO.setEmail("admin@example.com");
        adminDTO.setRole("ADMIN");
        adminDTO.setLabel("Label");
    }

    @Test
    void findAllAdmins() {
        when(adminService.findAll()).thenReturn(Arrays.asList(adminDTO));

        List<AdminDTO> result = adminWeb.findAllAdmins();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(adminDTO, result.get(0));
        verify(adminService).findAll();
    }

    @Test
    void findAdminById() {
        Long id = 1L;

        when(adminService.findOne(id)).thenReturn(adminDTO);

        AdminDTO result = adminWeb.findAdminById(id);

        assertNotNull(result);
        assertEquals(adminDTO.getId(), result.getId());
        assertEquals(adminDTO, result);
        verify(adminService).findOne(id);

    }

    @Test
    void saveAdmin() {

        when(adminService.save(adminDTO)).thenReturn(adminDTO);

        AdminDTO result = adminWeb.saveAdmin(adminDTO);

        assertNotNull(result);
        assertEquals(adminDTO.getId(), result.getId());
        assertEquals(adminDTO, result);
        verify(adminService).save(adminDTO);

    }

    @Test
    void deleteAdmin() {

        Long id = 1L;

        Boolean result = adminWeb.deleteAdmin(id);

        assertTrue(result);
        verify(adminService).delete(id);

    }

    @Test
    void updateAdmin() {
        Long id = 1L;
        AdminDTO updatedAdmin = new AdminDTO();
        updatedAdmin.setId(id);
        updatedAdmin.setFirstName("updatedAdmin");
        updatedAdmin.setPassword("newPassword");
        updatedAdmin.setEmail("updated@example.com");


        when(adminService.findOne(id)).thenReturn(adminDTO);
        when(adminService.save(updatedAdmin)).thenReturn(updatedAdmin);

        AdminDTO result = adminWeb.updateAdmin(id, updatedAdmin);

        assertNotNull(result);
        assertEquals(updatedAdmin.getId(), result.getId());
        assertEquals(updatedAdmin, result);
        verify(adminService).findOne(id);
        verify(adminService).save(updatedAdmin);
    }
}