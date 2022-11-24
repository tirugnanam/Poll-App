package samplepackage;

import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class Signup extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");

		JSONObject reqObj, respObj = new JSONObject();
		PrintWriter out = response.getWriter();
		try {
			reqObj = new JSONObject(request.getParameter("jsonData"));
//			respObj.put("got",reqObj);
			MickeyDB mdb= new MickeyDB();
			if(mdb.addUser(reqObj))
			{
				respObj.put("boolean", "true");
				respObj.put("ResultText", "Account created");
			}
			else
			{
				respObj.put("boolean", "false");
				respObj.put("ResultText", "Employee ID/ Username already exists. Account NOT created");
			}
		} catch (Exception e) {
			respObj.put("boolean", "false");
			respObj.put("ResultText", "Exception caught in code!!!");
		}
		out.println(respObj);
	}
}
