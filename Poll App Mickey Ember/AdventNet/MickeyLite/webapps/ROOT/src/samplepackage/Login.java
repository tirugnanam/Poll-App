package samplepackage;

import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class Login extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");

		JSONObject reqObj, respObj = new JSONObject();
		PrintWriter out = response.getWriter();
		try {
			reqObj = new JSONObject(request.getParameter("jsonData"));
			String username = reqObj.getString("username");
			String password = reqObj.getString("password");

			MickeyDB mdb= new MickeyDB();
			if(mdb.validateUser(username, password))		//its a valid username & password
			{
				respObj.put("boolean", "true");
				respObj.put("ResultText", "Username and Password matched with db");
			}
			else
			{
				respObj.put("boolean", "false");
				respObj.put("ResultText", "Username/Password invalid");
			}
		} catch (Exception e) {
			respObj.put("boolean", "false");
			respObj.put("ResultText", "Exception raised -> " + e);
		}
		out.println(respObj);
	}
}