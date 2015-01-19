package se.niteco.service;

import java.util.List;

import javax.portlet.ActionRequest;

import se.niteco.model.Employee;

public interface EmployeeService {
	
	/**
	 * @return List of employee
	 */
	public List<Employee> getListEmployee();
	
	/**
	 * @param newEmp
	 */
	public boolean insertEmployee(Employee newEmp);
	
	/**
	 * @param id
	 * @return employee
	 */
	public Employee getEmployee(String id);
	
	/**
	 * @param info
	 */
	public void updateEmployee(Employee info);
	
	/**
	 * @param id
	 */
	public void removeEmployee(String id);
	
}
