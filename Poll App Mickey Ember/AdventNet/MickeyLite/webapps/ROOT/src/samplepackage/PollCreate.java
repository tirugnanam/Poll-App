package samplepackage;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

public class PollCreate extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");

		JSONObject reqObj, respObj = new JSONObject();
		PrintWriter out = response.getWriter();
		try {
			reqObj = new JSONObject(request.getParameter("jsonData"));

			long millis = System.currentTimeMillis();	//Generates millisecond
			UUID uuid = UUID.randomUUID(); 				//Generates random UUID
			reqObj.put("datetime",millis);
			reqObj.put("pollid",uuid.toString());

			MickeyDB mdb= new MickeyDB();
			String res = mdb.addPoll(reqObj);
			if(res.contains("true"))
			{
				respObj.put("res", res);
				respObj.put("boolean", "true");
				respObj.put("ResultText", "Poll added to the DB");
			}
			else
			{
				respObj.put("res", res);
				respObj.put("boolean", "false");
				respObj.put("ResultText", "Poll NOT added to the DB");
			}
		} catch (Exception e) {
			respObj.put("boolean", "false");
			respObj.put("ResultText", "Exception raised -> " + e);
		}
		out.println(respObj);
	}
}