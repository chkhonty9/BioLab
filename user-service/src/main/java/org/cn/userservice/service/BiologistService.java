package org.cn.userservice.service;

import org.cn.userservice.dto.BiologistDTO;

import java.util.List;

public interface BiologistService {
    BiologistDTO save(BiologistDTO biologistDTO);
    List<BiologistDTO> findAll();
    BiologistDTO findOne(Long id);
    void delete(Long id);
    List<BiologistDTO> searchByLabel(String label);
    List<BiologistDTO> searchByLastName(String lastName);
    List<BiologistDTO> searchAdmin(Long id);
}
