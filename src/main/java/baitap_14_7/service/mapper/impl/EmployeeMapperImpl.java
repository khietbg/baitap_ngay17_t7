package baitap_14_7.service.mapper.impl;

import baitap_14_7.domain.Department;
import baitap_14_7.domain.Employee;
import baitap_14_7.domain.Role;
import baitap_14_7.service.dto.EmployeeDTO;
import baitap_14_7.service.mapper.EmployeeMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeMapperImpl implements EmployeeMapper {
    @Override
    public Employee toEntity(EmployeeDTO dto) {
        if (dto == null){
            return null;
        }

        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setDepartmentId(dto.getDepartmentId());
        return employee;
    }

    @Override
    public EmployeeDTO toDto(Employee entity) {
        if (entity == null){
            return null;
        }

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(entity.getId());
        employeeDTO.setName(entity.getName());
        employeeDTO.setEmail(entity.getEmail());
        employeeDTO.setDepartmentId(entity.getDepartmentId());

        Department department = entity.getDepartment();
        if (department != null){
           employeeDTO.setDepartmentName(department.getName());
        }

        Set<Role> roles = entity.getRoles();
        if (roles != null){
            employeeDTO.setRoles(roles.stream().map(Role::getName).collect(Collectors.toSet()));
        }
        return employeeDTO;
    }

    @Override
    public List<Employee> toEntity(List<EmployeeDTO> dtoList) {
        if (dtoList == null){
            return null;
        }

        List<Employee> employees = new ArrayList<>();
        for (EmployeeDTO dto:dtoList) {
            employees.add(toEntity(dto));
        }
        return employees;
    }

    @Override
    public List<EmployeeDTO> toDto(List<Employee> entityList) {
        if (entityList == null){
            return null;
        }

        List<EmployeeDTO> dtoList = new ArrayList<>();
        for (Employee e:entityList) {
            dtoList.add(toDto(e));
        }
        return dtoList;
    }
}
