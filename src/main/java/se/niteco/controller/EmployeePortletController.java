package se.niteco.controller;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import se.niteco.model.Employee;
import se.niteco.service.EmployeeService;
import senselogic.sitevision.api.Utils;

@Controller
@RequestMapping(value="VIEW")
public class EmployeePortletController{
	
	@Autowired
	@Qualifier("employeeService")
	private EmployeeService service;
	
	@RenderMapping
	public String showEmployee(Model model, RenderRequest request, RenderResponse response, PortletPreferences pref){
		
		Utils util = (Utils) request.getAttribute("sitevision.utils");
		
		//Set add url
		PortletURL showAddUrl = response.createRenderURL();
		showAddUrl.setParameter("action", "showAdd");
		model.addAttribute("showAddUrl", showAddUrl);

		//Set edit url
		PortletURL editUrl = response.createRenderURL();
		editUrl.setParameter("action", "showEdit");
		model.addAttribute("editUrl", editUrl);

		//Set remove url
		PortletURL removeUrl = response.createActionURL();
		removeUrl.setParameter("action", "deleteEmployee");
		model.addAttribute("removeUrl", removeUrl);
		
		//Get list of employee
		List<Employee> lst = service.getListEmployee();
		model.addAttribute("name","Niteco");
		model.addAttribute("lstEmployee", lst);
		
		String mode = pref.getValue("mode", "View");
		return "employeeList" + mode;
	}
	
	@RenderMapping(params = "action=showAdd")
	public String showAdd(Model model, RenderRequest request, RenderResponse response){
	
		//Set url to model
		PortletURL registUrl = response.createActionURL();
		registUrl.setParameter("action", "insertEmployee");
		
		model.addAttribute("registUrl", registUrl);
		model.addAttribute("homeUrl", response.createRenderURL());
		
		Employee emp = new Employee();
		model.addAttribute("emp", emp);
		model.addAttribute("request", request);
		model.addAttribute("errMessage", request.getParameter("errMessage"));
		
		return "employeeForm";
	}
	
	@ActionMapping(params = "action=insertEmployee")
	public void doAdd(ActionRequest request, ActionResponse response) throws Exception{
		
		Employee newEmp = new Employee(request.getParameter("id"), 
				request.getParameter("name"), 
				request.getParameter("dept"), 
				request.getParameter("position"), 
				Integer.valueOf(request.getParameter("salary")));
		
		boolean ret = service.insertEmployee(newEmp);
		if ( ret == false ){
			response.setRenderParameter("action", "showAdd");
			response.setRenderParameter("errMessage", "Employee Id is duplicated.");
		}
		else{
			response.setRenderParameter("errMessage", "");
		}
		
	}
	
	@RenderMapping(params = "action=showEdit")
	public String showEdit(Model model, RenderRequest request, RenderResponse response){

		//Set url to model
		PortletURL updateUrl = response.createActionURL();
		updateUrl.setParameter("action", "updateEmployee");
		
		model.addAttribute("registUrl", updateUrl);
		model.addAttribute("homeUrl", response.createRenderURL());
		
		//Get selected employee
		String id = (String) request.getParameter("id");
		Employee emp = service.getEmployee(id);
		
		model.addAttribute("emp", emp);
		model.addAttribute("request", request);
		
		return "employeeForm";
	}
	
	@ActionMapping(params = "action=updateEmployee")
	public void doEdit(ActionRequest request){
		
		Employee updateEmp = new Employee(request.getParameter("id"), 
				request.getParameter("name"), 
				request.getParameter("dept"), 
				request.getParameter("position"), 
				Integer.valueOf(request.getParameter("salary")));
		
		service.updateEmployee(updateEmp);
	}
	
	@ActionMapping(params = "action=deleteEmployee")
	public void doRemove(ActionRequest request){
		
		String id = (String)request.getParameter("id");
		service.removeEmployee(id);
	}
}
