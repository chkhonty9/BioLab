package org.cn.userservice.web;

import org.cn.userservice.dto.AdminDTO;
import org.cn.userservice.dto.BiologistDTO;
import org.cn.userservice.entity.Admin;
import org.cn.userservice.service.BiologistService;
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

class BiologistWebTest {

    @Mock
    private BiologistService biologistService;

    @InjectMocks
    private BiologistWeb biologistWeb;

    private BiologistDTO biologistDTO;
    private AdminDTO admin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        admin = new AdminDTO();
        admin.setId(2L);
        admin.setFirstName("Nouha");
        admin.setLastName("Chkhonty");
        admin.setEmail("nouha@example.com");
        admin.setPassword("password");
        admin.setRole("ADMIN");
        admin.setLabel("SuperAdmin");

        biologistDTO = new BiologistDTO();
        biologistDTO.setId(1L);
        biologistDTO.setFirstName("John");
        biologistDTO.setLastName("Doe");
        biologistDTO.setEmail("doe@example.com");
        biologistDTO.setPassword("password");
        biologistDTO.setRole("BIOLOGIST");
        biologistDTO.setLabel("Molecular Biologist");
        biologistDTO.setAdmin(admin);


    }


    @Test
    void findAllBiologists() {
        when(biologistService.findAll()).thenReturn(Arrays.asList(biologistDTO));

        List<BiologistDTO> result = biologistWeb.findAllBiologists();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(biologistDTO, result.get(0));
        verify(biologistService).findAll();
    }

    @Test
    void findBiologistById() {
        Long id = 1L;

        when(biologistService.findOne(id)).thenReturn(biologistDTO);

        BiologistDTO result = biologistWeb.findBiologistById(id);

        assertNotNull(result);
        assertEquals(biologistDTO, result);
        //assertEquals(biologistDTO.getFirstName(), result.getFirstName());
        verify(biologistService).findOne(id);
    }

    @Test
    void searchBiologistsByLabel() {
        String label = "Molecular Biologist";

        when(biologistService.searchByLabel(label)).thenReturn(Arrays.asList(biologistDTO));

        List<BiologistDTO> result = biologistWeb.searchBiologistsByLabel(label);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(biologistDTO, result.get(0));
        verify(biologistService).searchByLabel(label);
    }

    @Test
    void searchBiologistsByLastName() {

        String lastName = "Doe";

        when(biologistService.searchByLastName(lastName)).thenReturn(Arrays.asList(biologistDTO));

        List<BiologistDTO> result = biologistWeb.searchBiologistsByLastName(lastName);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(biologistDTO, result.get(0));
        verify(biologistService).searchByLastName(lastName);
    }

    @Test
    void searchBiologistsAdmin() {
        Long id = 2L;

        when(biologistService.searchAdmin(id)).thenReturn(Arrays.asList(biologistDTO));

        List<BiologistDTO> result = biologistWeb.searchBiologistsAdmin(id);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(biologistService).searchAdmin(id);
    }

    @Test
    void saveBiologist() {

        when(biologistService.save(biologistDTO)).thenReturn(biologistDTO);

        BiologistDTO result = biologistWeb.saveBiologist(biologistDTO);

        assertNotNull(result);
        assertEquals(biologistDTO, result);
        //assertEquals(biologistDTO.getFirstName(), result.getFirstName());
        verify(biologistService).save(biologistDTO);
    }

    @Test
    void deleteBiologist() {

        Long id = 1L;

        Boolean result = biologistWeb.deleteBiologist(id);

        assertTrue(result);
        verify(biologistService).delete(id);
    }

    @Test
    void updateBiologist() {
        Long id = 1L;

        BiologistDTO updatedBiologist = new BiologistDTO();
        updatedBiologist.setId(id);
        updatedBiologist.setFirstName("UpdatedJohn");
        updatedBiologist.setLastName("UpdatedDoe");
        updatedBiologist.setLabel("Updated Biologist");

        when(biologistService.findOne(id)).thenReturn(biologistDTO);
        when(biologistService.save(updatedBiologist)).thenReturn(updatedBiologist);

        BiologistDTO result = biologistWeb.updateBiologist(id, updatedBiologist);

        assertNotNull(result);
        assertEquals(updatedBiologist, result);
        //assertEquals(updatedBiologist.getFirstName(), result.getFirstName());
        verify(biologistService).findOne(id);
        verify(biologistService).save(updatedBiologist);
    }
}