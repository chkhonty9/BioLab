package org.cn.userservice.dao;

import org.assertj.core.api.AssertionsForClassTypes;
import org.cn.userservice.configuration.RSAConfig;
import org.cn.userservice.configuration.SecurityConfig;
import org.cn.userservice.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;



@DataJpaTest
@ActiveProfiles("test")
class PersonRepositoryTest {

    @MockBean
    private SecurityConfig securityConfig;

    @MockBean
    private RSAConfig rsaConfig;

    @Autowired
    private PersonRepository personRepository;



    @BeforeEach
    void setUp() {
        personRepository.save(new Person(1L,"Nouhaila","Chkhonty", "nouha@gmail.com","nouhaila", "BIOLOGIST" ));
    }

    @Test
    public void findByEmail() {
        Person person = new Person(1L,"Nouhaila","Chkhonty", "nouha@gmail.com","nouhaila", "BIOLOGIST" );
        Person foundPerson = personRepository.findByEmail("nouha@gmail.com");

        AssertionsForClassTypes.assertThat(foundPerson).isNotNull();
        AssertionsForClassTypes.assertThat(foundPerson).usingRecursiveComparison().ignoringFields("id").isEqualTo(person);
    }

    @Test
    public void testFindByEmailNotFound() {

        Person foundPerson = personRepository.findByEmail("no@example.com");

        AssertionsForClassTypes.assertThat(foundPerson).isNull();


    }
}