package samplepackage;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class home extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				response.addHeader("Access-Control-Allow-Origin", "*");
				response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
				response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
				response.addHeader("Access-Control-Max-Age", "1728000");
//
				response.setContentType("text/html;charset=UTF-8");
//				response.setContentType("application/json");

				PrintWriter out = response.getWriter();
				out.println("HOME page servlet");
//				HttpSession session=request.getSession(false);
//
//				out.println("Current sessionID : "+ session.getId());
//				out.println("Current sessionTime : "+ session.getCreationTime());
//				out.println("Current sessiongetLastAccessedTime : "+ session.getLastAccessedTime());
//
//				out.println(String.valueOf(session.getAttribute("SessionID")));
//				out.println(String.valueOf(session.getAttribute("SessionTime")));
//				out.println(String.valueOf(session.getAttribute("LastAccessedTimeSession")));
//		out.println(String.valueOf(session.getAttribute("uname")));
//		out.println(String.valueOf(session.getAttribute("pswd")));


//		out.println("START COOKIE");
//		Cookie cookie = null;
//		Cookie[] cookies = request.getCookies();
//		if( cookies != null ) {
//			out.println("<h2> Found Cookies Name and Value</h2>");
//			for (Cookie value : cookies) {
//				cookie = value;
//				out.print("Cookie Name : " + cookie.getName() + ",  ");
//				out.print("Cookie Value: " + cookie.getValue() + " <br/>");
//			}
//		}
//		else{
//			out.println("<h2>No cookies founds</h2>");
//		}
//		out.println("END COOKIE");
	}
}
