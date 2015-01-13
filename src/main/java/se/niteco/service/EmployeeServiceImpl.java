package se.niteco.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

import se.niteco.model.Employee;

@Service(value="employeeService")
public class EmployeeServiceImpl implements EmployeeService{
	
	private List<Employee> empData = Collections.synchronizedList(new ArrayList<Employee>());
	
	public EmployeeServiceImpl(){
		empData.add(new Employee("HN001", "KhoiBD", "RxEyeAB", "Team Leader", 2000));
		empData.add(new Employee("HN200", "Rene", "SiteVision", "Developer", 1000));
		empData.add(new Employee("HN266", "XonMX", "SiteVision", "Developer", 500));
	}
	
	
	public List<Employee> getListEmployee(){	
		return this.empData;
	}
	
	public boolean insertEmployee(Employee newEmp){
		Employee exist = getEmployee(newEmp.getEmployeeId());
		if( exist == null ){
			empData.add(newEmp);
			return true;
		}else{
			return false;
		}
		
	}
	
	public Employee getEmployee(String id){
		Employee ret = null;
		
		for(Employee emp : empData){
			if(id.equals(emp.getEmployeeId())){
				return emp;
			}
		}
		return ret;
	}
	
	public void updateEmployee(Employee info){
		synchronized(empData){
			for(Employee emp : empData){
				if(emp.getEmployeeId().equals(info.getEmployeeId())){
					emp.setEmployeeName(info.getEmployeeName());
					emp.setPosition(info.getPosition());
					emp.setDepartment(info.getDepartment());
					emp.setSalary(info.getSalary());
				}
			}
		}
	}
	
	public void removeEmployee(String id){
		synchronized(empData){
			empData.remove(getEmployee(id));
		}
	}
}
