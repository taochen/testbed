package com.testbed;

import java.io.*;

import javax.servlet.http.*;
import javax.servlet.*;

public class TestService extends HttpServlet {
	
	
	private ServiceInstance si = new ServiceInstance();
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		if ("1".equals(req.getParameter("arg"))) {
			si.lightService();
		} else {
			si.intensiveService();
		}
		PrintWriter out = res.getWriter();

		out.println("Xen!!!");
		out.close();
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException {
		this.doGet(req, res);
	
	}
}