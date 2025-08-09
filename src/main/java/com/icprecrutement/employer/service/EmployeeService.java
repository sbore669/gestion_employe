package com.icprecrutement.employer.service;

import com.icprecrutement.employer.entity.Employee;
import com.icprecrutement.employer.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    
    private final EmployeeRepository employeeRepository;
    
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
    
    public Employee createEmployee(Employee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new RuntimeException("Un employé avec cet email existe déjà");
        }
        return employeeRepository.save(employee);
    }
    
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé avec l'id: " + id));
        
        // Vérifier si l'email est déjà utilisé par un autre employé
        if (!employee.getEmail().equals(employeeDetails.getEmail()) && 
            employeeRepository.existsByEmail(employeeDetails.getEmail())) {
            throw new RuntimeException("Un employé avec cet email existe déjà");
        }
        
        employee.setNom(employeeDetails.getNom());
        employee.setPrenom(employeeDetails.getPrenom());
        employee.setPoste(employeeDetails.getPoste());
        employee.setEmail(employeeDetails.getEmail());
        employee.setDateEmbauche(employeeDetails.getDateEmbauche());
        
        return employeeRepository.save(employee);
    }
    
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employé non trouvé avec l'id: " + id);
        }
        employeeRepository.deleteById(id);
    }
    
    public List<Employee> searchByPrenom(String prenom) {
        return employeeRepository.findByPrenomContainingIgnoreCase(prenom);
    }
    
    public List<Employee> searchByNom(String nom) {
        return employeeRepository.findByNomContainingIgnoreCase(nom);
    }
    
    public List<Employee> getEmployeesByPoste(String poste) {
        return employeeRepository.findByPoste(poste);
    }
}