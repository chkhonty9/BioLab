package org.cn.userservice.service.imp;

import org.cn.userservice.dao.PersonRepository;
import org.cn.userservice.dto.PersonDTO;
import org.cn.userservice.entity.Person;
import org.cn.userservice.mapper.PersonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceImplTest {

    @InjectMocks
    private PersonServiceImpl personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Person person;
    private PersonDTO personDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        person = new Person();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("john.doe@example.com");
        person.setPassword("password");

        personDTO = new PersonDTO();
        personDTO.setId(1L);
        personDTO.setFirstName("John");
        personDTO.setLastName("Doe");
        personDTO.setEmail("john.doe@example.com");
        personDTO.setPassword("password");
    }

    @Test
    void save() {

        when(passwordEncoder.encode(personDTO.getPassword())).thenReturn("password");
        when(personMapper.toPerson(personDTO)).thenReturn(person);
        when(personRepository.save(person)).thenReturn(person);
        when(personMapper.fromPerson(person)).thenReturn(personDTO);

        PersonDTO result = personService.save(personDTO);

        assertNotNull(result);
        assertEquals(personDTO.getEmail(), result.getEmail());
        verify(passwordEncoder, times(1)).encode(personDTO.getPassword());
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void findAll() {

        List<Person> persons = List.of(person);
        List<PersonDTO> personDTOs = List.of(personDTO);
        when(personRepository.findAll()).thenReturn(persons);
        when(personMapper.fromPerson(person)).thenReturn(personDTO);

        List<PersonDTO> result = personService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(personDTO, result.get(0));
        verify(personRepository, times(1)).findAll();
    }

    @Test
    void findOne() {

        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        when(personMapper.fromPerson(person)).thenReturn(personDTO);

        PersonDTO result = personService.findOne(1L);

        assertNotNull(result);
        assertEquals(personDTO, result);
        verify(personRepository, times(1)).findById(1L);
    }

    @Test
    void delete() {

        when(personRepository.existsById(1L)).thenReturn(true);

        personService.delete(1L);

        verify(personRepository, times(1)).deleteById(1L);
    }

    @Test
    void findByEmail() {

        when(personRepository.findByEmail("john.doe@example.com")).thenReturn(person);
        when(personMapper.fromPerson(person)).thenReturn(personDTO);

        PersonDTO result = personService.findByEmail("john.doe@example.com");

        assertNotNull(result);
        assertEquals(personDTO, result);
        verify(personRepository, times(1)).findByEmail("john.doe@example.com");
    }
}