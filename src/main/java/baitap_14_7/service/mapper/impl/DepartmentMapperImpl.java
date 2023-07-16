package baitap_14_7.service.mapper.impl;

import baitap_14_7.domain.Department;
import baitap_14_7.service.dto.DepartmentDTO;
import baitap_14_7.service.mapper.DepartmentMapper;

import java.util.ArrayList;
import java.util.List;

public class DepartmentMapperImpl implements DepartmentMapper {
    @Override
    public Department toEntity(DepartmentDTO dto) {
        if (dto == null) {
            return null;
        }

        Department department = new Department();
        department.setId(dto.getId());
        department.setName(dto.getName());
        department.setDescription(dto.getDescription());
        return department;

    }

    @Override
    public DepartmentDTO toDto(Department entity) {
        if (entity == null) {
            return null;
        }

        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(entity.getId());
        departmentDTO.setName(entity.getName());
        departmentDTO.setDescription(entity.getDescription());
        return departmentDTO;
    }

    @Override
    public List<Department> toEntity(List<DepartmentDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<Department> departments = new ArrayList<>();
        for (DepartmentDTO dto : dtoList) {
            departments.add(toEntity(dto));
        }
        return departments;

    }

    @Override
    public List<DepartmentDTO> toDto(List<Department> entityList) {
        if (entityList == null) {
            return null;
        }

        List<DepartmentDTO> dtoList = new ArrayList<>();
        for (Department department : entityList) {
            dtoList.add(toDto(department));
        }
        return dtoList;
    }
}
