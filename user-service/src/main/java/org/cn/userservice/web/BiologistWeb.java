package org.cn.userservice.web;


import lombok.AllArgsConstructor;


import org.cn.userservice.dto.BiologistDTO;
import org.cn.userservice.service.BiologistService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class BiologistWeb {
    private final BiologistService biologistService;

    @QueryMapping
    public List<BiologistDTO> findAllBiologists() {
        return biologistService.findAll();
    }

    @QueryMapping
    public BiologistDTO findBiologistById(@Argument Long id) {
        return biologistService.findOne(id);
    }

    @QueryMapping
    public List<BiologistDTO> searchBiologistsByLabel(@Argument String label) {
        return biologistService.searchByLabel(label);
    }

    @QueryMapping
    public List<BiologistDTO> searchBiologistsByLastName(@Argument String lastName) {
        return biologistService.searchByLastName(lastName);
    }

    @QueryMapping
    public List<BiologistDTO> searchBiologistsAdmin(@Argument Long id){
        return biologistService.searchAdmin(id);
    }

    @MutationMapping
    public BiologistDTO saveBiologist(@Argument BiologistDTO biologist) {
        return biologistService.save(biologist);
    }

    @MutationMapping
    public Boolean deleteBiologist(@Argument Long id) {
        biologistService.delete(id);
        return true;
    }

    @MutationMapping
    public BiologistDTO updateBiologist(@Argument Long id, @Argument BiologistDTO biologist) {
        if(biologistService.findOne(id) == null) {
            return null;
        }
        biologist.setId(id);
        return biologistService.save(biologist);
    }
}
