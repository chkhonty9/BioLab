package org.cn.userservice.web;

import org.cn.userservice.dto.PersonDTO;
import org.cn.userservice.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonWebTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonWeb personWeb;

    private PersonDTO personDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        personDTO = new PersonDTO();
        personDTO.setId(1L);
        personDTO.setEmail("nouha@example.com");
        personDTO.setFirstName("nouha");
        personDTO.setLastName("chkhonty");
        personDTO.setPassword("password");
        personDTO.setRole("Person");
    }

    @Test
    void findAllPersons() {
        when(personService.findAll()).thenReturn(List.of(personDTO));

        List<PersonDTO> result = personWeb.findAllPersons();
        assertEquals(1, result.size());
        assertEquals(personDTO, result.get(0));
        verify(personService).findAll();
    }

    @Test
    void findPersonById() {
        Long id = 1L;
        when(personService.findOne(id)).thenReturn(personDTO);

        PersonDTO result = personWeb.findPersonById(id);
        assertEquals(personDTO, result);
        verify(personService).findOne(id);
    }

    @Test
    void findPersonByEmail() {
        String email = "nouha@example.com";
        when(personService.findByEmail(email)).thenReturn(personDTO);

        PersonDTO result = personWeb.findPersonByEmail(email);
        assertEquals(personDTO, result);
        verify(personService).findByEmail(email);
    }

    @Test
    void savePerson() {
        when(personService.save(personDTO)).thenReturn(personDTO);

        PersonDTO result = personWeb.savePerson(personDTO);
        assertEquals(personDTO, result);
        verify(personService).save(personDTO);
    }

    @Test
    void deletePerson() {
        Long id = 1L;
        doNothing().when(personService).delete(id);

        Boolean result = personWeb.deletePerson(id);
        assertTrue(result);
        verify(personService).delete(id);
    }

    @Test
    void updatePerson() {

        Long id = 1L;

        when(personService.findOne(id)).thenReturn(personDTO);

        PersonDTO updatedPerson = new PersonDTO();
        updatedPerson.setId(id);
        updatedPerson.setFirstName("Jane");
        updatedPerson.setLastName("Smith");
        updatedPerson.setEmail("jane@example.com");
        updatedPerson.setPassword("password");
        updatedPerson.setRole("Person");

        when(personService.save(updatedPerson)).thenReturn(updatedPerson);

        PersonDTO result = personWeb.updatePerson(id, updatedPerson);

        assertNotNull(result);
        assertEquals(updatedPerson.getId(), result.getId());
        assertEquals(updatedPerson, result);
        //assertEquals(updatedPerson.getLastName(), result.getLastName());
        verify(personService).findOne(id);
        verify(personService).save(updatedPerson);
    }
}