package baitap_14_7.repository;

import baitap_14_7.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findAllByNameContainingIgnoreCase(String textSearch, Pageable pageable);
    boolean existsByEmail(String email);
    Optional<Employee> findByEmail(String email);
}
