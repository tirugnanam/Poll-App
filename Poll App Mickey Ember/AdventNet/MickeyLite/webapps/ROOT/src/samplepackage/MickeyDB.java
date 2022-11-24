package samplepackage;

import com.adventnet.ds.query.*;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.*;
import org.json.JSONObject;

import java.security.Security;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("rawtypes")				//suppress warning for Iterator
public class MickeyDB {
	public boolean addUser(JSONObject jobj) throws Exception {
		try {
			Persistence persistence= (Persistence)BeanUtil.lookup("Persistence");
			Row user_row = new Row ("Users");
			user_row.set("USER_ID", jobj.get("user_id"));
			user_row.set("NAME", jobj.get("name"));
			user_row.set("DEPARTMENT", jobj.get("dept"));
			user_row.set("USER_MAIL_ID", jobj.get("user_mail_id"));

			DataObject user_dobj=new WritableDataObject();
			user_dobj.addRow(user_row);
			persistence.add(user_dobj);

			Row user_cred_row = new Row ("UsersCredentials");
			user_cred_row.set("USER_ID", jobj.get("user_id"));
			user_cred_row.set("USERNAME", jobj.get("username"));
			user_cred_row.set("PASSWORD", jobj.get("pswd"));

			DataObject user_cred_dobj=new WritableDataObject();
			user_cred_dobj.addRow(user_cred_row);
			persistence.add(user_cred_dobj);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public boolean validateUser(String username, String password) throws Exception {
		Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");

		SelectQuery sq = new SelectQueryImpl(Table.getTable("UsersCredentials"));
		sq.addSelectColumn(Column.getColumn("UsersCredentials", "USER_ID"));
		sq.addSelectColumn(Column.getColumn("UsersCredentials", "USERNAME"));
		sq.addSelectColumn(Column.getColumn("UsersCredentials", "PASSWORD"));

		Criteria username_criteria = new Criteria(new Column("UsersCredentials", "USERNAME"), username, QueryConstants.EQUAL);
		Criteria password_criteria = new Criteria(new Column("UsersCredentials", "PASSWORD"), password, QueryConstants.EQUAL);
		sq.setCriteria(username_criteria.and(password_criteria));

		DataObject dobj = persistence.get(sq);
		Row row = dobj.getRow("UsersCredentials");
		//if row == null then return false else return true
		return row != null;
	}

	public String usernameToUserID(String username) throws Exception {
		Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");

		SelectQuery user_cred_sq = new SelectQueryImpl(Table.getTable("UsersCredentials"));
		user_cred_sq.addSelectColumn(Column.getColumn("UsersCredentials", "USER_ID"));
		user_cred_sq.addSelectColumn(Column.getColumn("UsersCredentials", "USERNAME"));

		Criteria username_criteria = new Criteria(new Column("UsersCredentials", "USERNAME"), username, QueryConstants.EQUAL);
		user_cred_sq.setCriteria(username_criteria);

		DataObject user_cred_dobj = persistence.get(user_cred_sq);
		Row user_cred_row = user_cred_dobj.getRow("UsersCredentials");

		return user_cred_row.getString("USER_ID");		//getting USER_ID from UsersCredentials
	}

	public String userIDToUsername(String user_id) throws Exception {
		Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");

		SelectQuery user_cred_sq = new SelectQueryImpl(Table.getTable("UsersCredentials"));
		user_cred_sq.addSelectColumn(Column.getColumn("UsersCredentials", "USER_ID"));
		user_cred_sq.addSelectColumn(Column.getColumn("UsersCredentials", "USERNAME"));

		Criteria username_criteria = new Criteria(new Column("UsersCredentials", "USER_ID"), user_id, QueryConstants.EQUAL);
		user_cred_sq.setCriteria(username_criteria);

		DataObject user_cred_dobj = persistence.get(user_cred_sq);
		Row user_cred_row = user_cred_dobj.getRow("UsersCredentials");

		return user_cred_row.getString("USERNAME");		//getting USER_ID from UsersCredentials
	}

	public JSONObject pollIDToPollDetails(String pollID) throws Exception {
		JSONObject jsonObject = new JSONObject();
		try {
			Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");

			SelectQuery poll_data_sq = new SelectQueryImpl(Table.getTable("PollData"));
			poll_data_sq.addSelectColumn(Column.getColumn("PollData", "POLL_ID"));
			poll_data_sq.addSelectColumn(Column.getColumn("PollData", "USER_ID"));
			poll_data_sq.addSelectColumn(Column.getColumn("PollData", "POLL_CONTENT"));
			poll_data_sq.addSelectColumn(Column.getColumn("PollData", "POLL_OPTIONS"));
			poll_data_sq.addSelectColumn(Column.getColumn("PollData", "DATETIME"));

			Criteria pollID_criteria = new Criteria(new Column("PollData", "POLL_ID"), pollID, QueryConstants.EQUAL);
			poll_data_sq.setCriteria(pollID_criteria);

			DataObject poll_data_dobj = persistence.get(poll_data_sq);
			Row poll_data_row = poll_data_dobj.getRow("PollData");
			jsonObject = poll_data_row.getAsJSON();                    //getting Poll details for pollID from PollData

			//Below code is to get poll assigned user ID(s)
			SelectQuery pollassignuser_sq = new SelectQueryImpl(Table.getTable("PollAssignedUsers"));
			pollassignuser_sq.addSelectColumn(Column.getColumn("PollAssignedUsers", "POLL_ASSIGN_USER_ID"));
			pollassignuser_sq.addSelectColumn(Column.getColumn("PollAssignedUsers", "POLL_ID"));
			pollassignuser_sq.addSelectColumn(Column.getColumn("PollAssignedUsers", "ASSIGNED_USER_ID"));

			Criteria userID_criteria = new Criteria(new Column("PollAssignedUsers", "POLL_ID"), pollID, QueryConstants.EQUAL);
			pollassignuser_sq.setCriteria(userID_criteria);

			DataObject pollassignuser_dobj = persistence.get(pollassignuser_sq);
			Iterator pollassignuser_it = pollassignuser_dobj.getRows("PollAssignedUsers");

			StringBuilder poll_assign_usernames = new StringBuilder();
			while (pollassignuser_it.hasNext()) {
				Row pollassignuser_row = (Row) pollassignuser_it.next();
				poll_assign_usernames.append(userIDToUsername(pollassignuser_row.getString("ASSIGNED_USER_ID"))).append(",");
			}
			//Below 1 line - removing last character ; from strings
			poll_assign_usernames = new StringBuilder(poll_assign_usernames.substring(0, poll_assign_usernames.length() - 1));
			jsonObject.put("poll_assign_usernames", poll_assign_usernames);
		}
		catch (Exception e)
		{
			jsonObject.put("Exception raised in pollIDToPollDetails(String pollID)-> ", e);
		}
		return jsonObject;
	}

	public JSONObject userDetails(String username) throws Exception {
		JSONObject jObj, jObj2;
		Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");

		SelectQuery user_cred_sq = new SelectQueryImpl(Table.getTable("UsersCredentials"));
		user_cred_sq.addSelectColumn(Column.getColumn("UsersCredentials", "USER_ID"));
		user_cred_sq.addSelectColumn(Column.getColumn("UsersCredentials", "USERNAME"));
//		user_cred_sq.addSelectColumn(Column.getColumn("UsersCredentials", "PASSWORD"));

		Criteria username_criteria = new Criteria(new Column("UsersCredentials", "USERNAME"), username, QueryConstants.EQUAL);
		user_cred_sq.setCriteria(username_criteria);

		DataObject user_cred_dobj = persistence.get(user_cred_sq);
		Row user_cred_row = user_cred_dobj.getRow("UsersCredentials");
		jObj =user_cred_row.getAsJSON();
		String user_id= (String) user_cred_row.get("USER_ID");		//getting USER_ID from UsersCredentials

		SelectQuery user_sq = new SelectQueryImpl(Table.getTable("Users"));
		user_sq.addSelectColumn(Column.getColumn("Users", "USER_ID"));
		user_sq.addSelectColumn(Column.getColumn("Users", "NAME"));
		user_sq.addSelectColumn(Column.getColumn("Users", "DEPARTMENT"));
		user_sq.addSelectColumn(Column.getColumn("Users", "USER_MAIL_ID"));

		Criteria user_id_criteria = new Criteria(new Column("Users", "USER_ID"), user_id, QueryConstants.EQUAL);
		user_sq.setCriteria(user_id_criteria);

		DataObject user_dobj = persistence.get(user_sq);
		Row user_row = user_dobj.getRow("Users");
		jObj2 =user_row.getAsJSON();
		for (String key : jObj2.keySet())			//Note: USER_ID from Users gets overwritten in jObj
		{
			Object key_value = jObj2.get(key);
//			System.out.println("key: "+ keyStr + " value: " + key_value);
			jObj.put(key,key_value);
		}
		return jObj;
	}

public String addPoll(JSONObject reqObj) throws Exception {
		try {
			Persistence persistence= (Persistence)BeanUtil.lookup("Persistence");
//			String userID = usernameToUserID(reqObj.getString("username"));
			JSONObject jObj1 = userDetails(reqObj.getString("username"));
			String userID = jObj1.getString("user_id");
//			String email_from_addr = jObj1.getString("user_mail_id");
			String email_from_addr = "tirugnanam.m@zohocorp.com";

			String[] pollassigned_users = reqObj.getString("pollassigned_users").split(",");
			String pollID = reqObj.getString("pollid");

			Row polldata_row = new Row ("PollData");
			polldata_row.set("POLL_ID", pollID);
			polldata_row.set("USER_ID", userID);					//Poll owner USER_ID
			polldata_row.set("POLL_CONTENT", reqObj.get("pollcontent"));
			polldata_row.set("POLL_OPTIONS", reqObj.get("polloptions"));
			polldata_row.set("DATETIME", reqObj.get("datetime"));

			DataObject polldata_dobj=new WritableDataObject();
			polldata_dobj.addRow(polldata_row);
			persistence.add(polldata_dobj);

			List<String> assigned_users_email_to_addr_list=new ArrayList<String>();
			List<String> assigned_users_name_list =new ArrayList<String>();
			for(String assigned_user: pollassigned_users)
			{
//				String assigned_userID = usernameToUserID(assigned_user);
				JSONObject jObj2 = userDetails(assigned_user);
				String assigned_userID = jObj2.getString("user_id");
				assigned_users_email_to_addr_list.add(jObj2.getString("user_mail_id"));
				assigned_users_name_list.add(jObj2.getString("name"));

				Row pollassignuser_row = new Row ("PollAssignedUsers");
				pollassignuser_row.set("POLL_ID", pollID);
				pollassignuser_row.set("ASSIGNED_USER_ID", assigned_userID);             //Poll assigns to which USER_ID

				DataObject pollassignuser_dobj=new WritableDataObject();
				pollassignuser_dobj.addRow(pollassignuser_row);
				persistence.add(pollassignuser_dobj);
			}
			String[] assigned_users_email_to_addr =assigned_users_email_to_addr_list.toArray(new String[0]);
			String[] assigned_users_name =assigned_users_name_list.toArray(new String[0]);

			//convert milliseconds into date in IST
			long milliSec = (long) reqObj.get("datetime");
			DateFormat dateformat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss Z");
			dateformat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			Date date = new Date(milliSec);

			String emailSubjectTxt = "New Poll has been assigned to you";

			String emailMsgTxt = "<br><br><table border=2>";
//			emailMsgTxt += "<tr><td>POLL ID: </td><td>"+ reqObj.getString("pollid")+"</td></tr>";
//			emailMsgTxt += "<tr><td>POLL CREATOR USER ID: </td><td>"+ jObj1.getString("user_id")+"</td></tr>";					//Poll owner USER_ID
			emailMsgTxt += "<tr><td>POLL CREATOR NAME: </td><td>"+ jObj1.getString("name")+"</td></tr>";					//Poll owner NAME
//			emailMsgTxt += "<tr><td>POLL CREATOR USERNAME: </td><td>"+ jObj1.getString("username")+"</td></tr>";					//Poll owner USERNAME
			emailMsgTxt += "<tr><td>POLL CONTENT: </td><td>"+ reqObj.get("pollcontent")+"</td></tr>";
			emailMsgTxt += "<tr><td>POLL OPTIONS: </td><td>"+ reqObj.get("polloptions")+"</td></tr>";
			emailMsgTxt += "<tr><td>POLL CREATION DATETIME: </td><td>"+ dateformat.format(date)+" IST" +"</td></tr>";
			emailMsgTxt += "</table>";
			emailMsgTxt += "<br>Notification mail. Please Do not reply";

			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			String result = new SendMail().sendSSLMessage(assigned_users_email_to_addr, assigned_users_name, emailSubjectTxt, emailMsgTxt, email_from_addr);

			return "addPoll true, "+result;
		}
		catch (Exception e)
		{
			return "addPoll false, "+ e;
		}
	}

	public JSONObject pollRequestDetails(String username) throws Exception {
		JSONObject jObj = new JSONObject();
		try {
			Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
			String userID = usernameToUserID(username);

			SelectQuery pollassignuser_sq = new SelectQueryImpl(Table.getTable("PollAssignedUsers"));
			pollassignuser_sq.addSelectColumn(Column.getColumn("PollAssignedUsers", "POLL_ASSIGN_USER_ID"));
			pollassignuser_sq.addSelectColumn(Column.getColumn("PollAssignedUsers", "POLL_ID"));
			pollassignuser_sq.addSelectColumn(Column.getColumn("PollAssignedUsers", "ASSIGNED_USER_ID"));

			Criteria userID_criteria = new Criteria(new Column("PollAssignedUsers", "ASSIGNED_USER_ID"), userID, QueryConstants.EQUAL);
			pollassignuser_sq.setCriteria(userID_criteria);

			DataObject pollassignuser_dobj = persistence.get(pollassignuser_sq);
			Iterator pollassignuser_it = pollassignuser_dobj.getRows("PollAssignedUsers");

			StringBuilder poll_id = new StringBuilder(), poll_owner = new StringBuilder(), poll_content = new StringBuilder(), poll_options = new StringBuilder(), poll_datetime = new StringBuilder();
			while (pollassignuser_it.hasNext())
			{
				Row pollassignuser_row = (Row) pollassignuser_it.next();
				String pollID = pollassignuser_row.getString("POLL_ID");

				SelectQuery pollResponseData_sq = new SelectQueryImpl(Table.getTable("PollResponseData"));
				pollResponseData_sq.addSelectColumn(Column.getColumn("PollResponseData", "POLL_RESP_DATA_ID"));
				pollResponseData_sq.addSelectColumn(Column.getColumn("PollResponseData", "POLL_ID"));
				pollResponseData_sq.addSelectColumn(Column.getColumn("PollResponseData", "USER_ID"));

				Criteria pollResponseData_pollID_criteria = new Criteria(new Column("PollResponseData", "POLL_ID"), pollID, QueryConstants.EQUAL);

				Criteria pollResponseData_userID_criteria = new Criteria(new Column("PollResponseData", "USER_ID"), userID, QueryConstants.EQUAL);
				pollResponseData_sq.setCriteria(pollResponseData_pollID_criteria.and(pollResponseData_userID_criteria));

				DataObject pollResponseData_dobj = persistence.get(pollResponseData_sq);
				Row pollResponseData_row= pollResponseData_dobj.getRow("PollResponseData");

				//check is the poll is already responded by the assigned user or not in PollResponseData
				if(pollResponseData_row == null)			//Not responded by the user
				{
					SelectQuery sq = new SelectQueryImpl(Table.getTable("PollData"));
					sq.addSelectColumn(Column.getColumn("PollData", "POLL_ID"));
					sq.addSelectColumn(Column.getColumn("PollData", "USER_ID"));
					sq.addSelectColumn(Column.getColumn("PollData", "POLL_CONTENT"));
					sq.addSelectColumn(Column.getColumn("PollData", "POLL_OPTIONS"));
					sq.addSelectColumn(Column.getColumn("PollData", "DATETIME"));

					Criteria pollID_criteria = new Criteria(new Column("PollData", "POLL_ID"), pollID, QueryConstants.EQUAL);
					sq.setCriteria(pollID_criteria);

					DataObject dobj = persistence.get(sq);
					Row row = dobj.getRow("PollData");

					poll_id.append(row.getString("POLL_ID")).append(";");
					poll_content.append(row.getString("POLL_CONTENT")).append(";");
					poll_options.append(row.getString("POLL_OPTIONS")).append(";");
					poll_owner.append(userIDToUsername(row.getString("USER_ID"))).append(";");
					poll_datetime.append(row.get("DATETIME")).append(";");
				}
			}

			if((poll_id.length() == 0) && (poll_content.length() == 0)) 	//if no unfilled poll request data available
			{
				jObj.put("data_status", "NO DATA");
			}
			else {
				//Below 5 lines - removing last character ; from strings
				poll_id = new StringBuilder(poll_id.substring(0, poll_id.length() - 1));
				poll_content = new StringBuilder(poll_content.substring(0, poll_content.length() - 1));
				poll_options = new StringBuilder(poll_options.substring(0, poll_options.length() - 1));
				poll_owner = new StringBuilder(poll_owner.substring(0, poll_owner.length() - 1));
				poll_datetime = new StringBuilder(poll_datetime.substring(0, poll_datetime.length() - 1));

				jObj.put("poll_id", poll_id.toString());
				jObj.put("poll_content", poll_content.toString());
				jObj.put("poll_options", poll_options.toString());
				jObj.put("poll_owner", poll_owner.toString());
				jObj.put("poll_datetime", poll_datetime.toString());

				jObj.put("data_status", "DATA available");
			}
			return jObj;
		}
		catch (Exception e)
		{
			jObj.put("Exception raised in DB-> ", e);
			return jObj;
		}
	}

	public boolean addPollResponse(String username, String pollID, String poll_result, long millis) throws Exception {
		try {
			Persistence persistence= (Persistence)BeanUtil.lookup("Persistence");
			String userID = usernameToUserID(username);

			Row row = new Row ("PollResponseData");
			row.set("POLL_ID", pollID);
			row.set("USER_ID", userID);
			row.set("POLL_RESULT", poll_result);
			row.set("DATETIME", millis);

			DataObject dobj=new WritableDataObject();
			dobj.addRow(row);
			persistence.add(dobj);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public JSONObject pollResponseDetails(String username) throws Exception {
		JSONObject jObj = new JSONObject();
		try {
			Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
			String userID = usernameToUserID(username);

			SelectQuery sq = new SelectQueryImpl(Table.getTable("PollResponseData"));
			sq.addSelectColumn(Column.getColumn("PollResponseData", "POLL_RESP_DATA_ID"));
			sq.addSelectColumn(Column.getColumn("PollResponseData", "POLL_ID"));
			sq.addSelectColumn(Column.getColumn("PollResponseData", "USER_ID"));
			sq.addSelectColumn(Column.getColumn("PollResponseData", "POLL_RESULT"));
			sq.addSelectColumn(Column.getColumn("PollResponseData", "DATETIME"));

			Criteria username_criteria = new Criteria(new Column("PollResponseData", "USER_ID"), userID, QueryConstants.EQUAL);
			sq.setCriteria(username_criteria);

			DataObject dobj = persistence.get(sq);
			Iterator it = dobj.getRows("PollResponseData");

			if(!it.hasNext()) 	//if no response has been recorded (i.e) it.hasNext() == false
			{
				jObj.put("data_status", "NO DATA");
				return jObj;
			}
			StringBuilder poll_resp_data_id= new StringBuilder(), poll_id = new StringBuilder(), poll_response = new StringBuilder(), datetime = new StringBuilder();
			StringBuilder poll_content= new StringBuilder(), poll_owner= new StringBuilder(), poll_options= new StringBuilder(), poll_creation_datetime= new StringBuilder();
			while (it.hasNext()) {
				Row r = (Row) it.next();
				String pollID = r.getString("POLL_ID");

				poll_resp_data_id.append(r.get("POLL_RESP_DATA_ID")).append(";");
				poll_id.append(pollID).append(";");
				poll_response.append(r.get("POLL_RESULT")).append(";");
				datetime.append(r.get("DATETIME")).append(";");

				JSONObject jsonObject = pollIDToPollDetails(pollID);
				poll_content.append(jsonObject.getString("poll_content")).append(";");
				poll_owner.append(userIDToUsername(jsonObject.getString("user_id"))).append(";");
				poll_options.append(jsonObject.getString("poll_options")).append(";");
				poll_creation_datetime.append(jsonObject.get("datetime")).append(";");
			}

			poll_resp_data_id = new StringBuilder(poll_resp_data_id.substring(0, poll_resp_data_id.length() - 1));
			poll_id = new StringBuilder(poll_id.substring(0, poll_id.length() - 1));
			poll_response = new StringBuilder(poll_response.substring(0, poll_response.length() - 1));
			datetime = new StringBuilder(datetime.substring(0, datetime.length() - 1));

			poll_content = new StringBuilder(poll_content.substring(0, poll_content.length() - 1));
			poll_owner = new StringBuilder(poll_owner.substring(0, poll_owner.length() - 1));
			poll_options = new StringBuilder(poll_options.substring(0, poll_options.length() - 1));
			poll_creation_datetime = new StringBuilder(poll_creation_datetime.substring(0, poll_creation_datetime.length() - 1));

			jObj.put("poll_resp_data_id", poll_resp_data_id.toString());
			jObj.put("poll_id", poll_id.toString());
			jObj.put("poll_response", poll_response.toString());
			jObj.put("datetime", datetime.toString());

			jObj.put("poll_content", poll_content.toString());
			jObj.put("poll_owner", poll_owner.toString());
			jObj.put("poll_options", poll_options.toString());
			jObj.put("poll_creation_datetime", poll_creation_datetime.toString());

			jObj.put("data_status", "DATA available");
			return jObj;
		}
		catch (Exception e)
		{
			jObj.put("Exception raised in DB-> ", e);
			return jObj;
		}
	}

	public JSONObject getAllUsernames() throws Exception {
		JSONObject jsonObject = new JSONObject();
		Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");

		SelectQuery sq = new SelectQueryImpl(Table.getTable("UsersCredentials"));
		sq.addSelectColumn(Column.getColumn("UsersCredentials", "USER_ID"));
		sq.addSelectColumn(Column.getColumn("UsersCredentials", "USERNAME"));

		DataObject dobj = persistence.get(sq);
		Iterator it = dobj.getRows("UsersCredentials");

		List<String> list=new ArrayList<String>();

		while (it.hasNext()) {
			Row r = (Row) it.next();
			list.add(r.getString("USERNAME"));
		}
		String[] names =list.toArray(new String[0]);
		jsonObject.put("all_usernames",names);
		return jsonObject;
	}

	public JSONObject pollResultDetails(String username) {
		JSONObject jsonObject = new JSONObject();
		try{
			Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
			String userID = usernameToUserID(username);

			SelectQuery polldata_sq = new SelectQueryImpl(Table.getTable("PollData"));
			polldata_sq.addSelectColumn(Column.getColumn("PollData", "POLL_ID"));
			polldata_sq.addSelectColumn(Column.getColumn("PollData", "USER_ID"));

			Criteria userid_criteria = new Criteria(new Column("PollData", "USER_ID"), userID, QueryConstants.EQUAL);
			polldata_sq.setCriteria(userid_criteria);

			DataObject polldata_dobj = persistence.get(polldata_sq);
			Iterator polldata_it = polldata_dobj.getRows("PollData");

			//if no poll has been created by current userID
			if(!polldata_it.hasNext()) 	//if no response has been recorded (i.e) it.hasNext() == false
			{
				jsonObject.put("data_status", "NO POLL DATA");
				return jsonObject;
			}
			int count=0;
			while (polldata_it.hasNext()) {
				Row polldata_row = (Row) polldata_it.next();
				String pollID = polldata_row.getString("POLL_ID");
				JSONObject jObj = pollIDToPollDetails(pollID);

				SelectQuery sq = new SelectQueryImpl(Table.getTable("PollResponseData"));
				sq.addSelectColumn(Column.getColumn("PollResponseData", "POLL_RESP_DATA_ID"));
				sq.addSelectColumn(Column.getColumn("PollResponseData", "POLL_ID"));
				sq.addSelectColumn(Column.getColumn("PollResponseData", "USER_ID"));
				sq.addSelectColumn(Column.getColumn("PollResponseData", "POLL_RESULT"));
				sq.addSelectColumn(Column.getColumn("PollResponseData", "DATETIME"));

				Criteria pollID_criteria = new Criteria(new Column("PollResponseData", "POLL_ID"), pollID, QueryConstants.EQUAL);
				sq.setCriteria(pollID_criteria);

				DataObject dobj = persistence.get(sq);
				Iterator it = dobj.getRows("PollResponseData");

				//if no response has been detected for current pollID
				if(!it.hasNext()) 	//if no response has been recorded (i.e) it.hasNext() == false
				{
					jObj.put("data_status", "NO RESPONSE DATA");
					jsonObject.put(String.valueOf(++count), jObj);
					continue;
				}
				StringBuilder poll_resp_data_id= new StringBuilder(), poll_result_username= new StringBuilder(),poll_result = new StringBuilder(), poll_result_datetime = new StringBuilder();
				while (it.hasNext()) {
					Row r = (Row) it.next();
					poll_resp_data_id.append(r.get("POLL_RESP_DATA_ID")).append(";");
					poll_result.append(r.get("POLL_RESULT")).append(";");
					poll_result_username.append(userIDToUsername(r.getString("USER_ID"))).append(";");
					poll_result_datetime.append(r.get("DATETIME")).append(";");
				}
				poll_resp_data_id = new StringBuilder(poll_resp_data_id.substring(0, poll_resp_data_id.length() - 1));
				poll_result = new StringBuilder(poll_result.substring(0, poll_result.length() - 1));
				poll_result_username = new StringBuilder(poll_result_username.substring(0, poll_result_username.length() - 1));
				poll_result_datetime = new StringBuilder(poll_result_datetime.substring(0, poll_result_datetime.length() - 1));

				jObj.put("poll_resp_data_id", poll_resp_data_id.toString());
				jObj.put("poll_result", poll_result.toString());
				jObj.put("poll_result_username", poll_result_username.toString());
				jObj.put("poll_resp_datetime", poll_result_datetime.toString());

				jObj.put("data_status", "RESPONSE DATA FETCHED");
				jsonObject.put(String.valueOf(++count), jObj);
			}
			jsonObject.put("data_status", "DATA available");
			return jsonObject;
		}
		catch (Exception e)
		{
			jsonObject.put("Exception raised in DB-> ", e);
			return jsonObject;
		}
	}

	public boolean deletePoll(String poll_id) {
		try{
			Persistence persistence= (Persistence)BeanUtil.lookup("Persistence");
			Criteria criteria = new Criteria(new Column("PollData", "POLL_ID"), poll_id, QueryConstants.EQUAL);
			persistence.delete(criteria);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
}
