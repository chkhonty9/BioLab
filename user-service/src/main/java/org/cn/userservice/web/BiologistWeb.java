package org.cn.userservice.web;


import lombok.AllArgsConstructor;


import lombok.extern.slf4j.Slf4j;
import org.cn.userservice.dto.BiologistDTO;
import org.cn.userservice.service.BiologistService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class BiologistWeb {
    private final BiologistService biologistService;

    @QueryMapping
    public List<BiologistDTO> findAllBiologists() {
        log.info("controller : Find all biologists");
        return biologistService.findAll();
    }

    @QueryMapping
    public BiologistDTO findBiologistById(@Argument Long id) {
        log.info("controller : Find biologist by id: {}", id);
        return biologistService.findOne(id);
    }

    @QueryMapping
    public List<BiologistDTO> searchBiologistsByLabel(@Argument String label) {
        log.info("controller : Find biologists by label: {}", label);
        return biologistService.searchByLabel(label);
    }

    @QueryMapping
    public List<BiologistDTO> searchBiologistsByLastName(@Argument String lastName) {
        log.info("controller : Find biologists by last name: {}", lastName);
        return biologistService.searchByLastName(lastName);
    }

    @QueryMapping
    public List<BiologistDTO> searchBiologistsAdmin(@Argument Long id){
        log.info("controller : Find biologists admin by id: {}", id);
        return biologistService.searchAdmin(id);
    }

    @MutationMapping
    public BiologistDTO saveBiologist(@Argument BiologistDTO biologist) {
        log.info("controller : Save biologist: {}", biologist);
        return biologistService.save(biologist);
    }

    @MutationMapping
    public Boolean deleteBiologist(@Argument Long id) {
        log.info("controller : Delete biologist: {}", id);
        biologistService.delete(id);
        return true;
    }

    @MutationMapping
    public BiologistDTO updateBiologist(@Argument Long id, @Argument BiologistDTO biologist) {
        log.info("controller : Update biologist: {}", biologist);
        if(biologistService.findOne(id) == null) {
            return null;
        }
        biologist.setId(id);
        return biologistService.save(biologist);
    }
}
