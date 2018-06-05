package com.spring.boot.service;

import org.springframework.stereotype.Service;

import com.spring.boot.entity.Employee;
import com.spring.boot.entity.EmployeeJson;
import com.spring.boot.util.JSONUtility;

@Service
public class IndexServiceImpl implements IndexService{

	@Override
	public EmployeeJson getIndexData() {
		EmployeeJson employeeJson = new EmployeeJson();
		Employee employee = new Employee();
		employee.setName("Hello");
		employee.setEmail("hello@gmail.com");
		employee.setPhone(789456);
		employeeJson.setEmployee(employee);
		
		employeeJson.setName("Suresh");
		employeeJson.setCity("HYD");
		employeeJson.setPhone("5678");
		employeeJson.setMobile("567889909877");
		
		return employeeJson;
	}

}
