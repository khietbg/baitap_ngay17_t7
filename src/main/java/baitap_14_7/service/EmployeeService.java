package baitap_14_7.service;

import baitap_14_7.domain.Employee;
import baitap_14_7.service.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    EmployeeDTO save(EmployeeDTO employeeDTO);

    Page<EmployeeDTO> findAllByNameContainingIgnoreCase(String textSearch, Pageable pageable);

    Optional<EmployeeDTO> findOne(Long id);

    void delete(Long id);

    Optional<EmployeeDTO> findByEmail(String email);

    boolean existsByEmail(String email);

    List<EmployeeDTO> findAll();

}
