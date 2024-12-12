package org.cn.userservice.mapper;

import org.assertj.core.api.AssertionsForClassTypes;
import org.cn.userservice.dto.PersonDTO;
import org.cn.userservice.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class PersonMapperTest {

    private PersonMapper personMapper;
    private Person person;
    private PersonDTO personDTO;

    @BeforeEach
    void setUp() {
        personMapper = new PersonMapper();

        // Create a Person object
        person = new Person();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("john.doe@example.com");
        person.setPassword("encodedPassword123");
        person.setRole("BIOLOGIST");

        // Create a PersonDTO object
        personDTO = new PersonDTO();
        personDTO.setId(1L);
        personDTO.setFirstName("John");
        personDTO.setLastName("Doe");
        personDTO.setEmail("john.doe@example.com");
        personDTO.setPassword("encodedPassword123");
        personDTO.setRole("BIOLOGIST");
    }

    @Test
    void fromPerson() {
        PersonDTO result = personMapper.fromPerson(person);

        assertNotNull(result);
        AssertionsForClassTypes.assertThat(personDTO).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    void toPerson() {

        Person result = personMapper.toPerson(personDTO);

        assertNotNull(result);
        AssertionsForClassTypes.assertThat(person).usingRecursiveComparison().isEqualTo(result);
    }
}