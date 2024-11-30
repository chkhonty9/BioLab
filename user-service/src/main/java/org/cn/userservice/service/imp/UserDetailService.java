package org.cn.userservice.service.imp;


import org.cn.userservice.dao.PersonRepository;
import org.cn.userservice.entity.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailService implements UserDetailsService {

    private final PersonRepository repository;

    public UserDetailService(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person u = repository.findByEmail(email);
        if(u == null) throw new UsernameNotFoundException("User not found with username: " + email);

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(u.getRole()));
        return new User(u.getEmail(), u.getPassword(), authorities);
    }

}
