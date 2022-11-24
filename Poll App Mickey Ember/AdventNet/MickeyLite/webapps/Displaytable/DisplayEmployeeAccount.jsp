<%@page contentType="text/html" import="java.net.*, java.io.*, java.util.*, com.adventnet.persistence.*, com.adventnet.ds.query.*, com.adventnet.ds.query.util.*, com.adventnet.db.persistence.metadata.*, com.adventnet.db.api.*, com.adventnet.persistence.personality.*, com.adventnet.persistence.xml.*, java.sql.*, javax.sql.*, com.adventnet.db.adapter.oracle.*, com.adventnet.db.persistence.metadata.*, com.adventnet.db.persistence.metadata.util.*, com.adventnet.ds.adapter.mds.*, com.adventnet.mfw.bean.*, com.adventnet.customview.*, com.adventnet.model.table.*, com.adventnet.metapersistence.*, org.json.JSONObject"%>
<html>
    <h1>Display 1 CustomView with XML</h1>
    <table border='1'>
        <tr><%
        System.out.println(QueryUtil.getSelectQuery(1l));
        CustomViewRequest cvRequest = new CustomViewRequest("EmployeeAccountCV");

        CustomViewManager cvManager = (CustomViewManager) BeanUtil.lookup("TableViewManager");
        System.out.println(cvManager);
        ViewData viewData = cvManager.getData(cvRequest);

        CVTableModel model = (CVTableModel) viewData.getModel();
        int colcount = model.getColumnCount();
        int rowcount = model.getRowCount();
        for(int k = 0; k<colcount; k++)
        {%>
            <th><%=model.getColumnName(k)%></th><%
        }%>
        </tr><%
        for(int i=0; i<rowcount;i++)
        {%>
            <tr><%
            for (int j=0;j<colcount;j++)
            {%>
                <td><%=model.getValueAt(i, j)%></td><%
            }%>
            </tr><%
         }%>
    </table>
    <br><br>



    <h1>Display 2 Data Access</h1>
    <%
        Criteria c = new Criteria(new Column("EmployeeAccount", "EMPLOYEE_ID"),"PT-6045", QueryConstants.EQUAL);

        DataObject d = DataAccess.get("EmployeeAccount",c);
        //DataObject d = DataAccess.get("EmployeeAccount",(Criteria)null);
        Iterator it=d.getRows("EmployeeAccount");

        while(it.hasNext())
        {
        // returns a row
        Row r=(Row)it.next();
        // to return each column value of the row
        %>
        <%=r.get(1)%>
        <%=r.get(4)%>
        <%=r.get(2)%><br><%
        }
    %>
    <br><br>



    <h1>Display 3 CustomView without XML</h1>
    <table border='1'><tr>
    <%
        SelectQuery sq = new SelectQueryImpl(Table.getTable("EmployeeAccount"));
        sq.addSelectColumn(Column.getColumn("EmployeeAccount", "EMPLOYEE_ID"));
        sq.addSelectColumn(Column.getColumn("EmployeeAccount", "EMPLOYEE_NAME"));
        sq.addSelectColumn(Column.getColumn("EmployeeAccount", "USERNAME"));

        CustomViewRequest cvRequest2 = new CustomViewRequest(sq);

        CustomViewManager cvManager2 = (CustomViewManager) BeanUtil.lookup("TableViewManager");
        ViewData viewData2 = cvManager2.getData(cvRequest2);
        CVTableModel model2 = (CVTableModel) viewData2.getModel();
        int colcount2 = model2.getColumnCount();
        int rowcount2 = model2.getRowCount();
        System.out.println();
        for(int k = 0; k<colcount2; k++)
        { %>
            <th><%=model2.getColumnName(k)%></th><%
        }%>
        </tr>
        <%
        for(int i = 0; i < rowcount2; i++)
        { %>
            <tr><%
            for(int j = 0; j < colcount2; j++)
            {%>
                <td><%=model2.getValueAt(i, j)%></td><%
            }%>
            </tr>
        <%}
    %>
    </table>
    <br><br>
    <table border='2'><tr>
        <%
            SelectQuery sq2 = new SelectQueryImpl(Table.getTable("PollDB"));
            sq2.addSelectColumn(Column.getColumn("PollDB", "POLL_ID"));
            sq2.addSelectColumn(Column.getColumn("PollDB", "EMPLOYEE_ID"));
            sq2.addSelectColumn(Column.getColumn("PollDB", "EMPLOYEE_NAME"));
            sq2.addSelectColumn(Column.getColumn("PollDB", "USERNAME"));
            sq2.addSelectColumn(Column.getColumn("PollDB", "POLL_DATETIME"));
            sq2.addSelectColumn(Column.getColumn("PollDB", "POLL_CONTENT"));
            sq2.addSelectColumn(Column.getColumn("PollDB", "POLL_OPTIONS"));
            sq2.addSelectColumn(Column.getColumn("PollDB", "POLL_ASSIGNED"));
            sq2.addSelectColumn(Column.getColumn("PollDB", "POLL_FILLED"));
            sq2.addSelectColumn(Column.getColumn("PollDB", "POLL_UNFILLED"));
            sq2.addSelectColumn(Column.getColumn("PollDB", "POLL_RESULTS"));


            CustomViewRequest cvRequest3 = new CustomViewRequest(sq2);

            CustomViewManager cvManager3 = (CustomViewManager) BeanUtil.lookup("TableViewManager");
            ViewData viewData3 = cvManager3.getData(cvRequest3);
            CVTableModel model3 = (CVTableModel) viewData3.getModel();
            int colcount3 = model3.getColumnCount();
            int rowcount3 = model3.getRowCount();
            System.out.println();
            for(int k = 0; k<colcount3; k++)
            { %>
                <th><%=model3.getColumnName(k)%></th><%
            }%>
            </tr>
            <%
            for(int i = 0; i < rowcount3; i++)
            { %>
                <tr><%
                for(int j = 0; j < colcount3; j++)
                {%>
                    <td><%=model3.getValueAt(i, j)%></td><%
                }%>
                </tr>
            <%}
        %>
        </table>
    <h1>END</h1><br>
    <%
        out.println("<br><br>Username :" + session.getAttribute("Username"));
        		out.println("<br>Password :" + session.getAttribute("Password"));
        		out.println("<br>sessionID :" + session.getAttribute("SessionID"));
        		out.println("<br>sessionTime :" + session.getAttribute("SessionTime"));

    %>
    <br><br><h1>PERS 1</h1>
    <%
    Persistence pers = (Persistence)BeanUtil.lookup("Persistence");
        SelectQuery sq1 = new SelectQueryImpl(Table.getTable("EmployeeAccount"));
            sq1.addSelectColumn(Column.getColumn("EmployeeAccount", "EMPLOYEE_ID"));
            sq1.addSelectColumn(Column.getColumn("EmployeeAccount", "EMPLOYEE_NAME"));
            sq1.addSelectColumn(Column.getColumn("EmployeeAccount", "USERNAME"));
            sq1.addSelectColumn(Column.getColumn("EmployeeAccount", "PASSWORD"));

            Criteria criteria1 = new Criteria(new Column("EmployeeAccount", "USERNAME"), "tirugnanam.m", QueryConstants.EQUAL);
            sq1.setCriteria(criteria1);

            Criteria criteria2 = new Criteria(new Column("EmployeeAccount", "PASSWORD"), "tiru123", QueryConstants.EQUAL);
            sq1.setCriteria(criteria1.and(criteria2));

            DataObject dobj = pers.get(sq1);

            Row r= dobj.getRow("EmployeeAccount");
            if(r==null)
                out.println("<h1>NOT FOUND</h1>");
            else
            {%>
                <%=r.get(1)%>
                <%=r.get(2)%>
                <%=r.get(3)%>
                <%=r.get(4)%><br><br>
            <%
            }
            try
            {
                JSONObject jObj =r.getAsJSON();
                out.println(jObj.toString());
                out.println(jObj.get("username"));
                //out.println(jObj.toString(2));
            }
            catch(Exception e)
            {
                out.println("Exception arised -> "+String.valueOf(e));
            }
            %>


            <%
                /*UpdateQuery u = new UpdateQueryImpl("PollDB");
                Criteria cia = new Criteria(new Column("PollDB", "POLL_ID"),"4", QueryConstants.EQUAL);
                u.setCriteria(cia);
                //u.setUpdateColumn("POLL_ASSIGNED","prem,rahul,");
                u.setUpdateColumn("POLL_FILLED","");
                u.setUpdateColumn("POLL_UNFILLED","prem,rahul,");
                u.setUpdateColumn("POLL_RESULTS","");

                //update row
                Persistence per= (Persistence)BeanUtil.lookup("Persistence");
                per.update(u);*/


                out.println("<br><br>rahul,tirugnanam.m<br><br>");



                Persistence pr = (Persistence) BeanUtil.lookup("Persistence");

                			SelectQuery sqy = new SelectQueryImpl(Table.getTable("PollDB"));
                			sqy.addSelectColumn(Column.getColumn("PollDB", "POLL_ID"));
                			sqy.addSelectColumn(Column.getColumn("PollDB", "POLL_FILLED"));
                			sqy.addSelectColumn(Column.getColumn("PollDB", "POLL_UNFILLED"));
                			sqy.addSelectColumn(Column.getColumn("PollDB", "POLL_RESULTS"));


                			Criteria idcriteria = new Criteria(new Column("PollDB", "POLL_ID"), 4, QueryConstants.EQUAL);
                			sqy.setCriteria(idcriteria);

                            try
                            {
                			DataObject doj = pr.get(sqy);
                			Row rowq = doj.getRow("PollDB");%>
                			<%=rowq.get(1)%><br>

                            <%=rowq.get("POLL_UNFILLED")%><br>
                            <%=rowq.get("POLL_FILLED")%><br>
                            <%=rowq.get(11)%><br><br><%
                            }
                            catch(Exception e)
                            {
                                out.println(e);
                            }
            %>




            <h2>Join table</h2>
            <%
                Table table1 = new Table("PollDB");
                SelectQuery sqj = new SelectQueryImpl(table1);

                sqj.addSelectColumn(Column.getColumn("PollDB", "POLL_ID"));
                sqj.addSelectColumn(Column.getColumn("PollDB","EMPLOYEE_ID"));
                sqj.addSelectColumn(Column.getColumn("PollDB","EMPLOYEE_NAME"));

                sqj.addSelectColumn(Column.getColumn("EmployeeAccount","EMPLOYEE_ID"));
                sqj.addSelectColumn(Column.getColumn("EmployeeAccount","USERNAME"));

                Join join = new Join("PollDB", "EmployeeAccount",
                new String[]{"EMPLOYEE_ID"}, new String[]{"EMPLOYEE_ID"}, Join.INNER_JOIN);
                sqj.addJoin(join);

                Persistence per= (Persistence)BeanUtil.lookup("Persistence");
                DataObject dj =per.get(sqj);
                out.println(dj);
                %><br><br>TABLE below<br>

                <%
                Iterator itj= dj.getRows("PollDB");
                //dj.getRow("EmployeeAccount",new Criteria(Column.getColumn("EmployeeAccount","EMPLOYEE_ID"),rj.get("EMPLOYEE_ID"),QueryConstants.EQUAL)).getString("USERNAME")


                while(itj.hasNext())
                {
                    Row rj=(Row)itj.next();

                    %>
                    <%=rj.get("POLL_ID")%><br>
                    <%=rj.get("EMPLOYEE_ID")%><br>
                    <%=rj.get("EMPLOYEE_NAME")%><br>

                    <%=dj.getRow("EmployeeAccount").getString("USERNAME")%><br><br>

                <%}%>


</html>

