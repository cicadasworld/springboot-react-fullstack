package cn.jin.ems.mapper;

import cn.jin.ems.dto.EmployeeDto;
import cn.jin.ems.entity.Department;
import cn.jin.ems.entity.Employee;

import java.util.Optional;

public final class EmployeeMapper {

	public static EmployeeDto mapToEmployeeDto(Employee employee) {
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setId(employee.getId());
		employeeDto.setFirstName(employee.getFirstName());
		employeeDto.setLastName(employee.getLastName());
		employeeDto.setEmail(employee.getEmail());
		Department department = employee.getDepartment();
		Optional.ofNullable(department).ifPresent(d -> employeeDto.setDepartmentId(d.getId()));
		return employeeDto;
	}

	public static Employee mapToEmployee(EmployeeDto employeeDto) {
		Employee employee = new Employee();
		employee.setId(employeeDto.getId());
		employee.setFirstName(employeeDto.getFirstName());
		employee.setLastName(employeeDto.getLastName());
		employee.setEmail(employeeDto.getEmail());
		return employee;
	}

}
