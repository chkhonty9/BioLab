package org.cn.userservice.service.imp;

import org.cn.userservice.dao.AdminRepository;
import org.cn.userservice.dao.BiologistRepository;
import org.cn.userservice.dto.AdminDTO;
import org.cn.userservice.dto.BiologistDTO;
import org.cn.userservice.entity.Admin;
import org.cn.userservice.entity.Biologist;
import org.cn.userservice.mapper.BiologistMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BiologistServiceImplTest {

    @Mock
    private BiologistRepository biologistRepository;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private BiologistMapper biologistMapper;

    @InjectMocks
    private BiologistServiceImpl biologistService;

    private Biologist biologist;
    private BiologistDTO biologistDTO;
    Admin admin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create an Admin object
        admin = new Admin();
        admin.setId(2L);
        admin.setFirstName("Nouha");
        admin.setLastName("Chkhonty");
        admin.setEmail("nouha@example.com");
        admin.setPassword("password");
        admin.setRole("ADMIN");
        admin.setLabel("SuperAdmin");

        // Create a Biologist object
        biologist = new Biologist();
        biologist.setId(1L);
        biologist.setFirstName("Jane");
        biologist.setLastName("Smith");
        biologist.setEmail("jane.smith@example.com");
        biologist.setPassword("password");
        biologist.setRole("BIOLOGIST");
        biologist.setLabel("BiologistLabel");
        biologist.setAdmin(admin);

        // Create a BiologistDTO object
        biologistDTO = new BiologistDTO();
        biologistDTO.setId(1L);
        biologistDTO.setFirstName("Jane");
        biologistDTO.setLastName("Smith");
        biologistDTO.setEmail("jane.smith@example.com");
        biologistDTO.setPassword("password");
        biologistDTO.setRole("BIOLOGIST");
        biologistDTO.setLabel("BiologistLabel");
        AdminDTO adminDTO = new AdminDTO();
        BeanUtils.copyProperties(admin, adminDTO);
        biologistDTO.setAdmin(adminDTO);
    }

    @Test
    void save() {

        when(biologistMapper.toBiologist(biologistDTO)).thenReturn(biologist);
        when(biologistMapper.fromBiologist(biologist)).thenReturn(biologistDTO);
        when(biologistRepository.save(biologist)).thenReturn(biologist);

        BiologistDTO result = biologistService.save(biologistDTO);
        assertEquals(biologistDTO, result);
        verify(biologistRepository).save(biologist);

    }

    @Test
    void findAll() {

        when(biologistRepository.findAll()).thenReturn(List.of(biologist));
        when(biologistMapper.fromBiologist(biologist)).thenReturn(biologistDTO);

        List<BiologistDTO> result = biologistService.findAll();
        assertEquals(1, result.size());
        assertEquals(biologistDTO, result.get(0));
        verify(biologistRepository).findAll();
    }

    @Test
    void findOne() {
        Long id = 1L;
        when(biologistRepository.findById(id)).thenReturn(Optional.of(biologist));
        when(biologistMapper.fromBiologist(biologist)).thenReturn(biologistDTO);

        BiologistDTO result = biologistService.findOne(id);
        assertEquals(biologistDTO, result);
        verify(biologistRepository).findById(id);
    }

    @Test
    void delete() {
        Long id = 1L;
        when(biologistRepository.findById(id)).thenReturn(Optional.of(biologist));

        biologistService.delete(id);
        verify(biologistRepository).delete(biologist);
    }

    @Test
    void searchByLabel() {
        String label = "BiologistLabel";
        when(biologistRepository.findByLabelContainingIgnoreCase(label)).thenReturn(List.of(biologist));
        when(biologistMapper.fromBiologist(biologist)).thenReturn(biologistDTO);

        List<BiologistDTO> result = biologistService.searchByLabel(label);
        assertEquals(1, result.size());
        assertEquals(biologistDTO, result.get(0));
        verify(biologistRepository).findByLabelContainingIgnoreCase(label);
    }

    @Test
    void searchByLastName() {
        String lastName = "Jane";
        when(biologistRepository.findByLastNameContainingIgnoreCase(lastName)).thenReturn(List.of(biologist));
        when(biologistMapper.fromBiologist(biologist)).thenReturn(biologistDTO);

        List<BiologistDTO> result = biologistService.searchByLastName(lastName);
        assertEquals(1, result.size());
        assertEquals(biologistDTO, result.get(0));
        verify(biologistRepository).findByLastNameContainingIgnoreCase(lastName);
    }

    @Test
    void searchAdmin() {
        Long adminId = 2L;
        admin.setId(adminId);
        when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));
        when(biologistRepository.findByAdmin(admin)).thenReturn(List.of(biologist));
        when(biologistMapper.fromBiologist(biologist)).thenReturn(biologistDTO);

        List<BiologistDTO> result = biologistService.searchAdmin(adminId);
        assertEquals(1, result.size());
        assertEquals(biologistDTO, result.get(0));
        verify(adminRepository).findById(adminId);
    }
}