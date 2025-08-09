package com.icprecrutement.employer.repository;

import com.icprecrutement.employer.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByPrenomContainingIgnoreCase(String prenom);
    List<Employee> findByNomContainingIgnoreCase(String nom);
    List<Employee> findByPoste(String poste);
    boolean existsByEmail(String email);
}