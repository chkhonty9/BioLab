package org.cn.userservice.web;

import lombok.AllArgsConstructor;
import org.cn.userservice.dto.PersonDTO;
import org.cn.userservice.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TestAPI {

    private PersonService personService;

    @GetMapping("/test")
    public List<PersonDTO> getAllPersons() {
        return this.personService.findAll();
    }

}
