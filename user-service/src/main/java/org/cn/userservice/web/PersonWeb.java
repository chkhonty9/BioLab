package org.cn.userservice.web;

import lombok.AllArgsConstructor;
import org.cn.userservice.dto.PersonDTO;
import org.cn.userservice.service.PersonService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@AllArgsConstructor
public class PersonWeb {

    private final PersonService personService;

    @QueryMapping
    public List<PersonDTO> findAllPersons() {
        return personService.findAll();
    }

    @QueryMapping
    public PersonDTO findPersonById(@Argument Long id) {
        return personService.findOne(id);
    }

    @QueryMapping
    public PersonDTO findPersonByEmail(@Argument String email) {
        return personService.findByEmail(email);
    }

    @MutationMapping
    public PersonDTO savePerson(@Argument PersonDTO person) {
        return personService.save(person);
    }

    @MutationMapping
    public Boolean deletePerson(@Argument Long id) {
        personService.delete(id);
        return true;
    }

    @MutationMapping
    public PersonDTO updatePerson(@Argument Long id,@Argument PersonDTO person) {
        if(personService.findOne(id) == null) {
            return null;
        }
        person.setId(id);
        return personService.save(person);
    }

}
