package com.icprecrutement.employer.service;

import com.icprecrutement.employer.entity.Employee;
import com.icprecrutement.employer.entity.EmployeeHistory;
import com.icprecrutement.employer.repository.EmployeeHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeHistoryService {
    
    private final EmployeeHistoryRepository historyRepository;
    private final AuthenticationService authenticationService;
    
    public void saveCreateHistory(Employee employee) {
        EmployeeHistory history = new EmployeeHistory();
        history.setEmployeeId(employee.getId());
        history.setAction("CREATE");
        history.setTimestamp(LocalDateTime.now());
        history.setModifiedBy(authenticationService.getCurrentUsername());
        history.setOldData(null);
        history.setNewData(convertToEmployeeData(employee));
        
        historyRepository.save(history);
    }
    
    public void saveUpdateHistory(Employee oldEmployee, Employee newEmployee) {
        EmployeeHistory history = new EmployeeHistory();
        history.setEmployeeId(newEmployee.getId());
        history.setAction("UPDATE");
        history.setTimestamp(LocalDateTime.now());
        history.setModifiedBy(authenticationService.getCurrentUsername());
        history.setOldData(convertToEmployeeData(oldEmployee));
        history.setNewData(convertToEmployeeData(newEmployee));
        
        historyRepository.save(history);
    }
    
    public void saveDeleteHistory(Employee employee) {
        EmployeeHistory history = new EmployeeHistory();
        history.setEmployeeId(employee.getId());
        history.setAction("DELETE");
        history.setTimestamp(LocalDateTime.now());
        history.setModifiedBy(authenticationService.getCurrentUsername());
        history.setOldData(convertToEmployeeData(employee));
        history.setNewData(null);
        
        historyRepository.save(history);
    }
    
    public List<EmployeeHistory> getEmployeeHistory(Long employeeId) {
        return historyRepository.findByEmployeeIdOrderByTimestampDesc(employeeId);
    }
    
    public List<EmployeeHistory> getAllHistory() {
        return historyRepository.findAll();
    }
    
    public List<EmployeeHistory> getHistoryByUser(String username) {
        return historyRepository.findByModifiedByOrderByTimestampDesc(username);
    }
    
    private EmployeeHistory.EmployeeData convertToEmployeeData(Employee employee) {
        return new EmployeeHistory.EmployeeData(
            employee.getNom(),
            employee.getPrenom(),
            employee.getPoste(),
            employee.getEmail(),
            employee.getDateEmbauche()
        );
    }
}