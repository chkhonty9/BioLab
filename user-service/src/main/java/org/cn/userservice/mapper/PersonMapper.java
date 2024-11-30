package org.cn.userservice.mapper;

import lombok.AllArgsConstructor;
import org.cn.userservice.dto.PersonDTO;
import org.cn.userservice.entity.Person;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonMapper {

    public PersonDTO fromPerson(Person person) {
        PersonDTO personDTO = new PersonDTO();
        BeanUtils.copyProperties(person, personDTO);
        return personDTO;
    }

    public Person toPerson(PersonDTO personDTO) {
        Person person = new Person();
        BeanUtils.copyProperties(personDTO, person);
        return person;
    }

}
