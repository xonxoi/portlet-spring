package se.niteco.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping(value="CONFIG")
public class EmployeeConfigController {
	
	@RenderMapping
	public void doConfig(RenderRequest request, RenderResponse response) 
			throws PortletException, IOException{
		
		System.out.println("Configuration Mode");
		PrintWriter jout = response.getWriter();
		jout.write("CONFIGURINGGGGG");
		
	}
}
