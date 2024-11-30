package org.cn.userservice.service;

import org.cn.userservice.dto.AdminDTO;
import org.cn.userservice.dto.BiologistDTO;

import java.util.List;

public interface AdminService {
    AdminDTO save(AdminDTO adminDTO);
    AdminDTO findOne(Long id);
    List<AdminDTO> findAll();
    void delete(Long id);
}
