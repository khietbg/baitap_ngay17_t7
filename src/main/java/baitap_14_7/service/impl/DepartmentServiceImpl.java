package baitap_14_7.service.impl;

import baitap_14_7.domain.Department;
import baitap_14_7.repository.DepartmentRepository;
import baitap_14_7.service.DepartmentService;
import baitap_14_7.service.dto.DepartmentDTO;
import baitap_14_7.service.mapper.DepartmentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentMapper departmentMapper;
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentMapper departmentMapper, DepartmentRepository departmentRepository) {
        this.departmentMapper = departmentMapper;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentDTO save(DepartmentDTO departmentDTO) {
        Department department = departmentRepository.save(departmentMapper.toEntity(departmentDTO));
        return departmentMapper.toDto(department);
    }

    @Override
    public Page<DepartmentDTO> findAllByNameContainingIgnoreCase(String textSearch, Pageable pageable) {
        return departmentRepository.findAllByNameContainingIgnoreCase(textSearch, pageable).map(departmentMapper::toDto);
    }

    @Override
    public Optional<DepartmentDTO> findOne(Long id) {
        return departmentRepository.findById(id).map(departmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public List<DepartmentDTO> getAll() {
        List<Department> list = departmentRepository.findAll();
        return departmentMapper.toDto(list);
    }


}
