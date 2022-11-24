package samplepackage;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PollResponse extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");

		JSONObject reqObj, respObj = new JSONObject();
		PrintWriter out = response.getWriter();
		try {
			reqObj = new JSONObject(request.getParameter("jsonData"));
			String username = reqObj.getString("username");

			MickeyDB mdb= new MickeyDB();
			respObj = mdb.pollResponseDetails(username);
			respObj.put("boolean", "true");
			respObj.put("ResultText", "Poll Result success");
		} catch (Exception e) {
			respObj.put("boolean", "false");
			respObj.put("ResultText", "Exception raised -> " + e);
		}
		out.println(respObj);
	}
}
