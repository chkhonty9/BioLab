package org.cn.userservice.dao;

import org.cn.userservice.entity.Admin;
import org.cn.userservice.entity.Biologist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BiologistRepository extends JpaRepository<Biologist, Long> {
    List<Biologist> findByLabelContainingIgnoreCase(String label);
    List<Biologist> findByLastNameContainingIgnoreCase(String lastName);
    List<Biologist> findByAdmin(Admin admin);
}
