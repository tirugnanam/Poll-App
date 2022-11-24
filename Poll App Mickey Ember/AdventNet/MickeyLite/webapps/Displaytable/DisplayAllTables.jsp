<%@page contentType="text/html" import="java.net.*, java.io.*, java.util.*, com.adventnet.persistence.*, com.adventnet.ds.query.*, com.adventnet.ds.query.util.*, com.adventnet.db.persistence.metadata.*, com.adventnet.db.api.*, com.adventnet.persistence.personality.*, com.adventnet.persistence.xml.*, java.sql.*, javax.sql.*, com.adventnet.db.adapter.oracle.*, com.adventnet.db.persistence.metadata.*, com.adventnet.db.persistence.metadata.util.*, com.adventnet.ds.adapter.mds.*, com.adventnet.mfw.bean.*, com.adventnet.customview.*, com.adventnet.model.table.*, com.adventnet.metapersistence.*, org.json.JSONObject"%>
<html>
    <title>Display All Tables</title>
<body>
    <h1>Display Users</h1>
    <table border='2'>
    <tr>
    <%
                /*
                Persistence persistence= (Persistence)BeanUtil.lookup("Persistence");
    			Row user_row = new Row ("Users");
    			user_row.set("USER_ID", "PT-6053");
    			user_row.set("NAME", "Gopinath");
    			user_row.set("DEPARTMENT", "IT");

    			DataObject user_dobj=new WritableDataObject();
    			user_dobj.addRow(user_row);
    			persistence.add(user_dobj);

    			Row user_cred_row = new Row ("UsersCredentials");
    			user_cred_row.set("USER_ID", "PT-6053");
    			user_cred_row.set("USERNAME", "gopi");
    			user_cred_row.set("PASSWORD", "gopi123");

    			DataObject user_cred_dobj=new WritableDataObject();
    			user_cred_dobj.addRow(user_cred_row);
    			persistence.add(user_cred_dobj);
    			*/

                /*
                try
                {
                Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
                String poll_id = "", poll_owner = "", poll_content = "", poll_options = "", poll_datetime = "";
				String pollID = "016c3bcb-f441-44de-b457-b6d2bebac3c9";


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


				poll_id = poll_id + row.getString("POLL_ID") + ";";
				poll_content = poll_content + row.getString("POLL_CONTENT") + ";";
				poll_options = poll_options + row.getString("POLL_OPTIONS") + ";";
				poll_owner = poll_owner + row.getString("USER_ID") + ";";
				poll_datetime = poll_datetime + row.get("DATETIME") + ";";

                out.println(poll_id+"<br>");
                out.println(poll_content+"<br>");
                out.println(poll_options+"<br>");
                out.println(poll_owner+"<br>");
                out.println(poll_datetime+"<br>"+"<br>");
                }
                catch(Exception e)
                {
                    out.println(e+"<br>");
                }
                */

    			/*
    			UUID uuid = UUID.randomUUID();
    			out.println(uuid);
    			*/
    try{
        SelectQuery sq = new SelectQueryImpl(Table.getTable("Users"));
        sq.addSelectColumn(Column.getColumn("Users", "USER_ID"));
        sq.addSelectColumn(Column.getColumn("Users", "NAME"));
        sq.addSelectColumn(Column.getColumn("Users", "DEPARTMENT"));
        sq.addSelectColumn(Column.getColumn("Users", "USER_MAIL_ID"));

        CustomViewRequest cvRequest2 = new CustomViewRequest(sq);

        CustomViewManager cvManager2 = (CustomViewManager) BeanUtil.lookup("TableViewManager");
        ViewData viewData2 = cvManager2.getData(cvRequest2);
        CVTableModel model2 = (CVTableModel) viewData2.getModel();
        int colcount2 = model2.getColumnCount();
        int rowcount2 = model2.getRowCount();
        System.out.println();
        for(int k = 0; k<colcount2; k++)
        {
    %>
            <th><%=model2.getColumnName(k)%></th>
    <%
        }
    %>
    </tr>
    <%
        for(int i = 0; i < rowcount2; i++)
        {
    %>
    <tr>
    <%
            for(int j = 0; j < colcount2; j++)
            {
    %>
                <td><%=model2.getValueAt(i, j)%></td>
    <%
            }
    %>
    </tr>
    <%
        }
    }
    catch(Exception e)
    {
        out.println(e);
    }
    %>
    </table>
    <br><br>



    <h1>Display UsersCredentials</h1>
        <table border='2'>
        <tr>
        <%
        try{
            SelectQuery sq = new SelectQueryImpl(Table.getTable("UsersCredentials"));
            sq.addSelectColumn(Column.getColumn("UsersCredentials", "USER_ID"));
            sq.addSelectColumn(Column.getColumn("UsersCredentials", "USERNAME"));
            sq.addSelectColumn(Column.getColumn("UsersCredentials", "PASSWORD"));

            CustomViewRequest cvRequest2 = new CustomViewRequest(sq);

            CustomViewManager cvManager2 = (CustomViewManager) BeanUtil.lookup("TableViewManager");
            ViewData viewData2 = cvManager2.getData(cvRequest2);
            CVTableModel model2 = (CVTableModel) viewData2.getModel();
            int colcount2 = model2.getColumnCount();
            int rowcount2 = model2.getRowCount();
            System.out.println();
            for(int k = 0; k<colcount2; k++)
            {
        %>
                <th><%=model2.getColumnName(k)%></th>
        <%
            }
        %>
        </tr>
        <%
            for(int i = 0; i < rowcount2; i++)
            {
        %>
        <tr>
        <%
                for(int j = 0; j < colcount2; j++)
                {
        %>
                    <td><%=model2.getValueAt(i, j)%></td>
        <%
                }
        %>
        </tr>
        <%
            }
        }
        catch(Exception e)
        {
            out.println(e);
        }
        %>
        </table>
        <br><br>



        <h1>Display PollData</h1>
                <table border='2'>
                <tr>
                <%
                try{
                    SelectQuery sq = new SelectQueryImpl(Table.getTable("PollData"));
                    sq.addSelectColumn(Column.getColumn("PollData", "POLL_ID"));
                    sq.addSelectColumn(Column.getColumn("PollData", "USER_ID"));
                    sq.addSelectColumn(Column.getColumn("PollData", "POLL_CONTENT"));
                    sq.addSelectColumn(Column.getColumn("PollData", "POLL_OPTIONS"));
                    sq.addSelectColumn(Column.getColumn("PollData", "DATETIME"));

                    CustomViewRequest cvRequest2 = new CustomViewRequest(sq);

                    CustomViewManager cvManager2 = (CustomViewManager) BeanUtil.lookup("TableViewManager");
                    ViewData viewData2 = cvManager2.getData(cvRequest2);
                    CVTableModel model2 = (CVTableModel) viewData2.getModel();
                    int colcount2 = model2.getColumnCount();
                    int rowcount2 = model2.getRowCount();
                    System.out.println();
                    for(int k = 0; k<colcount2; k++)
                    {
                %>
                        <th><%=model2.getColumnName(k)%></th>
                <%
                    }
                %>
                </tr>
                <%
                    for(int i = 0; i < rowcount2; i++)
                    {
                %>
                <tr>
                <%
                        for(int j = 0; j < colcount2; j++)
                        {
                %>
                            <td><%=model2.getValueAt(i, j)%></td>
                <%
                        }
                %>
                </tr>
                <%
                    }
                }
                catch(Exception e)
                {
                    out.println(e);
                }
                %>
                </table>
                <br><br>



                <h1>Display PollResponseData</h1>
                <table border='2'>
                <tr>
                <%
                try{
                    SelectQuery sq = new SelectQueryImpl(Table.getTable("PollResponseData"));
                    sq.addSelectColumn(Column.getColumn("PollResponseData", "POLL_RESP_DATA_ID"));
                    sq.addSelectColumn(Column.getColumn("PollResponseData", "POLL_ID"));
                    sq.addSelectColumn(Column.getColumn("PollResponseData", "USER_ID"));
                    sq.addSelectColumn(Column.getColumn("PollResponseData", "POLL_RESULT"));
                    sq.addSelectColumn(Column.getColumn("PollResponseData", "DATETIME"));

                    CustomViewRequest cvRequest2 = new CustomViewRequest(sq);

                    CustomViewManager cvManager2 = (CustomViewManager) BeanUtil.lookup("TableViewManager");
                    ViewData viewData2 = cvManager2.getData(cvRequest2);
                    CVTableModel model2 = (CVTableModel) viewData2.getModel();
                    int colcount2 = model2.getColumnCount();
                    int rowcount2 = model2.getRowCount();
                    System.out.println();
                    for(int k = 0; k<colcount2; k++)
                    {
                %>
                        <th><%=model2.getColumnName(k)%></th>
                <%
                    }
                %>
                </tr>
                <%
                    for(int i = 0; i < rowcount2; i++)
                    {
                %>
                <tr>
                <%
                        for(int j = 0; j < colcount2; j++)
                        {
                %>
                            <td><%=model2.getValueAt(i, j)%></td>
                <%
                        }
                %>
                </tr>
                <%
                    }
                }
                catch(Exception e)
                {
                    out.println(e);
                }
                %>
                </table>
                <br><br>



                <h1>Display PollAssignedUsers</h1>
                <table border='2'>
                <tr>
                <%
                try{
                    SelectQuery sq = new SelectQueryImpl(Table.getTable("PollAssignedUsers"));
                    sq.addSelectColumn(Column.getColumn("PollAssignedUsers", "POLL_ASSIGN_USER_ID"));
                    sq.addSelectColumn(Column.getColumn("PollAssignedUsers", "POLL_ID"));
                    sq.addSelectColumn(Column.getColumn("PollAssignedUsers", "ASSIGNED_USER_ID"));

                    CustomViewRequest cvRequest2 = new CustomViewRequest(sq);

                    CustomViewManager cvManager2 = (CustomViewManager) BeanUtil.lookup("TableViewManager");
                    ViewData viewData2 = cvManager2.getData(cvRequest2);
                    CVTableModel model2 = (CVTableModel) viewData2.getModel();
                    int colcount2 = model2.getColumnCount();
                    int rowcount2 = model2.getRowCount();
                    System.out.println();
                    for(int k = 0; k<colcount2; k++)
                    {
                %>
                        <th><%=model2.getColumnName(k)%></th>
                <%
                    }
                %>
                </tr>
                <%
                    for(int i = 0; i < rowcount2; i++)
                    {
                %>
                <tr>
                <%
                        for(int j = 0; j < colcount2; j++)
                        {
                %>
                            <td><%=model2.getValueAt(i, j)%></td>
                <%
                        }
                %>
                </tr>
                <%
                    }
                }
                catch(Exception e)
                {
                    out.println(e);
                }
                %>
                </table>
                <br><br>



                <%
                /*
                out.println("<h1>PT-6046</h1>");
                try {
            			Persistence persistence = (Persistence) BeanUtil.lookup("Persistence");
            			String userID = "PT-6046";

            			SelectQuery pollassignuser_sq = new SelectQueryImpl(Table.getTable("PollAssignedUsers"));
            			pollassignuser_sq.addSelectColumn(Column.getColumn("PollAssignedUsers", "POLL_ASSIGN_USER_ID"));
            			pollassignuser_sq.addSelectColumn(Column.getColumn("PollAssignedUsers", "POLL_ID"));
            			pollassignuser_sq.addSelectColumn(Column.getColumn("PollAssignedUsers", "ASSIGNED_USER_ID"));

            			Criteria userID_criteria = new Criteria(new Column("PollAssignedUsers", "ASSIGNED_USER_ID"), userID, QueryConstants.EQUAL);

            			pollassignuser_sq.setCriteria(userID_criteria);

            			DataObject pollassignuser_dobj = persistence.get(pollassignuser_sq);
            			Iterator pollassignuser_it = pollassignuser_dobj.getRows("PollAssignedUsers");

                        if(pollassignuser_it.hasNext() == true)
                            out.println("<h1>NO data available</h1>");

            			String poll_id = "", poll_owner = "", poll_content = "", poll_options = "", poll_datetime = "";
            			*/
            			/*
            			while (pollassignuser_it.hasNext())
            			{
            				Row pollassignuser_row = (Row) pollassignuser_it.next();
            				String pollID = pollassignuser_row.getString("POLL_ID");

                            SelectQuery sq1 = new SelectQueryImpl(Table.getTable("PollResponseData"));
                            sq1.addSelectColumn(Column.getColumn("PollResponseData", "POLL_RESP_DATA_ID"));
                            sq1.addSelectColumn(Column.getColumn("PollResponseData", "POLL_ID"));
                            sq1.addSelectColumn(Column.getColumn("PollResponseData", "USER_ID"));

                            Criteria pollID_criteria1 = new Criteria(new Column("PollResponseData", "POLL_ID"), pollID, QueryConstants.EQUAL);
                            //sq1.setCriteria(pollID_criteria1);

                            Criteria userID_criteria1 = new Criteria(new Column("PollResponseData", "USER_ID"), userID, QueryConstants.EQUAL);
                            sq1.setCriteria(pollID_criteria1.and(userID_criteria1));

                            DataObject dobj1 = persistence.get(sq1);
                            Row r1= dobj1.getRow("PollResponseData");
                            if(r1 == null)
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

                                poll_id = poll_id + row.getString("POLL_ID") + ";";
                                poll_content = poll_content + row.getString("POLL_CONTENT") + ";";
                                poll_options = poll_options + row.getString("POLL_OPTIONS") + ";";
                                poll_owner = poll_owner + row.getString("USER_ID") + ";";
                                poll_datetime = poll_datetime + row.get("DATETIME") + ";";
            			    }

            			}
            			//Below 5 lines - removing last character ; from strings
            			poll_id = poll_id.substring(0, poll_id.length() - 1);
            			poll_content = poll_content.substring(0, poll_content.length() - 1);
            			poll_options = poll_options.substring(0, poll_options.length() - 1);
            			poll_owner = poll_owner.substring(0, poll_owner.length() - 1);
            			poll_datetime = poll_datetime.substring(0, poll_datetime.length() - 1);
                        */
                        /*
            			out.println("poll_id\t\t"+ poll_id);
            			out.println("<br>poll_content\t"+ poll_content);
            			out.println("<br>poll_options\t"+ poll_options);
            			out.println("<br>poll_owner\t"+ poll_owner);
            			out.println("<br>poll_datetime\t"+ poll_datetime);
            			out.println();
            		}
            		catch (Exception e)
            		{
            			out.println("<br>Exception raised in DB-> \t"+ e);
            		}
            		*/
                %>
                <br><br><br>





    </body>
</html>