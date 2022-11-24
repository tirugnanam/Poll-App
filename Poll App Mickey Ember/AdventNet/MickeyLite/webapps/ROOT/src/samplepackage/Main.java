package samplepackage;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet("/ServletCall")
public class Main extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.println("<center><h2>Welcome " + req.getParameter("uname") + "</h2><br>");
		out.println("<p>This is a Jakarta EE 9 Servlet!<br>");
		out.println("this.getServletContext().getServerInfo() = " + this.getServletContext().getServerInfo() + "<br>");
		out.println("this.getClass().getSuperclass() = " + this.getClass().getSuperclass() + "<br>");
		out.println("System.getProperty(\"java.version\") = " + System.getProperty("java.version") + "<br>");
		out.println("System.getProperty(\"java.runtime.version\") = " + System.getProperty("java.runtime.version") + "<br>");
		out.println("System.getProperty(\"java.vendor\") = " + System.getProperty("java.vendor") + "</p><br></center>");
		out.println("TESTING");
	}

	public static void main(String[] args)
	{
		System.out.println("Hello User 0123456789! hi tomcat");
	}
}