package org.cn.userservice.service.imp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cn.userservice.dao.AdminRepository;
import org.cn.userservice.dto.AdminDTO;
import org.cn.userservice.mapper.AdminMapper;
import org.cn.userservice.service.AdminService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {
    private AdminRepository adminRepository;
    private AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminDTO save(AdminDTO adminDTO) {
        log.info("Saving AdminDTO: {}", adminDTO);
        adminDTO.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        return adminMapper.fromAdmin(adminRepository.save(adminMapper.toAdmin(adminDTO)));
    }

    @Override
    public AdminDTO findOne(Long id) {
        log.info("Finding AdminDTO: {}", id);

        return adminMapper.fromAdmin(adminRepository.findById(id).orElse(null));
    }

    @Override
    public List<AdminDTO> findAll() {
        log.info("Finding All AdminDTOs");

        return adminRepository.findAll().stream().map(a->adminMapper.fromAdmin(a)).toList();
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting AdminDTO: {}", id);
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
        }

    }
}
