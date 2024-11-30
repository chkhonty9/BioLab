package org.cn.userservice.service.imp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cn.userservice.dao.AdminRepository;
import org.cn.userservice.dao.BiologistRepository;
import org.cn.userservice.dto.BiologistDTO;
import org.cn.userservice.entity.Admin;
import org.cn.userservice.mapper.BiologistMapper;
import org.cn.userservice.service.BiologistService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BiologistServiceImpl implements BiologistService {

    private final BiologistRepository biologistRepository;
    private final AdminRepository adminRepository;
    private final BiologistMapper biologistMapper;

    @Override
    public BiologistDTO save(BiologistDTO biologistDTO) {
        log.info("save Biologist DTO: {}", biologistDTO);
        return biologistMapper.fromBiologist(
                biologistRepository.save(biologistMapper.toBiologist(biologistDTO))
        );
    }

    @Override
    public List<BiologistDTO> findAll() {
        log.info("find Biologist List");
        return biologistRepository.findAll().stream()
                .map(biologistMapper::fromBiologist)
                .collect(Collectors.toList());
    }

    @Override
    public BiologistDTO findOne(Long id) {
        log.info("find Biologist DTO by id: {}", id);
        return biologistRepository.findById(id)
                .map(biologistMapper::fromBiologist)
                .orElse(null);
    }

    @Override
    public void delete(Long id) {
        log.info("delete Biologist DTO: {}", id);
        biologistRepository.findById(id).ifPresent(biologistRepository::delete);
    }

    @Override
    public List<BiologistDTO> searchByLabel(String label) {
        log.info("find Biologist List by label");
        return biologistRepository.findByLabelContainingIgnoreCase(label).stream()
                .map(biologistMapper::fromBiologist)
                .collect(Collectors.toList());
    }

    @Override
    public List<BiologistDTO> searchByLastName(String lastName) {
        log.info("find Biologist List by last name");
        return biologistRepository.findByLastNameContainingIgnoreCase(lastName).stream()
                .map(biologistMapper::fromBiologist)
                .collect(Collectors.toList());
    }

    @Override
    public List<BiologistDTO> searchAdmin(Long id) {
        log.info("find Biologist List by admin");
        Admin admin = adminRepository.findById(id).orElse(null);
        return biologistRepository.findByAdmin(admin).stream().map(biologistMapper::fromBiologist).toList();
    }
}
