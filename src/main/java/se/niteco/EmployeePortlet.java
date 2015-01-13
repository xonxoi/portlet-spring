package se.niteco;

import java.io.IOException;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import se.niteco.model.Employee;
import se.niteco.service.EmployeeService;
import se.niteco.service.EmployeeServiceImpl;

public class EmployeePortlet extends GenericPortlet {
	
	private EmployeeServiceImpl service = new EmployeeServiceImpl();

	public void init() {
		System.out.println("Run over init function");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.portlet.GenericPortlet#doView(javax.portlet.RenderRequest,
	 * javax.portlet.RenderResponse)
	 */
	public void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {

		List<Employee> empList = service.getListEmployee();

		String action = (String) request.getParameter("actionStatus");

		if ("insertEmployee".equals(action)) {
			request.setAttribute("homeURL", response.createRenderURL());

			// Forward to display result page
			getPortletContext().getRequestDispatcher(
					"/WEB-INF/vm/announcement.vm").include(request, response);
		} else {
			// Forward to list employee page
			request.setAttribute("employees", empList);
			request.setAttribute("label", "This is Niteco List");

			PortletURL insertURL = response.createActionURL();
			insertURL.setParameter(ActionRequest.ACTION_NAME, "insertEmployee");
			request.setAttribute("insertURL", insertURL);

			getPortletContext().getRequestDispatcher(
					"/WEB-INF/jsp/employeeList.jsp").include(request, response);
		}
	}

	/**
	 * @param request
	 * @param response
	 * @throws PortletException
	 * @throws IOException
	 */
	@ProcessAction(name = "insertEmployee")
	public void insertEmployee(ActionRequest request, ActionResponse response)
			throws PortletException, IOException {

		String actionName = request.getParameter(ActionRequest.ACTION_NAME);
		System.out.println("Executing.......... " + actionName);
		
		Employee newEmp = new Employee(request.getParameter("empCode"),
				request.getParameter("empName"),
				request.getParameter("department"),
				request.getParameter("position"),
				Integer.valueOf(request.getParameter("salary"))
				);
		
//		EmployeeServiceImpl service = new EmployeeServiceImpl();
		service.insertEmployee(newEmp);

		response.setRenderParameter("empCode", request.getParameter("empCode"));
		response.setRenderParameter("empName", request.getParameter("empName"));
		response.setRenderParameter("position",request.getParameter("position"));
		response.setRenderParameter("department",request.getParameter("department"));
		response.setRenderParameter("salary", request.getParameter("salary"));
		response.setRenderParameter("actionStatus", "insertEmployee");
	}
}
