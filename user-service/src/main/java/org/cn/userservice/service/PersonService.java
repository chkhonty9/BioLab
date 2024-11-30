package org.cn.userservice.service;

import org.cn.userservice.dto.PersonDTO;

import java.util.List;

public interface PersonService {
    PersonDTO save(PersonDTO personDTO);
    List<PersonDTO> findAll();
    PersonDTO findOne(Long id);
    void delete(Long id);
    PersonDTO findByEmail(String email);

}
