package org.cn.userservice.service.imp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cn.userservice.dao.PersonRepository;
import org.cn.userservice.dto.PersonDTO;
import org.cn.userservice.entity.Person;
import org.cn.userservice.mapper.PersonMapper;
import org.cn.userservice.service.PersonService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;
    private PersonMapper personMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PersonDTO save(PersonDTO personDTO) {
        log.info("Saving person: {}", personDTO);
        personDTO.setPassword(passwordEncoder.encode(personDTO.getPassword()));
        return personMapper.fromPerson(personRepository.save(personMapper.toPerson(personDTO)));
    }

    @Override
    public List<PersonDTO> findAll() {
        log.info("Finding all persons");
        return personRepository.findAll().stream().toList().stream().map(p-> personMapper.fromPerson(p)).toList();
    }

    @Override
    public PersonDTO findOne(Long id) {
        log.info("Finding person by id: {}", id);
        return personMapper.fromPerson(personRepository.findById(id).orElse(null));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting person by id: {}", id);
        if(personRepository.existsById(id)) {
            personRepository.deleteById(id);
        }
    }

    @Override
    public PersonDTO findByEmail(String email) {
        log.info("Finding person by email: {}", email);
        return personMapper.fromPerson(personRepository.findByEmail(email));
    }
}
