package org.cn.userservice.mapper;

import lombok.AllArgsConstructor;
import org.cn.userservice.dto.BiologistDTO;
import org.cn.userservice.entity.Biologist;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BiologistMapper {

    private AdminMapper adminMapper;

    public BiologistDTO fromBiologist(Biologist biologist) {
        BiologistDTO biologistDTO = new BiologistDTO();
        BeanUtils.copyProperties(biologist, biologistDTO);
        biologistDTO.setAdmin(adminMapper.fromAdmin(biologist.getAdmin()));
        return biologistDTO;
    }


    public Biologist toBiologist(BiologistDTO biologistDTO) {
        Biologist biologist = new Biologist();
        BeanUtils.copyProperties(biologistDTO, biologist);
        biologist.setAdmin(adminMapper.toAdmin(biologistDTO.getAdmin()));
        return biologist;
    }
}
