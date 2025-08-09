package com.icprecrutement.employer.controller;

import com.icprecrutement.employer.entity.Employee;
import com.icprecrutement.employer.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    
    private final EmployeeService employeeService;
    
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(employee -> ResponseEntity.ok(employee))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee employee) {
        try {
            Employee createdEmployee = employeeService.createEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, 
                                          @Valid @RequestBody Employee employeeDetails) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
            return ResponseEntity.ok(updatedEmployee);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok("Employé supprimé avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/search/prenom/{prenom}")
    public ResponseEntity<List<Employee>> searchByPrenom(@PathVariable String prenom) {
        List<Employee> employees = employeeService.searchByPrenom(prenom);
        return ResponseEntity.ok(employees);
    }
    
    @GetMapping("/search/nom/{nom}")
    public ResponseEntity<List<Employee>> searchByNom(@PathVariable String nom) {
        List<Employee> employees = employeeService.searchByNom(nom);
        return ResponseEntity.ok(employees);
    }
    
    @GetMapping("/poste/{poste}")
    public ResponseEntity<List<Employee>> getEmployeesByPoste(@PathVariable String poste) {
        List<Employee> employees = employeeService.getEmployeesByPoste(poste);
        return ResponseEntity.ok(employees);
    }
}