package samplepackage;

import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

public class PollDelete extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");

		JSONObject reqObj, respObj = new JSONObject();
		PrintWriter out = response.getWriter();
		try {
			reqObj = new JSONObject(request.getParameter("jsonData"));
			String Poll_ID = reqObj.getString("poll_id");

			MickeyDB mdb= new MickeyDB();
			if(mdb.deletePoll(Poll_ID))
			{
				respObj.put("boolean", "true");
				respObj.put("ResultText", "Poll deleted from the DB");
			}
			else
			{
				respObj.put("boolean", "false");
				respObj.put("ResultText", "Poll NOT deleted from the DB");
			}
		} catch (Exception e) {
			respObj.put("boolean", "false");
			respObj.put("ResultText", "Exception raised -> " + e);
		}
		out.println(respObj);
	}
}