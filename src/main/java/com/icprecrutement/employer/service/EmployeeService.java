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
    private final EmployeeHistoryService historyService;

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
        Employee savedEmployee = employeeRepository.save(employee);

        // Sauvegarder l'historique de création
        historyService.saveCreateHistory(savedEmployee);

        return savedEmployee;
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee oldEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé avec l'id: " + id));

        // Créer une copie des anciennes données pour l'historique
        Employee oldEmployeeCopy = new Employee();
        oldEmployeeCopy.setId(oldEmployee.getId());
        oldEmployeeCopy.setNom(oldEmployee.getNom());
        oldEmployeeCopy.setPrenom(oldEmployee.getPrenom());
        oldEmployeeCopy.setPoste(oldEmployee.getPoste());
        oldEmployeeCopy.setEmail(oldEmployee.getEmail());
        oldEmployeeCopy.setDateEmbauche(oldEmployee.getDateEmbauche());

        // Vérifier si l'email est déjà utilisé par un autre employé
        if (!oldEmployee.getEmail().equals(employeeDetails.getEmail()) &&
                employeeRepository.existsByEmail(employeeDetails.getEmail())) {
            throw new RuntimeException("Un employé avec cet email existe déjà");
        }

        oldEmployee.setNom(employeeDetails.getNom());
        oldEmployee.setPrenom(employeeDetails.getPrenom());
        oldEmployee.setPoste(employeeDetails.getPoste());
        oldEmployee.setEmail(employeeDetails.getEmail());
        oldEmployee.setDateEmbauche(employeeDetails.getDateEmbauche());

        Employee updatedEmployee = employeeRepository.save(oldEmployee);

        // Sauvegarder l'historique de modification
        historyService.saveUpdateHistory(oldEmployeeCopy, updatedEmployee);

        return updatedEmployee;
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé avec l'id: " + id));

        // Sauvegarder l'historique de suppression avant de supprimer
        historyService.saveDeleteHistory(employee);

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