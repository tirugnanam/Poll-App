package samplepackage;

import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PollSubmit extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");

		JSONObject reqObj, respObj = new JSONObject();
		PrintWriter out = response.getWriter();
		try {
			reqObj = new JSONObject(request.getParameter("jsonData"));
			String username = reqObj.getString("username");

			JSONArray jsonArray = (JSONArray) reqObj.get("results");
			MickeyDB mdb= new MickeyDB();
			for (int i = 0; i <jsonArray.length(); i++)
			{
				JSONObject obj= (JSONObject) jsonArray.get(i);
				String pollID= (String) obj.get("poll_id");
				String poll_result= (String) obj.get("poll_result");
				long millis = System.currentTimeMillis();	//Generates millisecond

				if(mdb.addPollResponse(username, pollID, poll_result, millis))
				{
					respObj.put("resulttext"+i+" : ","update success");
				}
				else
				{
					respObj.put("resulttext"+i+" : ","update failed");
				}
			}
			respObj.put("boolean", "true");
			respObj.put("ResultText", "Poll submit success");
		} catch (Exception e) {
			respObj.put("boolean", "false");
			respObj.put("ResultText", "Exception raised -> " + e);
		}
		out.println(respObj);
	}
}