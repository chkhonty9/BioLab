package org.cn.userservice.web;


import lombok.AllArgsConstructor;
import org.cn.userservice.dto.AdminDTO;
import org.cn.userservice.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class AdminWeb {
    private static final Logger log = LoggerFactory.getLogger(AdminWeb.class);
    private final AdminService adminService;

    @QueryMapping
    public List<AdminDTO> findAllAdmins() {
        log.info("controller : Find all admins");
        return adminService.findAll();
    }

    @QueryMapping
    public AdminDTO findAdminById(@Argument Long id) {
        log.info("controller : Find admin by id: {}", id);
        return adminService.findOne(id);
    }

    @MutationMapping
    public AdminDTO saveAdmin(@Argument AdminDTO admin) {
        log.info("controller : Save admin: {}", admin);
        return adminService.save(admin);
    }

    @MutationMapping
    public Boolean deleteAdmin(@Argument Long id) {
        log.info("controller : Delete admin: {}", id);
        adminService.delete(id);
        return true;
    }

    @MutationMapping
    public AdminDTO updateAdmin(@Argument Long id,@Argument AdminDTO admin) {
        log.info("controller : Update admin: {}", admin);
        if (adminService.findOne(id) == null) {
            return null;
        }
        admin.setId(id);
        return adminService.save(admin);
    }
}
