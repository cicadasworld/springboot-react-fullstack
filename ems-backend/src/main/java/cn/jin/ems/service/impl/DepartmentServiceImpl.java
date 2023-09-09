package cn.jin.ems.service.impl;

import cn.jin.ems.dto.DepartmentDto;
import cn.jin.ems.entity.Department;
import cn.jin.ems.exception.ResourceNotFoundException;
import cn.jin.ems.mapper.DepartmentMapper;
import cn.jin.ems.repository.DepartmentRepository;
import cn.jin.ems.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

	private final DepartmentRepository departmentRepository;

	@Override
	public DepartmentDto createDepartment(DepartmentDto departmentDto) {
		Department department = DepartmentMapper.mapToDepartment(departmentDto);
		Department savedDepartment = departmentRepository.save(department);
		return DepartmentMapper.mapToDepartmentDto(savedDepartment);
	}

	@Override
	public DepartmentDto getDepartmentById(Long departmentId) {
		Department department = departmentRepository.findById(departmentId)
			.orElseThrow(() -> new ResourceNotFoundException("Department not exist with id: " + departmentId));
		return DepartmentMapper.mapToDepartmentDto(department);
	}

	@Override
	public List<DepartmentDto> getAllDepartments() {
		List<Department> departments = departmentRepository.findAll();
		return departments.stream().map(DepartmentMapper::mapToDepartmentDto).toList();
	}

	@Override
	public DepartmentDto updateDepartment(Long departmentId, DepartmentDto departmentDto) {
		Department department = departmentRepository.findById(departmentId)
			.orElseThrow(() -> new ResourceNotFoundException("Department not exist with id: " + departmentId));
		department.setDepartmentName(departmentDto.getDepartmentName());
		department.setDepartmentDescription(departmentDto.getDepartmentDescription());
		Department savedDepartment = departmentRepository.save(department);
		return DepartmentMapper.mapToDepartmentDto(savedDepartment);
	}

	@Override
	public void deleteDepartment(Long departmentId) {
		departmentRepository.findById(departmentId)
			.orElseThrow(() -> new ResourceNotFoundException("Department not exist with id: " + departmentId));
		departmentRepository.deleteById(departmentId);
	}

}
