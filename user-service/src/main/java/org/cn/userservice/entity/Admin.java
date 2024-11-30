package org.cn.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
public class Admin extends Person{
    private String label;
    @OneToMany(mappedBy = "admin", fetch = FetchType.EAGER)
    private List<Biologist> biologists;
}
