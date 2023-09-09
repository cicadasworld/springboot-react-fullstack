package cn.jin.ems.service.impl;

import cn.jin.ems.dto.EmployeeDto;
import cn.jin.ems.entity.Department;
import cn.jin.ems.entity.Employee;
import cn.jin.ems.exception.ResourceNotFoundException;
import cn.jin.ems.mapper.EmployeeMapper;
import cn.jin.ems.repository.DepartmentRepository;
import cn.jin.ems.repository.EmployeeRepository;
import cn.jin.ems.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;

	private final DepartmentRepository departmentRepository;

	@Override
	public EmployeeDto createEmployee(EmployeeDto employeeDto) {
		Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
		Department department = departmentRepository.findById(employeeDto.getDepartmentId())
			.orElseThrow(() -> new ResourceNotFoundException(
					"Department do not exist with id: " + employeeDto.getDepartmentId()));
		employee.setDepartment(department);
		Employee savedEmployee = employeeRepository.save(employee);
		return EmployeeMapper.mapToEmployeeDto(savedEmployee);
	}

	@Override
	public EmployeeDto getEmployeeById(Long employeeId) {
		Employee employee = employeeRepository.findById(employeeId)
			.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + employeeId));
		return EmployeeMapper.mapToEmployeeDto(employee);
	}

	@Override
	public List<EmployeeDto> getAllEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		return employees.stream().map(EmployeeMapper::mapToEmployeeDto).toList();
	}

	@Override
	public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
		Employee employee = employeeRepository.findById(employeeId)
			.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + employeeId));
		employee.setFirstName(updatedEmployee.getFirstName());
		employee.setLastName(updatedEmployee.getLastName());
		employee.setEmail(updatedEmployee.getEmail());

		Department department = departmentRepository.findById(updatedEmployee.getDepartmentId())
			.orElseThrow(() -> new ResourceNotFoundException(
					"Department not exist with id: " + updatedEmployee.getDepartmentId()));
		employee.setDepartment(department);

		Employee savedEmployee = employeeRepository.save(employee);
		return EmployeeMapper.mapToEmployeeDto(savedEmployee);
	}

	@Override
	public void deleteEmployee(Long employeeId) {
		employeeRepository.findById(employeeId)
			.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + employeeId));
		employeeRepository.deleteById(employeeId);
	}

}
