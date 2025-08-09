package com.icprecrutement.employer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "employee_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeHistory {

    @Id
    private String id;

    private Long employeeId;
    private String action; // CREATE, UPDATE, DELETE
    private LocalDateTime timestamp;
    private String modifiedBy; // Utilisateur qui a fait la modification

    // Données avant modification (null pour CREATE)
    private EmployeeData oldData;

    // Données après modification (null pour DELETE)
    private EmployeeData newData;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmployeeData {
        private String nom;
        private String prenom;
        private String poste;
        private String email;
        private LocalDate dateEmbauche;
    }
}