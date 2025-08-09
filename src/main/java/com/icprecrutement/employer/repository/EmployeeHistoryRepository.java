package com.icprecrutement.employer.repository;

import com.icprecrutement.employer.entity.EmployeeHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeHistoryRepository extends MongoRepository<EmployeeHistory, String> {
    List<EmployeeHistory> findByEmployeeIdOrderByTimestampDesc(Long employeeId);
    List<EmployeeHistory> findByActionOrderByTimestampDesc(String action);
    List<EmployeeHistory> findByModifiedByOrderByTimestampDesc(String modifiedBy);
}