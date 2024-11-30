package org.cn.userservice.mapper;

import lombok.AllArgsConstructor;
import org.cn.userservice.dto.AdminDTO;
import org.cn.userservice.entity.Admin;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AdminMapper {

    public AdminDTO fromAdmin(Admin admin) {
        AdminDTO dto = new AdminDTO();
        BeanUtils.copyProperties(admin, dto);
        return dto;
    }

    public Admin toAdmin(AdminDTO dto) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(dto, admin);
        return admin;
    }
}
