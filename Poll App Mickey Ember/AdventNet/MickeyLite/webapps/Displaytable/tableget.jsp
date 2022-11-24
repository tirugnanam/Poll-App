<%@page contentType="text/html" import="java.net.*, java.io.*, java.util.*, com.adventnet.persistence.*, com.adventnet.ds.query.*, com.adventnet.ds.query.util.*, com.adventnet.db.persistence.metadata.*, com.adventnet.db.api.*, com.adventnet.persistence.personality.*, com.adventnet.persistence.xml.*, java.sql.*, javax.sql.*, com.adventnet.db.adapter.oracle.*, com.adventnet.db.persistence.metadata.*, com.adventnet.db.persistence.metadata.util.*, com.adventnet.ds.adapter.mds.*, com.adventnet.mfw.bean.*, com.adventnet.customview.*, com.adventnet.model.table.*, com.adventnet.metapersistence.*"%>
<h1>PERS 1</h1>
    <%
    Persistence pers = (Persistence)BeanUtil.lookup("Persistence");
        SelectQuery sq1 = new SelectQueryImpl(Table.getTable("EmployeeAccount"));

            sq1.addSelectColumn(Column.getColumn("EmployeeAccount", "EMPLOYEE_NAME"));
            sq1.addSelectColumn(Column.getColumn("EmployeeAccount", "EMPLOYEE_ID"));
            sq1.addSelectColumn(Column.getColumn("EmployeeAccount", "USERNAME"));
            sq1.addSelectColumn(Column.getColumn("EmployeeAccount", "PASSWORD"));
            sq1.addSelectColumn(Column.getColumn("EmployeeAccount", "DEPARTMENT"));

            Criteria criteria1 = new Criteria(new Column("EmployeeAccount", "USERNAME"), "prem", QueryConstants.EQUAL);
            Criteria criteria2 = new Criteria(new Column("EmployeeAccount", "PASSWORD"), "prem123", QueryConstants.EQUAL);
            sq1.setCriteria(criteria1.and(criteria2));

            DataObject dobj = pers.get(sq1);

            Row r= dobj.getRow("EmployeeAccount");
            if(r==null)
                out.println("<h1>NOT FOUND</h1>");
            else
            {%>
                <%=r.get(1)%><br>
                <%=r.get(2)%><br>
                <%=r.get(3)%><br>
                <%=r.get(4)%><br>
                <%=r.get(5)%><br><br>
            <%
            }
            %>

            <h1>PERS 2</h1>
            <%
            Persistence p1 = (Persistence) BeanUtil.lookup("Persistence");

            		SelectQuery sq12 = new SelectQueryImpl(Table.getTable("EmployeeAccount"));


            		sq12.addSelectColumn(Column.getColumn("EmployeeAccount", "USERNAME"));
            		sq12.addSelectColumn(Column.getColumn("EmployeeAccount", "PASSWORD"));
            		sq12.addSelectColumn(Column.getColumn("EmployeeAccount", "DEPARTMENT"));
            		sq12.addSelectColumn(Column.getColumn("EmployeeAccount", "EMPLOYEE_ID"));

            		Criteria username_criteria = new Criteria(new Column("EmployeeAccount", "USERNAME"), "prem", QueryConstants.EQUAL);
            		//sq12.setCriteria(username_criteria);

            		Criteria password_criteria = new Criteria(new Column("EmployeeAccount", "PASSWORD"), "prem123", QueryConstants.EQUAL);
            		sq12.setCriteria(username_criteria.and(password_criteria));

            		DataObject dobj1 = p1.get(sq12);

            		Row row1 = dobj1.getRow("EmployeeAccount");

            		if(row1 == null)
                                    out.println("<h2>NOT FOUND</h2>");
                                else
                                {%>
                                    <%=row1.get(1)%><br>
                                    <%=row1.get(3)%><br>
                                    <%=row1.get(2)%><br>
                                    <%=row1.get(5)%><br>
                                <%
                                }
                                %>
