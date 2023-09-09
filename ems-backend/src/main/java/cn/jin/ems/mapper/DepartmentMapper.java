package cn.jin.ems.mapper;

import cn.jin.ems.dto.DepartmentDto;
import cn.jin.ems.entity.Department;
import org.springframework.beans.BeanUtils;

public final class DepartmentMapper {
    public static DepartmentDto mapToDepartmentDto(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        BeanUtils.copyProperties(department, departmentDto);
        return departmentDto;
    }

    public static Department mapToDepartment(DepartmentDto departmentDto) {
        Department department = new Department();
        BeanUtils.copyProperties(departmentDto, department);
        return department;
    }
}
